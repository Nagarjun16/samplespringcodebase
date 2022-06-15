package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.dao.ShipmentAWBDocumentDao;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.enums.MaintainFWBQueryIds;
import com.ngen.cosys.shipment.enums.MaintainFWBValidatorConstants;
import com.ngen.cosys.shipment.model.AccountingInfo;
import com.ngen.cosys.shipment.model.CustomerContactInfo;
import com.ngen.cosys.shipment.model.CustomerInfo;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FWBDetails;
import com.ngen.cosys.shipment.model.FetchRouting;
import com.ngen.cosys.shipment.model.FlightBooking;
import com.ngen.cosys.shipment.model.OtherCharges;
import com.ngen.cosys.shipment.model.OtherCustomsInfo;
import com.ngen.cosys.shipment.model.OtherParticipantInfo;
import com.ngen.cosys.shipment.model.OverseasConsignee;
import com.ngen.cosys.shipment.model.RateDescOtherInfo;
import com.ngen.cosys.shipment.model.RateDescription;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.model.SHC;
import com.ngen.cosys.shipment.model.SSROSIInfo;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MaintainFWBDAOImpl extends BaseDAO implements MaintainFWBDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	@Autowired
	private ShipmentAWBDocumentDao daoAwb;

	@Override
	public FWB get(String requestModel) throws CustomException {
		return fetchObject("searchFWB", requestModel, sqlSessionShipment);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB)
	public FWB save(FWB requestModel) throws CustomException {

		// Set the nature of goods for FWB
		// Variable for fuse nature of goods
		boolean isNatureOfGoodsFound = false;
		if (StringUtils.isEmpty(requestModel.getNatureOfGoodsDescription())) {
			// Check for Nature of Goods
			for (RateDescription t : requestModel.getRateDescription()) {
				if (!CollectionUtils.isEmpty(t.getRateDescriptionOtherInfo()) && !isNatureOfGoodsFound) {
					t.getRateDescriptionOtherInfo().forEach(t1 -> {
						if ("NG".equalsIgnoreCase(t1.getRateLine())) {
							requestModel.setNatureOfGoodsDescription(t1.getNatureOfGoodsDescription());
						}
					});

					if (StringUtils.isEmpty(requestModel.getNatureOfGoodsDescription())) {
						t.getRateDescriptionOtherInfo().forEach(t1 -> {
							if ("NC".equalsIgnoreCase(t1.getRateLine())) {
								requestModel.setNatureOfGoodsDescription(t1.getNatureOfGoodsDescription());
							}
						});
					}

					if (!StringUtils.isEmpty(requestModel.getNatureOfGoodsDescription())) {
						isNatureOfGoodsFound = true;
					}
				}
			}
		}
		Boolean isShipmentDeleivered = isShipmentDeliveryInitiated(requestModel);
		if(isShipmentDeleivered && requestModel.getShipmentFreightWayBillId() > 0) {
			requestModel.addError("data.shipment.delivered", null, ErrorType.ERROR);
			throw new CustomException();
		}
		Integer freightWayBillUpdateCount = 0;
		// 1. Insert/Update Shipment_FreightWayBill
		freightWayBillUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_DETAILS.getQueryId(),
				requestModel, sqlSessionShipment);
		
		
		if (freightWayBillUpdateCount == 0) {
			// manually created
			requestModel.setReceivedManuallyFlag(true);
			requestModel.setApplyCharge(Boolean.TRUE);
			insertData(MaintainFWBQueryIds.INSERT_AWB_DETAILS.getQueryId(), requestModel, sqlSessionShipment);

		}

		// 2. Insert/Delete Shipment_FreightWayBillSHC
		ArrayList<Integer> shcDeleteCountdeleteData = deleteData(
				MaintainFWBQueryIds.DELETE_AWB_SHC_DETAILS.getQueryId(), requestModel.getShcode(), sqlSessionShipment);
		long shcCnt = shcDeleteCountdeleteData.stream().count();

		if (shcCnt > 0) {
			requestModel.getShcode().stream()
					.forEach(shc -> shc.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId()));
			List<SHC> lstShc = requestModel.getShcode().stream()
					.filter(e -> Optional.ofNullable(e.getSpecialHandlingCode()).isPresent()
							&& e.getSpecialHandlingCode().length() > 0)
					.collect(Collectors.toList());
			insertData(MaintainFWBQueryIds.INSERT_AWB_SHC_DETAILS.getQueryId(), lstShc, sqlSessionShipment);
		}
		// 3.Consingee
		Integer freightWayBillConsigneeUpdateCount = updateData(
				MaintainFWBQueryIds.UPDATE_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getConsigneeInfo(),
				sqlSessionShipment);
		if (freightWayBillConsigneeUpdateCount == 0) {
			requestModel.getConsigneeInfo().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getConsigneeInfo(),
					sqlSessionShipment);
		}

		// 4. Consignee Address
		requestModel.getConsigneeInfo().getCustomerAddressInfo().setShipmentFreightWayBillCustomerInfoId(
				requestModel.getConsigneeInfo().getShipmentFreightWayBillCustomerInfoId());
		Integer freightWayBillConsigneeAddressUpdateCount = updateData(
				MaintainFWBQueryIds.UPDATE_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
				requestModel.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionShipment);
		if (freightWayBillConsigneeAddressUpdateCount == 0
				&& (Optional.ofNullable(requestModel.getConsigneeInfo().getCustomerName()).isPresent()
						&& requestModel.getConsigneeInfo().getCustomerName().length() != 0)) {
			insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
					requestModel.getConsigneeInfo().getCustomerAddressInfo(), sqlSessionShipment);
		}

		
		requestModel.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo().stream()
				.forEach(consigneeCont -> consigneeCont.setShipmentFreightWayBillCustomerAddressInfoId(requestModel
						.getConsigneeInfo().getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId()));
		List<CustomerContactInfo> consContactData = requestModel.getConsigneeInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
						&& Optional.ofNullable(e.getContactDetail()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() == null || e.getFlagCRUD().equalsIgnoreCase("C")).collect(Collectors.toList());
		//insert Contact info
		if(!CollectionUtils.isEmpty(consContactData)) {
		insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(), consContactData,
				sqlSessionShipment);
		}
		// update contact info
		List<CustomerContactInfo> cneUpdateContactData = requestModel.getConsigneeInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
						&& Optional.ofNullable(e.getContactDetail()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() != null && e.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(cneUpdateContactData)) {
			updateData(MaintainFWBQueryIds.UPDATE_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(), cneUpdateContactData,
					sqlSessionShipment);
		}

		List<CustomerContactInfo> cneDeleteContactData = requestModel.getConsigneeInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() != null && e.getFlagCRUD().equalsIgnoreCase("D")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(cneDeleteContactData)) {
			deleteData(MaintainFWBQueryIds.DELETE_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(),cneDeleteContactData,
					sqlSessionShipment);
		}

		// Overseas Consignee
		if (requestModel.getConsigneeInfo().isFlag()) {
			// INSTANTIATE OBJECT
			OverseasConsignee overseasConsignee = new OverseasConsignee();

			// SET THE VALUES
			CustomerInfo custOverseasConsigneeInfo = requestModel.getConsigneeInfo();

			// set Overseas data
			overseasConsignee.setConsigneeName(requestModel.getConsigneeInfo().getCustomerName());
			overseasConsignee.setDestinationCode(requestModel.getDestination());
			overseasConsignee.setConsigneeState(custOverseasConsigneeInfo.getCustomerAddressInfo().getStateCode());
			overseasConsignee
					.setConsigneeAddress(custOverseasConsigneeInfo.getCustomerAddressInfo().getStreetAddress1());
			overseasConsignee
					.setConsigneePostalCode(custOverseasConsigneeInfo.getCustomerAddressInfo().getPostalCode());
			overseasConsignee.setCountryCode(custOverseasConsigneeInfo.getCustomerAddressInfo().getCountryCode());
			overseasConsignee.setConsigneePlace(custOverseasConsigneeInfo.getCustomerAddressInfo().getCustomerPlace());

			List<CustomerContactInfo> custOverseasConsigneeContactInfo = new ArrayList<>();

			custOverseasConsigneeContactInfo = custOverseasConsigneeInfo.getCustomerAddressInfo()
					.getCustomerContactInfo();

			custOverseasConsigneeContactInfo.forEach(e1 -> {
				if (!Optional.ofNullable(e1).isPresent()) {
					if (e1.getContactIdentifier().equals("TE")) {
						overseasConsignee.setConsigneePhone(e1.getContactDetail());
					}
				}
			});

			int res = fetchObject("sqlGetCustomerInfo1", overseasConsignee, sqlSessionShipment);
			if (res == 0) {
				// consignee overseas data
				insertData("insertOverseasConsignee", overseasConsignee, sqlSessionShipment);
			} else {
				updateData("updateOverseasConsignee", overseasConsignee, sqlSessionShipment);
			}
		}

		// SHIPPER
		Integer freightWayBillShipperUpdateCount = updateData(
				MaintainFWBQueryIds.UPDATE_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getShipperInfo(),
				sqlSessionShipment);
		if (freightWayBillShipperUpdateCount == 0) {
			requestModel.getShipperInfo().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getShipperInfo(),
					sqlSessionShipment);
		}

		// Shipper Address
		requestModel.getShipperInfo().getCustomerAddressInfo().setShipmentFreightWayBillCustomerInfoId(
				requestModel.getShipperInfo().getShipmentFreightWayBillCustomerInfoId());
		Integer freightWayBillShipperAddressUpdateCount = updateData(
				MaintainFWBQueryIds.UPDATE_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
				requestModel.getShipperInfo().getCustomerAddressInfo(), sqlSessionShipment);

		if (freightWayBillShipperAddressUpdateCount == 0
				&& (Optional.ofNullable(requestModel.getShipperInfo().getCustomerName()).isPresent()
						&& requestModel.getShipperInfo().getCustomerName().length() != 0)) {
			insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
					requestModel.getShipperInfo().getCustomerAddressInfo(), sqlSessionShipment);

		}

		// f shipper contact save or update or delete contact info
		requestModel.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo().stream()
				.forEach(shpCont -> shpCont.setShipmentFreightWayBillCustomerAddressInfoId(requestModel.getShipperInfo()
						.getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId()));
		// insert contact info
		List<CustomerContactInfo> shpCreateContactData = requestModel.getShipperInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent() 
						&& Optional.ofNullable(e.getContactDetail()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() == null || e.getFlagCRUD().equalsIgnoreCase("C")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(shpCreateContactData)) {
			insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(), shpCreateContactData,
					sqlSessionShipment);
		}
		// update contact info
		List<CustomerContactInfo> shpUpdateContactData = requestModel.getShipperInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
						&& Optional.ofNullable(e.getContactDetail()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() != null && e.getFlagCRUD().equalsIgnoreCase("U")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(shpUpdateContactData)) {
			updateData(MaintainFWBQueryIds.UPDATE_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(), shpUpdateContactData,
					sqlSessionShipment);
		}

		List<CustomerContactInfo> shpDeleteContactData = requestModel.getShipperInfo().getCustomerAddressInfo()
				.getCustomerContactInfo().stream()
				.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
						&& e.getContactIdentifier().length() > 0)
				.filter(e -> e.getFlagCRUD() != null && e.getFlagCRUD().equalsIgnoreCase("D")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(shpDeleteContactData)) {
			deleteData(MaintainFWBQueryIds.DELETE_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(),shpDeleteContactData,
					sqlSessionShipment);
		}
			
		if (!ObjectUtils.isEmpty(requestModel.getAlsoNotify())
				&& (!StringUtils.isEmpty(requestModel.getAlsoNotify().getCustomerName()))) {
			// AlsoNotiify
			Integer freightWayBillAlsoNotifyUpdateCount = updateData(
					MaintainFWBQueryIds.UPDATE_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getAlsoNotify(),
					sqlSessionShipment);
			if (freightWayBillAlsoNotifyUpdateCount == 0
					&& (Optional.ofNullable(requestModel.getAlsoNotify().getCustomerName()).isPresent())) {
				requestModel.getAlsoNotify().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
				insertData(MaintainFWBQueryIds.INSERT_AWB_CUSTINFO_DETAILS.getQueryId(), requestModel.getAlsoNotify(),
						sqlSessionShipment);
			}

			// ALSO NOTIFY Address
			requestModel.getAlsoNotify().getCustomerAddressInfo().setShipmentFreightWayBillCustomerInfoId(
					requestModel.getAlsoNotify().getShipmentFreightWayBillCustomerInfoId());
			Integer freightWayBillAlsoNotifyAddressUpdateCount = updateData(
					MaintainFWBQueryIds.UPDATE_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
					requestModel.getAlsoNotify().getCustomerAddressInfo(), sqlSessionShipment);
			if (freightWayBillAlsoNotifyAddressUpdateCount == 0
					&& (Optional.ofNullable(requestModel.getAlsoNotify().getCustomerName()).isPresent()
							&& requestModel.getAlsoNotify().getCustomerName().length() != 0)
					&& (Optional.ofNullable(requestModel.getAlsoNotify().getCustomerAddressInfo().getCustomerPlace())
							.isPresent()
							&& requestModel.getAlsoNotify().getCustomerAddressInfo().getCustomerPlace().length() != 0)
					&& (Optional.ofNullable(requestModel.getAlsoNotify().getCustomerAddressInfo().getStreetAddress1())
							.isPresent()
							&& requestModel.getAlsoNotify().getCustomerAddressInfo().getStreetAddress1().length() != 0)
					&& (Optional.ofNullable(requestModel.getAlsoNotify().getCustomerAddressInfo().getCountryCode())
							.isPresent()
							&& requestModel.getAlsoNotify().getCustomerAddressInfo().getCountryCode().length() != 0)) {
				insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_ADD_INFO_DETAILS.getQueryId(),
						requestModel.getAlsoNotify().getCustomerAddressInfo(), sqlSessionShipment);

			}

			// also contact
			ArrayList<Integer> alsoDeleteCountData = deleteData(
					MaintainFWBQueryIds.DELETE_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(),
					requestModel.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo(), sqlSessionShipment);
			long alsoCnt = alsoDeleteCountData.stream().count();

			if (alsoCnt > 0) {
				requestModel.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo().stream().forEach(
						nfyCont -> nfyCont.setShipmentFreightWayBillCustomerAddressInfoId(requestModel.getAlsoNotify()
								.getCustomerAddressInfo().getShipmentFreightWayBillCustomerAddressInfoId()));
				List<CustomerContactInfo> alsoContactData = requestModel.getAlsoNotify().getCustomerAddressInfo()
						.getCustomerContactInfo().stream()
						.filter(e -> Optional.ofNullable(e.getContactIdentifier()).isPresent()
								&& Optional.ofNullable(e.getContactDetail()).isPresent()
								&& e.getContactIdentifier().length() > 0)
						.filter(e -> Optional.ofNullable(e.getFlagCRUD()).isPresent()
								&& !e.getFlagCRUD().equalsIgnoreCase("D"))
						.collect(Collectors.toList());
				insertData(MaintainFWBQueryIds.INSERT_AWB_CUST_CONTACT_INFO_DETAILS.getQueryId(), alsoContactData,
						sqlSessionShipment);
			}
		}
		// agent Info
		if ((Optional.ofNullable(requestModel.getAgentInfo().getAgentName()).isPresent()
				&& !StringUtils.isEmpty(requestModel.getAgentInfo().getAgentName()))
				|| (Optional.ofNullable(requestModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()
						&& !StringUtils.isEmpty(requestModel.getAgentInfo().getIATACargoAgentNumericCode()))
				|| (Optional.ofNullable(requestModel.getAgentInfo().getAgentPlace()).isPresent()
						&& !StringUtils.isEmpty(requestModel.getAgentInfo().getAgentPlace()))) {
			Integer agentUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_Agent_INFO_DETAILS.getQueryId(),
					requestModel.getAgentInfo(), sqlSessionShipment);
			if (agentUpdateCount == 0) {
				if ((Optional.ofNullable(requestModel.getAgentInfo().getAgentName()).isPresent()
						&& !StringUtils.isEmpty(requestModel.getAgentInfo().getAgentName()))
						&& (Optional.ofNullable(requestModel.getAgentInfo().getIATACargoAgentNumericCode()).isPresent()
								&& !StringUtils.isEmpty(requestModel.getAgentInfo().getIATACargoAgentNumericCode()))
						&& (Optional.ofNullable(requestModel.getAgentInfo().getAgentPlace()).isPresent()
								&& !StringUtils.isEmpty(requestModel.getAgentInfo().getAgentPlace()))) {
					requestModel.getAgentInfo().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					insertData(MaintainFWBQueryIds.INSERT_AWB_Agent_INFO_DETAILS.getQueryId(),
							requestModel.getAgentInfo(), sqlSessionShipment);
				}
			}
		}

		// 6. AccountingInfo Information
		for (AccountingInfo accountingInfo : requestModel.getAccountingInfo()) {
			accountingInfo.setFlagCRUD(Optional.ofNullable(accountingInfo.getFlagCRUD()).orElse("C"));
			switch (accountingInfo.getFlagCRUD()) {
			case MaintainFWBValidatorConstants.Type.CREATE:
				accountingInfo.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
				if ((!StringUtils.isEmpty(accountingInfo.getInformationIdentifier()))
						&& (!StringUtils.isEmpty(accountingInfo.getAccountingInformation())))
					insertData(MaintainFWBQueryIds.INSERT_AWB_ACCOUNTINGINFO.getQueryId(), accountingInfo,
							sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.UPDATE:
				updateData(MaintainFWBQueryIds.UPDATE_AWB_ACCOUNTINGINFO.getQueryId(), accountingInfo,
						sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.DELETE:
				deleteData(MaintainFWBQueryIds.DELETE_AWB_ACCOUNTINGINFO.getQueryId(), accountingInfo,
						sqlSessionShipment);
				break;
			default:
				break;
			}
		}

		for (SSROSIInfo ssrosi : requestModel.getSsrInfo()) {
			ssrosi.setFlagCRUD(Optional.ofNullable(ssrosi.getFlagCRUD()).orElse("C"));
			switch (ssrosi.getFlagCRUD()) {
			case MaintainFWBValidatorConstants.Type.CREATE:
				if (Optional.ofNullable(ssrosi.getServiceRequestcontent()).isPresent()
						&& !StringUtils.isEmpty(ssrosi.getServiceRequestcontent())) {
					ssrosi.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					ssrosi.setServiceRequestType(MaintainFWBValidatorConstants.Type.SSR);
					insertData(MaintainFWBQueryIds.INSERT_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				}
				break;
			case MaintainFWBValidatorConstants.Type.UPDATE:
				if (!Optional.ofNullable(ssrosi.getServiceRequestcontent()).isPresent()
						&& (StringUtils.isEmpty(ssrosi.getServiceRequestcontent())
								&& ssrosi.getShipmentFreightWayBillSSROSIInfoId() != 0)) {
					deleteData(MaintainFWBQueryIds.DELETE_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				} else {
					updateData(MaintainFWBQueryIds.UPDATE_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				}
				break;
			default:
				break;
			}
		}

		for (SSROSIInfo ssrosi : requestModel.getOsiInfo()) {
			ssrosi.setFlagCRUD(Optional.ofNullable(ssrosi.getFlagCRUD()).orElse("C"));
			switch (ssrosi.getFlagCRUD()) {
			case MaintainFWBValidatorConstants.Type.CREATE:
				if (Optional.ofNullable(ssrosi.getServiceRequestcontent()).isPresent()
						&& !StringUtils.isEmpty(ssrosi.getServiceRequestcontent())) {
					ssrosi.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					ssrosi.setServiceRequestType(MaintainFWBValidatorConstants.Type.OSI);
					insertData(MaintainFWBQueryIds.INSERT_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				}
				break;
			case MaintainFWBValidatorConstants.Type.UPDATE:
				if (!Optional.ofNullable(ssrosi.getServiceRequestcontent()).isPresent()
						&& StringUtils.isEmpty(ssrosi.getServiceRequestcontent())
						&& ssrosi.getShipmentFreightWayBillSSROSIInfoId() != 0) {
					deleteData(MaintainFWBQueryIds.DELETE_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				} else {
					updateData(MaintainFWBQueryIds.UPDATE_AWB_SSROSI_DETAILS.getQueryId(), ssrosi, sqlSessionShipment);
				}
				break;
			default:
				break;
			}
		}
		/* temp end */

		// 6. OCI Information
		for (OtherCustomsInfo oci : requestModel.getOtherCustomsInfo()) {
			if (((StringUtils.isEmpty(oci.getCountryCode())) && ((StringUtils.isEmpty(oci.getCsrciIdentifier()))
					&& (StringUtils.isEmpty(oci.getInformationIdentifier()))
					&& (StringUtils.isEmpty(oci.getScrcInformation()))))) {
				oci.setFlagCRUD("D");
			}
			if (!StringUtils.isEmpty(oci.getFlagCRUD())
					&& !oci.getFlagCRUD().equalsIgnoreCase(MaintainFWBValidatorConstants.Type.DELETE)
					&& (((!StringUtils.isEmpty(oci.getScrcInformation()))
							&& ((StringUtils.isEmpty(oci.getCsrciIdentifier()))
									&& (StringUtils.isEmpty(oci.getInformationIdentifier()))
									&& (StringUtils.isEmpty(oci.getCountryCode())))))) {
				requestModel.addError("awb.iso.code.information.identifier.customs",null, ErrorType.ERROR);
				throw new CustomException();
			}

			if (!StringUtils.isEmpty(oci.getFlagCRUD())
					&& !oci.getFlagCRUD().equalsIgnoreCase(MaintainFWBValidatorConstants.Type.DELETE)
					&& ((!StringUtils.isEmpty(oci.getCountryCode())) && ((StringUtils.isEmpty(oci.getCsrciIdentifier()))
							&& (StringUtils.isEmpty(oci.getInformationIdentifier()))
							&& (StringUtils.isEmpty(oci.getScrcInformation()))))) {
				requestModel.addError("awb.iso.code.information.identifier.scrc",null, ErrorType.ERROR);
				throw new CustomException();

			}
			
			if (!StringUtils.isEmpty(oci.getFlagCRUD())
					&& !oci.getFlagCRUD().equalsIgnoreCase(MaintainFWBValidatorConstants.Type.DELETE)
					&& ((StringUtils.isEmpty(oci.getScrcInformation())) && ((!StringUtils.isEmpty(oci.getCsrciIdentifier()))
							|| (!StringUtils.isEmpty(oci.getInformationIdentifier()))
							|| (!StringUtils.isEmpty(oci.getCountryCode()))))) {
				requestModel.addError("awb.supp.customs.sec.reg.inf.req",null, ErrorType.ERROR);
				throw new CustomException();

			}
			oci.setFlagCRUD(Optional.ofNullable(oci.getFlagCRUD()).orElse("C"));

			switch (oci.getFlagCRUD()) {
			case MaintainFWBValidatorConstants.Type.CREATE:
				oci.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
				insertData(MaintainFWBQueryIds.INSERT_AWB_OTHER_CUSTINFO.getQueryId(), oci, sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.UPDATE:
				updateData(MaintainFWBQueryIds.UPDATE_AWB_OTHER_CUSTINFO.getQueryId(), oci, sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.DELETE:
				deleteData(MaintainFWBQueryIds.DELETE_AWB_OTHER_CUSTINFO.getQueryId(), oci, sqlSessionShipment);
				break;
			default:
				break;
			}

		}
		// 7.Filght booking

		for (FlightBooking flightBooking : requestModel.getFlightBooking()) {
			if (flightBooking.getShipmentFreightWayBillFlightBookingId() > 0
					&& !"D".equalsIgnoreCase(flightBooking.getFlagCRUD())) {
				flightBooking.setFlagCRUD("U");
			} else if (StringUtils.isEmpty(flightBooking.getFlagCRUD())
					|| !"D".equalsIgnoreCase(flightBooking.getFlagCRUD())) {
				flightBooking.setFlagCRUD("C");
			}
			switch (flightBooking.getFlagCRUD()) {
      			case MaintainFWBValidatorConstants.Type.CREATE:
      				requestModel.getFlightBooking().forEach(item -> {
      					item.setShipmentFreightWayBillId(BigInteger.valueOf(requestModel.getShipmentFreightWayBillId()));
      				});
      
      				if (Optional.ofNullable(flightBooking.getFlightNumber()).isPresent()
      						&& Optional.ofNullable(flightBooking.getFlightDate()).isPresent()
      						&& flightBooking.getFlightNumber().length() > 0) {
      					insertData(MaintainFWBQueryIds.INSERT_AWB_FLIGHTBOOKING_DETAILS.getQueryId(), flightBooking,
      							sqlSessionShipment);
      				}
      				break;
      			case MaintainFWBValidatorConstants.Type.UPDATE:
      				if (Optional.ofNullable(flightBooking.getFlightNumber()).isPresent()
      						&& Optional.ofNullable(flightBooking.getFlightDate()).isPresent()) {
      					updateData(MaintainFWBQueryIds.UPDATE_AWB_FLIGHTBOOKING_DETAILS.getQueryId(), flightBooking,
      							sqlSessionShipment);
      				}
      				break;
      			case MaintainFWBValidatorConstants.Type.DELETE:
      				if (Optional.ofNullable(flightBooking.getFlightNumber()).isPresent()
      						&& Optional.ofNullable(flightBooking.getFlightDate()).isPresent()) {
      					updateData("deleteForUpdateAWBFlightBookingDetailsByOne", flightBooking, sqlSessionShipment);
      				}
      				break;
      			default:
      			   break;
			}
		}
		for (Routing routing : requestModel.getRouting()) {
			if (routing.getShipmentFreightWayBillRoutingId() > 0 && !"D".equalsIgnoreCase(routing.getFlagCRUD())) {
				routing.setFlagCRUD("U");
			} else if (StringUtils.isEmpty(routing.getFlagCRUD()) || !"D".equalsIgnoreCase(routing.getFlagCRUD())) {
				routing.setFlagCRUD("C");
			}
			switch (routing.getFlagCRUD()) {
      			case MaintainFWBValidatorConstants.Type.CREATE:
      				requestModel.getRouting().forEach(item -> {
      					item.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
      				});
      				if (Optional.ofNullable(routing.getAirportCode()).isPresent()
      						|| Optional.ofNullable(routing.getCarrierCode()).isPresent()) {
      					insertData(MaintainFWBQueryIds.INSERT_AWB_ROUTING.getQueryId(), routing, sqlSessionShipment);
      				}
      				break;
      			case MaintainFWBValidatorConstants.Type.UPDATE:
      				if (Optional.ofNullable(routing.getAirportCode()).isPresent()) {
      					updateData(MaintainFWBQueryIds.UPDATE_AWB_ROUTING.getQueryId(), routing, sqlSessionShipment);
      				}else {
      					this.deleteData(MaintainFWBQueryIds.DELETE_AWB_ROUTING.getQueryId(), routing, sqlSessionShipment);
      				}
      				break;
      			default:
      			   break;
			}
		}
		// Charge Declaration
		if (!StringUtils.isEmpty(requestModel.getChargeDeclaration().getCarriageValueDeclaration())) {
			requestModel.getChargeDeclaration().setFwbCarriageValueDeclaration(requestModel.getChargeDeclaration().getCarriageValueDeclaration());
		}else {
			requestModel.getChargeDeclaration().setFwbCarriageValueDeclaration(null);
		}
		if (!StringUtils.isEmpty(requestModel.getChargeDeclaration().getCustomsValueDeclaration())) {	
			requestModel.getChargeDeclaration().setFwbCustomsValueDeclaration(requestModel.getChargeDeclaration().getCustomsValueDeclaration());
		}else {
			requestModel.getChargeDeclaration().setFwbCustomsValueDeclaration(null);
		}
		if (!StringUtils.isEmpty(requestModel.getChargeDeclaration().getInsuranceValueDeclaration())) {
			requestModel.getChargeDeclaration().setFwbInsuranceValueDeclaration(requestModel.getChargeDeclaration().getInsuranceValueDeclaration());
		}else {
			requestModel.getChargeDeclaration().setFwbInsuranceValueDeclaration(null);
		}
		Boolean isvalidCurency=this.fetchObject("sqlIsValidCurrencyFWB", requestModel.getChargeDeclaration(), sqlSessionShipment);
		if(!isvalidCurency) {
			requestModel.addError("awb.invalid.currency.code", "currencyCode", ErrorType.ERROR);
			throw new CustomException();
		}
		Integer chargeDeclUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_CHARGE_DECLARATION_DETAILS.getQueryId(),
				requestModel.getChargeDeclaration(), sqlSessionShipment);
		if (chargeDeclUpdateCount == 0) {
			requestModel.getChargeDeclaration().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			// NVD NVC XXX
			if (!Optional.ofNullable(requestModel.getChargeDeclaration().getCarriageValueDeclaration()).isPresent()) {
				requestModel.getChargeDeclaration().setCarriageValueDeclaration("NVD");
			}
			if (!Optional.ofNullable(requestModel.getChargeDeclaration().getCustomsValueDeclaration()).isPresent()) {
				requestModel.getChargeDeclaration().setCustomsValueDeclaration("NCV");
			}
			if (!Optional.ofNullable(requestModel.getChargeDeclaration().getInsuranceValueDeclaration()).isPresent()) {
				requestModel.getChargeDeclaration().setInsuranceValueDeclaration("XXX");
			}

			insertData(MaintainFWBQueryIds.INSERT_AWB_CHARGE_DECLARATION_DETAILS.getQueryId(),
					requestModel.getChargeDeclaration(), sqlSessionShipment);
		}

		// RATE Description
		if (!CollectionUtils.isEmpty(requestModel.getRateDescription())) {
			if(StringUtils.isEmpty(requestModel.getRateDescription().get(0).getRateLineNumber())) {
				requestModel.getRateDescription().get(0).setRateLineNumber("1");
			}
			for (RateDescription rate : requestModel.getRateDescription()) {
				Integer rtdUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_RATE_DESCRIPTION.getQueryId(), rate,
						sqlSessionShipment);
				if (rtdUpdateCount == 0) {
					rate.setFlagCRUD(Optional.ofNullable(rate.getFlagCRUD()).orElse("C"));
					rate.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					insertData(MaintainFWBQueryIds.INSERT_AWB_RATE_DESCRIPTION.getQueryId(), rate, sqlSessionShipment);
				}
				if (rate.getFlagCRUD().equalsIgnoreCase("D")) {
					deleteData("deleteForUpdateAWBRateDescOtherInfoAll", rate,sqlSessionShipment);
					deleteData(MaintainFWBQueryIds.DELETE_AWB_RATE_DESCRIPTION.getQueryId(), rate, sqlSessionShipment);
				}
				// RATE Description OTHER INFO
				if (!CollectionUtils.isEmpty(rate.getRateDescriptionOtherInfo())) {
					for (RateDescOtherInfo rateDescOtherInfo : rate.getRateDescriptionOtherInfo()) {

						rateDescOtherInfo.setFlagCRUD(Optional.ofNullable(rateDescOtherInfo.getFlagCRUD()).orElse("C"));
						switch (rateDescOtherInfo.getFlagCRUD()) {
						case MaintainFWBValidatorConstants.Type.CREATE:
							rateDescOtherInfo.setShipmentFreightWayBillRateDescriptionId(
									rate.getShipmentFreightWayBillRateDescriptionId());
							rateDescOtherInfo.setMeasurementUnitCode(" ");
							rateDescOtherInfo.setCountryCode("AE");
							insertData(MaintainFWBQueryIds.INSERT_AWB_RATE_DESC_OTHER.getQueryId(), rateDescOtherInfo,
									sqlSessionShipment);
							break;
						case MaintainFWBValidatorConstants.Type.UPDATE:
							updateData(MaintainFWBQueryIds.UPDATE_AWB_RATE_DESC_OTHER.getQueryId(), rateDescOtherInfo,
									sqlSessionShipment);
							break;
						case MaintainFWBValidatorConstants.Type.DELETE:
							deleteData(MaintainFWBQueryIds.DELETE_AWB_RATE_DESC_OTHER.getQueryId(), rateDescOtherInfo,
									sqlSessionShipment);
							break;
						default:
							break;
						}

					}
				}
			}
		}
		// OTHER Charges
		for (OtherCharges otherCharges : requestModel.getOtherCharges()) {
			otherCharges.setFlagCRUD(Optional.ofNullable(otherCharges.getFlagCRUD()).orElse("C"));
			if (!StringUtils.isEmpty(otherCharges.getOtherChargeCode())) {
				switch (otherCharges.getFlagCRUD()) {
				case MaintainFWBValidatorConstants.Type.CREATE:
					otherCharges.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					insertData(MaintainFWBQueryIds.INSERT_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				case MaintainFWBValidatorConstants.Type.UPDATE:
					updateData(MaintainFWBQueryIds.UPDATE_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				case MaintainFWBValidatorConstants.Type.DELETE:
					deleteData(MaintainFWBQueryIds.DELETE_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				default:
					break;
				}
			}
		}
		// OTHER Charges
		for (OtherCharges otherCharges : requestModel.getOtherChargesCarrier()) {
			otherCharges.setFlagCRUD(Optional.ofNullable(otherCharges.getFlagCRUD()).orElse("C"));
			if (!StringUtils.isEmpty(otherCharges.getOtherChargeCode())) {
				switch (otherCharges.getFlagCRUD()) {
				case MaintainFWBValidatorConstants.Type.CREATE:
					otherCharges.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
					insertData(MaintainFWBQueryIds.INSERT_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				case MaintainFWBValidatorConstants.Type.UPDATE:
					updateData(MaintainFWBQueryIds.UPDATE_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				case MaintainFWBValidatorConstants.Type.DELETE:
					deleteData(MaintainFWBQueryIds.DELETE_AWB_OTHER_CHARGES.getQueryId(), otherCharges,
							sqlSessionShipment);
					break;
				default:
					break;
				}
			}
		}
		// CDC NEW
		Integer cdcCount = updateData("updateAWBChargeDestCurrency", requestModel.getChargeDestCurrency(),
				sqlSessionShipment);
		if (cdcCount == 0) {
			requestModel.getChargeDestCurrency()
					.setFlagCRUD(Optional.ofNullable(requestModel.getChargeDestCurrency().getFlagCRUD()).orElse("C"));
			requestModel.getChargeDestCurrency()
					.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			if ((!StringUtils.isEmpty(requestModel.getChargeDestCurrency().getDestinationCountryCode()))
					&& (!StringUtils.isEmpty(requestModel.getChargeDestCurrency().getDestinationCurrencyCode()))
					&& (!StringUtils.isEmpty(requestModel.getChargeDestCurrency().getCurrencyConversionExchangeRate()))
					&& (!StringUtils
							.isEmpty(requestModel.getChargeDestCurrency().getChargesAtDestinationChargeAmount()))
					&& (!StringUtils.isEmpty(requestModel.getChargeDestCurrency().getDestinationCurrencyChargeAmount()))
					&& (!StringUtils
							.isEmpty(requestModel.getChargeDestCurrency().getTotalCollectChargesChargeAmount()))) {
				insertData("insertAWBChargesDestinationCurrency", requestModel.getChargeDestCurrency(),
						sqlSessionShipment);
			}
		}

		// PPD
		// Temp
		if (!Optional.ofNullable(requestModel.getPpd().getChargeTypeLineIdentifier()).isPresent()) {
			requestModel.getPpd().setChargeTypeLineIdentifier("PPD");
		}
		Integer ppdUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_PREPAID_COLLECT_CHARGE_SUMMARY.getQueryId(),
				requestModel.getPpd(), sqlSessionShipment);
		if (ppdUpdateCount == 0 && Optional.ofNullable(requestModel.getPpd()).isPresent()) {
			requestModel.getPpd().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_PREPAID_COLLECT_CHARGE_SUMMARY.getQueryId(),
					requestModel.getPpd(), sqlSessionShipment);
		}
		// COL
		// Temp
		if (!Optional.ofNullable(requestModel.getCol().getChargeTypeLineIdentifier()).isPresent()) {
			requestModel.getCol().setChargeTypeLineIdentifier("COL");
		}

		Integer colUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_PREPAID_COLLECT_CHARGE_SUMMARY.getQueryId(),
				requestModel.getCol(), sqlSessionShipment);
		if(Optional.ofNullable(requestModel.getCol()).isPresent()) {
			requestModel.getCol().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
		}
		Boolean colExisted =fetchObject("sqlgetAWBPrepaidCollectChargeSummary", requestModel.getCol(), sqlSessionShipment);
		if (!colExisted && Optional.ofNullable(requestModel.getCol()).isPresent()) {
			requestModel.getCol().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_PREPAID_COLLECT_CHARGE_SUMMARY.getQueryId(),
					requestModel.getCol(), sqlSessionShipment);
		}

		// NominatedHandlingParty
		// temp
		if (!Optional.ofNullable(requestModel.getFwbNominatedHandlingParty().getHandlingPartyName()).isPresent()) {
			requestModel.getFwbNominatedHandlingParty().setHandlingPartyName("PARTY");
		}
		if (!Optional.ofNullable(requestModel.getFwbNominatedHandlingParty().getHandlingPartyPlace()).isPresent()) {
			requestModel.getFwbNominatedHandlingParty().setHandlingPartyPlace("PARTY BLR");
		}
		Integer nominateHabdkingUpdateCount = updateData(
				MaintainFWBQueryIds.UPDATE_AWB_NOMINATE_HANDLING_PARTY.getQueryId(),
				requestModel.getFwbNominatedHandlingParty(), sqlSessionShipment);
		if (nominateHabdkingUpdateCount == 0) {

			requestModel.getFwbNominatedHandlingParty()
					.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_NOMINATE_HANDLING_PARTY.getQueryId(),
					requestModel.getFwbNominatedHandlingParty(), sqlSessionShipment);
		}

		// ShpReferenceInformation
		Integer ShpReferInfoUpdateCount = updateData(MaintainFWBQueryIds.UPDATE_AWB_OTHER_SHP_REF_INFO.getQueryId(),
				requestModel.getShipmentReferenceInfor(), sqlSessionShipment);
		if(!ObjectUtils.isEmpty(requestModel.getShipmentReferenceInfor())) {
			requestModel.getShipmentReferenceInfor().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
		}
		Boolean shpReferenceInformationExisted =fetchObject("sqlgetShpReferenceInformation", requestModel.getShipmentReferenceInfor(), sqlSessionShipment);
		if (!shpReferenceInformationExisted) {
			requestModel.getShipmentReferenceInfor()
					.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData(MaintainFWBQueryIds.INSERT_AWB_OTHER_SHP_REF_INFO.getQueryId(),
					requestModel.getShipmentReferenceInfor(), sqlSessionShipment);
		}
		// OTHER Charges
		for (OtherParticipantInfo otherParticipantInfo : requestModel.getOtherParticipantInfo()) {
			otherParticipantInfo.setFlagCRUD(Optional.ofNullable(otherParticipantInfo.getFlagCRUD()).orElse("C"));
			switch (otherParticipantInfo.getFlagCRUD()) {
			case MaintainFWBValidatorConstants.Type.CREATE:
				otherParticipantInfo.setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
				insertData(MaintainFWBQueryIds.INSERT_AWB_OTHER_PARTICIPANT_INFO.getQueryId(), otherParticipantInfo,
						sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.UPDATE:
				updateData(MaintainFWBQueryIds.UPDATE_AWB_OTHER_PARTICIPANT_INFO.getQueryId(), otherParticipantInfo,
						sqlSessionShipment);
				break;
			case MaintainFWBValidatorConstants.Type.DELETE:
				deleteData(MaintainFWBQueryIds.DELETE_AWB_OTHER_PARTICIPANT_INFO.getQueryId(), otherParticipantInfo,
						sqlSessionShipment);
				break;
			default:
				break;
			}
		}

		Integer refCount = updateData("updateAWBSenderRef", requestModel.getSenderReference(), sqlSessionShipment);
		requestModel.getSenderReference().setFileReference(
				fetchObject("sqlGetSenderFileReference", requestModel.getSenderReference(), sqlSessionShipment));
		requestModel.getSenderReference().setRefParticipantIdentifier(
				fetchObject("sqlGetSenderIdentifier", requestModel.getSenderReference(), sqlSessionShipment));
		requestModel.getSenderReference().setRefParticipantCode(
				fetchObject("sqlGetSenderCode", requestModel.getSenderReference(), sqlSessionShipment));
		requestModel.getSenderReference().setRefAirportCityCode(
				fetchObject("sqlGetSenderAirport", requestModel.getSenderReference(), sqlSessionShipment));
		if (StringUtils.isEmpty(requestModel.getSenderReference().getAirportCityCode())) {
			String defaultAddress = this.fetchObject("sqlGetSenderDefaultAddress", requestModel.getSenderReference(),
					sqlSessionShipment);
			if (defaultAddress.length() >= 7) {
				requestModel.getSenderReference().setAirportCityCode(defaultAddress.substring(0, 3));
				requestModel.getSenderReference().setOfficeFunctionDesignator(defaultAddress.substring(3, 5));
				requestModel.getSenderReference().setCompanyDesignator(defaultAddress.substring(5, 7));
			}
		}

		if (refCount == 0) {
			requestModel.getSenderReference()
					.setFlagCRUD(Optional.ofNullable(requestModel.getSenderReference().getFlagCRUD()).orElse("C"));
			requestModel.getSenderReference().setShipmentFreightWayBillId(requestModel.getShipmentFreightWayBillId());
			insertData("insertAWBSenderRef", requestModel.getSenderReference(), sqlSessionShipment);

		}

		//

		return requestModel;
	}

	@Override
	public FWB delete(FWB requestModel) throws CustomException {

		// get AWB AND DELETE
		AWB shipmentMaster = new AWB();
		shipmentMaster.setShipmentNumber(requestModel.getAwbNumber());
		shipmentMaster.setShipmentdate(requestModel.getAwbDate());
		AWB awbData = daoAwb.getShipment(shipmentMaster);

		daoAwb.deleteAwbDocumentFromFWB(awbData);
		// delete ref
		deleteData("deleteAWBSenderRef", requestModel.getSenderReference(), sqlSessionShipment);

		// DELET DENSITY CURRENCY
		deleteData("deleteForUpdateChargesDestinationCurrency", requestModel.getChargeDestCurrency(),
				sqlSessionShipment);
		// delete other customs info
		deleteData("deleteAWBOtherCustomsInfo", requestModel.getOtherCustomsInfo(), sqlSessionShipment);

		// other participant info
		deleteData("deleteAWBOtherParticipantInfo", requestModel.getOtherParticipantInfo(), sqlSessionShipment);

		// NominatedHandlingParty
		deleteData("deleteForUpdateAWBNominatedHandlingParty", requestModel.getFwbNominatedHandlingParty(),
				sqlSessionShipment);

		// ShpReferenceInformation
		deleteData("deleteForUpdateAWBShpReferInfo", requestModel.getShipmentReferenceInfor(), sqlSessionShipment);

		// other charges
		deleteData("deleteAWBOtherChargesWithid", requestModel, sqlSessionShipment);

		// ppd
		deleteData("deleteForUpdateAWBPrepaidCollectChargeSummary", requestModel.getPpd(), sqlSessionShipment);

		// col
		deleteData("deleteForUpdateAWBPrepaidCollectChargeSummary", requestModel.getCol(), sqlSessionShipment);
		
		// rate description other info
		deleteData("deleteForUpdateAWBRateDescOtherInfoAll", requestModel.getRateDescription(),sqlSessionShipment);
		deleteData(MaintainFWBQueryIds.DELETE_AWB_RATE_DESCRIPTION.getQueryId(), requestModel.getRateDescription(), sqlSessionShipment);
		
		// account info
		deleteData("deleteAWBAccountingInfo", requestModel.getAccountingInfo(), sqlSessionShipment);

		// charge declaration
		deleteData("deleteForUpdateAWBChargeDeclaration", requestModel.getChargeDeclaration(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperContactInfoDetails",
				requestModel.getAlsoNotify().getCustomerAddressInfo().getCustomerContactInfo(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperAddInfoDetails", requestModel.getAlsoNotify().getCustomerAddressInfo(),
				sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperInfoDetails", requestModel.getAlsoNotify(), sqlSessionShipment);

		// ssri osi
		deleteData("deleteForUpdateAWBSSRDetails", requestModel.getSsrInfo(), sqlSessionShipment);
		deleteData("deleteForUpdateAWBOSIDetails", requestModel.getOsiInfo(), sqlSessionShipment);

		// agent info
		deleteData("deleteForUpdateAWBAgentInfoDetails", requestModel.getAgentInfo(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperContactInfoDetails",
				requestModel.getConsigneeInfo().getCustomerAddressInfo().getCustomerContactInfo(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperAddInfoDetails", requestModel.getConsigneeInfo().getCustomerAddressInfo(),
				sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperContactInfoDetails",
				requestModel.getShipperInfo().getCustomerAddressInfo().getCustomerContactInfo(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperAddInfoDetails", requestModel.getShipperInfo().getCustomerAddressInfo(),
				sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperInfoDetails", requestModel.getShipperInfo(), sqlSessionShipment);

		deleteData("deleteForUpdateAWBShipperInfoDetails", requestModel.getAlsoNotify(), sqlSessionShipment);
		deleteData("deleteForUpdateAWBShipperInfoDetails", requestModel.getConsigneeInfo(), sqlSessionShipment);
		// Routing
		deleteData("deleteForUpdateAWBRouting", requestModel.getRouting(), sqlSessionShipment);

		// flight booking deletion
		deleteData("deleteForUpdateAWBFlightBookingDetails", requestModel.getFlightBooking(), sqlSessionShipment);

		// Special Handling Code
		deleteData("deleteForUpdateAWBSHCDetails", requestModel.getShcode(), sqlSessionShipment);

		// Delete fwb
		deleteData("deleteAWBDetails", requestModel, sqlSessionShipment);

		return requestModel;
	}

	@Override
	public Integer isValidCountry(String code) throws CustomException {
		Integer count = super.fetchObject("checkValidCountry", code, sqlSessionShipment);
		return count;
	}

	@Override
	public Routing getRoutingData(FetchRouting requestModel) throws CustomException {
		int j = super.fetchObject("checkFlightExits", requestModel, sqlSessionShipment);
		if (j == 0) {
			return null;
		} else {
			Routing route = fetchObject("getFlightBookingDetails", requestModel, sqlSessionShipment);
			if (route == null) {
				route = fetchObject("getFlightFirstDestination", requestModel, sqlSessionShipment);
				if (route.getToDest().equals(requestModel.getAirportCode())) {
					return route;
				}

				requestModel.setFromOrigin(route.getToDest());
				requestModel.setSvc(route.getSvc());
				Routing firstRoute = route;
				firstRoute.setFromOrigin(requestModel.getFromOrigin());
				firstRoute.setToDest(requestModel.getAirportCode());
				// firstRoute.setT
				route = fetchObject("getFlightScheduleDetails", requestModel, sqlSessionShipment);
				if (route == null) {
					route = fetchObject("routingNormal", requestModel, sqlSessionShipment);
					if (route != null) {
						route.setFromOrigin(requestModel.getFromOrigin());
						route.setCarrierCode(requestModel.getCarrierCode());
					} else {
						route = fetchObject("routingSpecial", requestModel, sqlSessionShipment);
						if (route != null) {
							route.setCarrierCode(requestModel.getCarrierCode());
						} else {
							firstRoute.setTocarrierCode(null);
							return firstRoute;
						}
					}
					return route;

				}
				return route;
			}
			return route;
		}
	}

	@Override
	public FWB fwbOnSearch(String awbNumber) throws CustomException {
		return fetchObject("fwbOnSearch", awbNumber, sqlSessionShipment);
	}

	@Override
	public FWBDetails fetchFWBDetailsForMobile(FWBDetails requestModel) throws CustomException {
		return fetchObject("fetchFWBDetailsForMobile", requestModel, sqlSessionShipment);
	}

	@Override
	public FWBDetails saveFWBDetailsForMobile(FWBDetails requestModel) throws CustomException {
		updateData("saveFWBDetailsForMobile", requestModel, sqlSessionShipment);
		return requestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#isShipmentAnEAWB(com.ngen.cosys.
	 * shipment.model.FWB)
	 */
	@Override
	public Boolean isShipmentAnEAWB(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlGetFWBSHCAnEAWB", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#isPartShipment(com.ngen.cosys.
	 * shipment.model.FWB)
	 */
	@Override
	public Boolean isPartShipment(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlCheckFWBForPartShipment", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainFWBDAO#isSVCShipment(com.ngen.cosys.
	 * shipment.model.FWB)
	 */
	@Override
	public Boolean isSVCShipment(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlCheckFWBForSVCShipment", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#isAirportBelongsToChina(com.ngen.
	 * cosys.shipment.model.FWB)
	 */
	@Override
	public Boolean isAirportBelongsToChina(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlCheckFWBAirportBelongsToChina", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#isShipmentDeliveryInitiated(com.
	 * ngen.cosys.shipment.model.FWB)
	 */
	@Override
	public Boolean isShipmentDeliveryInitiated(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlCheckShipmentDeliveryInitiated", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#getShipmentInfoForCharges(com.ngen
	 * .cosys.shipment.model.FWB)
	 */
	@Override
	public FWB getShipmentInfoForCharges(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlGetFWBShipmentInfoForCharges", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainFWBDAO#getAcceptanceInfo(com.ngen.cosys.
	 * shipment.model.FWB)
	 */
	@Override
	public FWB getAcceptanceInfo(FWB requestModel) throws CustomException {
		return this.fetchObject("sqlGetFWBEAcceptanceDocumentInformation", requestModel, sqlSessionShipment);
	}
	
	@Override
	public BigInteger getShipmentLodedinfo(AWB requestModel) throws CustomException {
		return this.fetchObject("sqlGetShipmentLoadedInfo", requestModel, sqlSessionShipment);
	}

}