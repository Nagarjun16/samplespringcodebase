/**
 * 
 */
package com.ngen.cosys.house.information.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.billing.api.CheckPaymentStatus;
import com.ngen.cosys.events.payload.ShipmentStatusUpdateEvent;
import com.ngen.cosys.events.producer.ShipmentStatusUpdateEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.house.information.dao.HouseInformationDao;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.transaction.PureTransactional;

/**
 * @author Priyanka.Middha
 *
 */
@Service
@PureTransactional
public class HouseInformationServiceImpl implements HouseInformationService {

	@Autowired
	private HouseInformationDao houseInformationDAO;

	@Autowired
	private ShipmentProcessorService shipmentProcessorService;

	@Autowired
	CheckPaymentStatus paymentStatus;

	@Autowired
	HAWBHandlingHelper hawbHelper;
	
	@Autowired
	private DomesticInternationalHelper domesticInternationalHelper;
	
	@Autowired
	private ShipmentStatusUpdateEventProducer shipmentStatusUpdateEventProducer;
	/**
	 * Fetches HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentInfoModel
	 * @throws CustomException
	 */

	@Override
	public ShipmentInfoModel getHouseInfo(ShipmentInfoSearchReq search) throws CustomException {

		// Populate shipment date if empty
		if (ObjectUtils.isEmpty(search.getShipmentDate())) {
			LocalDate shipmentDate = this.shipmentProcessorService.getShipmentDate(search.getShipmentNumber());
			search.setShipmentDate(shipmentDate);
		}

		ShipmentInfoModel responseModel = this.houseInformationDAO.getHouseInfo(search);

		Optional<ShipmentInfoModel> o = Optional.ofNullable(responseModel);
		if (!o.isPresent()) {
			throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
		}
		 DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
			domesticInternationalHelperRequest.setOrigin(responseModel.getOrigin());
			domesticInternationalHelperRequest.setDestination(responseModel.getDestination());
			responseModel
					.setIndicatorDomIntl(domesticInternationalHelper.getDOMINTHandling(domesticInternationalHelperRequest));
			 // If Domestic & Feature is Disabled handledBy cannot be changed
			if(responseModel.getIndicatorDomIntl()!=null && responseModel.getIndicatorDomIntl().equalsIgnoreCase("DOM") 
					&& !FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.DomesticHAWBHandling.class)) {
				responseModel.setHandlingChangeFlag(false);
			}

		return responseModel;
	}

	/**
	 * Changing handledBy HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	@Override
	public ShipmentMaster changeHandling(ShipmentMaster shipmentMaster) throws CustomException {
		
		HAWBHandlingHelperRequest request=new  HAWBHandlingHelperRequest();
		request.setShipmentNumber(shipmentMaster.getShipmentNumber());
		request.setShipmentDate(shipmentMaster.getShipmentDate());
		HAWBHandlingHelperRequest handlingTypeChange=hawbHelper.checkChangeHandling(request);
		
		if(handlingTypeChange.getChangeHandlingFlag()) {
		if (shipmentMaster.getHandledByHouse().equalsIgnoreCase("M")) {
			shipmentMaster.setHandledByHouse("H");
		} else {
			shipmentMaster.setHandledByHouse("M");
		}
       //for audit trail
		shipmentMaster.setHandlingChangeDate(shipmentMaster.getModifiedOn());
		houseInformationDAO.changeHandling(shipmentMaster);
		return shipmentMaster;
		}
		else
		{   shipmentMaster.setMessageList(handlingTypeChange.getMessageList());
			return shipmentMaster;
		}
	}
	
	@Override
	public void publishShipmentEvent(ShipmentMaster shipmentMaster) throws CustomException {
		// Check the flight completed status
		BigInteger flightId = null;
		int isFlightCompleted = 0;
		if (!Objects.isNull(shipmentMaster.getShipmentId()) && shipmentMaster.getShipmentId() != 0) {
				flightId = houseInformationDAO.getFlightId(BigInteger.valueOf(shipmentMaster.getShipmentId()));
				if (!Objects.isNull(flightId)) {
					isFlightCompleted = houseInformationDAO.isFlightCompleted(flightId);
					if (isFlightCompleted > 0) {
						ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
						event.setFlightId(flightId);
						event.setHouseNumber(null);
						event.setShipmentNumber(shipmentMaster.getShipmentNumber());
						event.setCreatedBy(shipmentMaster.getCreatedBy());
						event.setCreatedOn(LocalDateTime.now());
						event.setFunction("AWB Document");
						if(Objects.nonNull(shipmentMaster.getConsignee())) {
							event.setConsigneeName(shipmentMaster.getConsignee().getCustomerName());
							event.setConsigneeAddress(shipmentMaster.getConsignee().getAddress().getPlace());
							event.setClearingAgentCode(shipmentMaster.getConsignee().getAppointedAgentCode());
							if(Objects.nonNull(shipmentMaster.getConsignee().getAppointedAgentCode())) {
								String clearingAgentName=getClearingAgentName(shipmentMaster.getConsignee().getAppointedAgentCode());
								if(Objects.nonNull(clearingAgentName)) {
									event.setClearingAgentName(clearingAgentName);
								}
							}
						} else {
							if(Objects.nonNull(shipmentMaster.getAgentCode())) {
								event.setClearingAgentCode(shipmentMaster.getAgentCode());
							}
							if(Objects.nonNull(shipmentMaster.getAgentName())) {
								event.setClearingAgentName(shipmentMaster.getAgentName());
							}
						}
						
						event.setMessageType("U");
						shipmentStatusUpdateEventProducer.publish(event);
					}
				}
		}
	}
	
	private String getClearingAgentName(String appointedAgent) throws CustomException {
		   String clearingAgentName=null;
		   clearingAgentName = houseInformationDAO.getClearingAgentName(appointedAgent);
		   return clearingAgentName;
	   }

}
