/**
 * 
 */
package com.ngen.cosys.house.information.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;
import com.ngen.cosys.shipment.enums.ShipmentProcessType;
import com.ngen.cosys.shipment.information.model.ShipmentAcceptanceModel;
import com.ngen.cosys.shipment.information.model.ShipmentDamageInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentDeliveryModel;
import com.ngen.cosys.shipment.information.model.ShipmentFlightModel;
import com.ngen.cosys.shipment.information.model.ShipmentFreightOutInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentHouseInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentHouseInfoSummaryModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.information.model.ShipmentInventoryModel;
import com.ngen.cosys.shipment.information.model.ShipmentLoadingInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentNoaListModel;
import com.ngen.cosys.shipment.information.model.ShipmentTransferManifestModel;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * @author Priyanka.Middha
 *
 */

@Repository("houseInformationDAO")
public class HouseInformationDaoImpl extends BaseDAO implements HouseInformationDao {

	@Autowired
	private DomesticInternationalHelper domesticInternationalHelper;

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSessionROI;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO#
	 * getImportShipmentInfo(com.ngen.cosys.shipment.information.model.
	 * ShipmentInfoSearchReq)
	 */
	@Override
	public ShipmentInfoModel getHouseInfo(ShipmentInfoSearchReq search) throws CustomException {
		ShipmentInfoModel shipmentInfoModel = this.fetchObject("sqlQueryGetHouseInformation", search, sqlSessionROI);

		Optional<ShipmentInfoModel> o = Optional.ofNullable(shipmentInfoModel);
		if (!o.isPresent()) {
			if ("AWB".equalsIgnoreCase(search.getShipmentType())) {
				// Look for FWB has been received
				shipmentInfoModel = this.fetchObject("sqlQueryGetFWBShipmentInformation", search, sqlSessionROI);
				o = Optional.ofNullable(shipmentInfoModel);
				if (!o.isPresent()) {
					// Look for Shipment has been booked
					shipmentInfoModel = this.fetchObject("sqlQueryGetBookingShipmentInformation", search,
							sqlSessionROI);
					o = Optional.ofNullable(shipmentInfoModel);
					if (!o.isPresent()) {
						throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
					}
				}
			} else {
				throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
			}
		} else if (!ObjectUtils.isEmpty(shipmentInfoModel.getCancelledOn())) {
			throw new CustomException("data.shipment.cancelled", "shipmentNumber", ErrorType.ERROR);
		}

		// Set the tenant id and terminal id // Tenant Id is not getting used anywhere
		// so not changing it tenantConfig.airportCode
		shipmentInfoModel.setTenantId(search.getTenantId());
		shipmentInfoModel.setTerminal(search.getTerminal());
		DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
		domesticInternationalHelperRequest.setOrigin(shipmentInfoModel.getOrigin());
		domesticInternationalHelperRequest.setDestination(shipmentInfoModel.getDestination());
		shipmentInfoModel
				.setIndicatorDomIntl(domesticInternationalHelper.getDOMINTHandling(domesticInternationalHelperRequest));
		// Set the remarks
		List<ShipmentRemarksModel> shipmentRemarks = this.fetchList("sqlQueryGetShipmentInformationShipmentRemarks",
				shipmentInfoModel, sqlSessionTemplate);
		List<ShipmentRemarksModel> shipmentHouseRemarks = this
				.fetchList("sqlQueryGetShipmentInformationShipmentHouseRemarks", shipmentInfoModel, sqlSessionTemplate);
		shipmentInfoModel.setRemarks(shipmentRemarks);
		shipmentInfoModel.setHouseRemarks(shipmentHouseRemarks);

		switch (shipmentInfoModel.getProcess()) {

		case ShipmentProcessType.Type.TRANSHIPMENT:
			// Fetch arrival manifest
			List<ShipmentFlightModel> transhipmentArrivalManifestDetails = this
					.fetchList("sqlQueryGetIncomingShipmentFilghtDetails", shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setIncomingFlightDetails(transhipmentArrivalManifestDetails);
			for (ShipmentFlightModel arrivalManifestInfo : transhipmentArrivalManifestDetails) {
				// Set the inventory
				arrivalManifestInfo.setShipmentId(shipmentInfoModel.getShipmentId());
				arrivalManifestInfo.setHwbNumber(search.getHwbNumber());
				List<ShipmentInventoryModel> shipmentInventory = this
						.fetchList("sqlQueryGetShipmentInventoryInformation", arrivalManifestInfo, sqlSessionROI);
				arrivalManifestInfo.setInventoryDetails(shipmentInventory);

				// Set the remarks
				List<ShipmentRemarksModel> remarks = this.fetchList("sqlGetShipmentFlightInfoShipmentRemarks",
						arrivalManifestInfo, sqlSessionROI);
				arrivalManifestInfo.setRemarks(remarks);

			}

			// Fetch Booking Info
			List<ShipmentFlightModel> transhipmentBookingInfo = this.fetchList("sqlQueryGetExportBookingInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setOutbooundFlightDetails(transhipmentBookingInfo);
			for (ShipmentFlightModel booking : transhipmentBookingInfo) {
				booking.setShipmentId(shipmentInfoModel.getShipmentId());
				List<ShipmentLoadingInfoModel> loadingInfoDetails = this.fetchList("sqlExportLoadingInfo", booking,
						sqlSessionROI);
				booking.setLoadingInfoModel(loadingInfoDetails);

				// Set the remarks
				List<ShipmentRemarksModel> remarks = this.fetchList("sqlGetShipmentFlightInfoShipmentRemarks", booking,
						sqlSessionROI);
				booking.setRemarks(remarks);
			}

			// Get Transfer Manifest Info
			List<ShipmentTransferManifestModel> transferManifestInfo = this.fetchList("sqlQueryGetTrmInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setTmDetails(transferManifestInfo);

			// Get Freight Out Info
			List<ShipmentFreightOutInfoModel> freightOutInfo = this.fetchList("sqlQueryGetFreightOutInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setFreightOutDetails(freightOutInfo);

			break;
		case ShipmentProcessType.Type.EXPORT:
			// Fetch Booking Info
			List<ShipmentFlightModel> bookingInfo = this.fetchList("sqlQueryGetExportBookingInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setOutbooundFlightDetails(bookingInfo);
			for (ShipmentFlightModel booking : bookingInfo) {
				booking.setShipmentId(shipmentInfoModel.getShipmentId());
				List<ShipmentLoadingInfoModel> loadingInfoDetails = this.fetchList("sqlQueryExportLoadingInformation",
						booking, sqlSessionROI);
				booking.setLoadingInfoModel(loadingInfoDetails);

				// Set the remarks
				List<ShipmentRemarksModel> remarks = this
						.fetchList("sqlQueryGetShipmentFlightInformationShipmentRemarks", booking, sqlSessionROI);
				booking.setRemarks(remarks);

			}
			// Fetch Acceptance Info
			List<ShipmentAcceptanceModel> acceptanceInfo = this.fetchList("sqlQueryGetEacceptanceInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setAcceptanceDetails(acceptanceInfo);
			for (ShipmentAcceptanceModel acceptanceModel : acceptanceInfo) {
				// Set the shipment info
				acceptanceModel.setShipmentNumber(shipmentInfoModel.getShipmentNumber());
				acceptanceModel.setShipmentDate(shipmentInfoModel.getShipmentDate());

				// Set the acceptance
				ShipmentFlightModel inventoryFlightModel = new ShipmentFlightModel();
				inventoryFlightModel.setShipmentId(shipmentInfoModel.getShipmentId());
				inventoryFlightModel.setHwbNumber(search.getHwbNumber());
				
				List<ShipmentInventoryModel> shipmentInventory = this
						.fetchList("sqlQueryGetShipmentInventoryInformation", inventoryFlightModel, sqlSessionROI);
				acceptanceModel.setInventoryDetails(shipmentInventory);

				// Set the remarks
				List<ShipmentRemarksModel> acceptanceRemarks = this
						.fetchList("sqlQueryGetShipmentAcceptanceShipmentRemarks", acceptanceModel, sqlSessionROI);
				acceptanceModel.setRemarks(acceptanceRemarks);
			}

			// Get Freight Out Info
			List<ShipmentFreightOutInfoModel> freightOutInfo1 = this.fetchList("sqlQueryGetFreightOutInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setFreightOutDetails(freightOutInfo1);

			// Get E-Warehouse Trigger Details
			List<ShipmentNoaListModel> eWarehouseRemarks = this.fetchList("sqlQueryGetEWarehouseReceiptDetails", search,
					sqlSessionROI);
			shipmentInfoModel.setEWarehouseRemarks(eWarehouseRemarks);

			break;
		case ShipmentProcessType.Type.IMPORT:
			// Fetch Arrival Manifest Info
			List<ShipmentFlightModel> arrivalManifestDetails = this
					.fetchList("sqlQueryGetIncomingShipmentFilghtDetails", shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setIncomingFlightDetails(arrivalManifestDetails);
			for (ShipmentFlightModel arrivalManifestInfo : arrivalManifestDetails) {
				arrivalManifestInfo.setShipmentId(shipmentInfoModel.getShipmentId());
				 arrivalManifestInfo.setHwbNumber(search.getHwbNumber());
				List<ShipmentInventoryModel> shipmentInventory = this
						.fetchList("sqlQueryGetShipmentInventoryInformation", arrivalManifestInfo, sqlSessionROI);
				arrivalManifestInfo.setInventoryDetails(shipmentInventory);

				// Set the remarks
				List<ShipmentRemarksModel> remarks = this.fetchList(
						"sqlQueryGetShipmentFlightInformationShipmentRemarks", arrivalManifestInfo, sqlSessionROI);
				arrivalManifestInfo.setRemarks(remarks);

				// Get Damage Info
				List<ShipmentDamageInfoModel> damageInfo = this.fetchList("sqlQueryGetDamageInformation",
						arrivalManifestInfo, sqlSessionROI);
				arrivalManifestInfo.setDamageDetails(damageInfo);

			}

			// Fetch Delivery Info
			List<ShipmentDeliveryModel> shipmentDeliveryDetails = this.fetchList("sqlQueryGetImportDeliveryInformation",
					shipmentInfoModel, sqlSessionROI);
			shipmentInfoModel.setShipmentDeliveryDetails(shipmentDeliveryDetails);

			// set noa details
			List<ShipmentNoaListModel> noaListModels = this.fetchList("sqlQueryGetNoaDetails", search, sqlSessionROI);
			shipmentInfoModel.setNoalist(noaListModels);

			break;
		default:
			break;
		}

		// Fetch House info

		List<ShipmentHouseInfoModel> houseInfoDetails = this.fetchList("sqlQueryGetShipmentHouseInformation",
				shipmentInfoModel, sqlSessionROI);
		shipmentInfoModel.setHouseInfoDetails(houseInfoDetails);

		// Fetch House info summary details

		List<ShipmentHouseInfoSummaryModel> houseSummaryDetails = this.fetchList("sqlQueryGetHouseSummary",
				shipmentInfoModel, sqlSessionROI);
		shipmentInfoModel.setHouseSummaryDetails(houseSummaryDetails);

		// Get FWB Message Info
		List<ShipmentInfoMessageModel> fwbMessages = this.fetchList("sqlQueryGetFWBMessageInformation",
				shipmentInfoModel, sqlSessionROI);
		shipmentInfoModel.setFwbOutgoingMessageSummary(fwbMessages);

		// Get FHL Message Info
		List<ShipmentInfoMessageModel> fsuMessages = this.fetchList("sqlQueryGetFSUMessageInformation",
				shipmentInfoModel, sqlSessionROI);
		shipmentInfoModel.setFsuOutgoingMessageSummary(fsuMessages);

		// Get FSU Message Info
		List<ShipmentInfoMessageModel> fhlMessages = this.fetchList("sqlQueryGetFHLMessageInformation",
				shipmentInfoModel, sqlSessionROI);
		shipmentInfoModel.setFhlOutgoingMessageSummary(fhlMessages);

		// part shipment info
		List<ShipmentFlightModel> partShpInfo = this.fetchList("sqlQueryGetPartShipmentInformation", shipmentInfoModel,
				sqlSessionROI);
		if (!CollectionUtils.isEmpty(partShpInfo)) {
			String previousSuffix = null;
			for (ShipmentFlightModel t : partShpInfo) {
				if (StringUtils.isEmpty(previousSuffix) || (!StringUtils.isEmpty(previousSuffix)
						&& !previousSuffix.equalsIgnoreCase(t.getCurrentPartSuffix()))) {
					previousSuffix = t.getCurrentPartSuffix();
					t.setPartSuffix(previousSuffix);
				}
			}
		}
		shipmentInfoModel.setPartShipmentDetails(partShpInfo);

		return shipmentInfoModel;
	}

	/**
	 * Changing handledBy HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT)
	public void changeHandling(ShipmentMaster shipmentMaster) throws CustomException {
		updateData("sqlQueryUpdateHandling", shipmentMaster, sqlSessionTemplate);
	}
	
	@Override
	public Integer isFlightCompleted(BigInteger flightId) throws CustomException {
		return fetchObject("sqlCheckFlightCompleted", flightId, sqlSessionTemplate);
	}
	
	@Override
	public BigInteger getFlightId(BigInteger shipmentId) throws CustomException {
		return fetchObject("sqlGetFlightId", shipmentId, sqlSessionTemplate);
	}

	@Override
	public String getClearingAgentName(String agentCode) throws CustomException {
		return fetchObject("sqlFetchClearingAgentName", agentCode, sqlSessionTemplate);
	}

}
