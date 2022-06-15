package com.ngen.cosys.shipment.house.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.shipment.enums.CRUDStatus;
import com.ngen.cosys.shipment.enums.MaintainHouseQueryIds;
import com.ngen.cosys.shipment.house.model.HouseCustomerContactsModel;
import com.ngen.cosys.shipment.house.model.HouseCustomerModel;
import com.ngen.cosys.shipment.house.model.HouseDescriptionOfGoodsModel;
import com.ngen.cosys.shipment.house.model.HouseDimensionDetailsModel;
import com.ngen.cosys.shipment.house.model.HouseDimensionModel;
import com.ngen.cosys.shipment.house.model.HouseHarmonisedTariffScheduleModel;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseOtherChargeDeclarationModel;
import com.ngen.cosys.shipment.house.model.HouseOtherCustomsInformationModel;
import com.ngen.cosys.shipment.house.model.HouseSearch;
import com.ngen.cosys.shipment.house.model.HouseSpecialHandlingCodeModel;
import com.ngen.cosys.shipment.house.model.MasterAirWayBillModel;

@Repository("maintainHouseDAO")
public class MaintainHouseDAOImpl extends BaseDAO implements MaintainHouseDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	@Autowired
	private DomesticInternationalHelper domesticInternationalHelper;

	@Autowired
	private HAWBHandlingHelper hawbHandlingHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#isAirportBelongsToChina(
	 * com.ngen.cosys.shipment.house.model.HouseModel)
	 */
	@Override
	public Boolean isAirportBelongsToChina(HouseModel houseRequestModel) throws CustomException {
		return this.fetchObject(MaintainHouseQueryIds.SQL_CHECK_AIRPORT_BELONGS_TO_CHINA.getQueryId(),
				houseRequestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#getMasterAWBInformation(
	 * com.ngen.cosys.shipment.house.model.MasterAirWayBillModel)
	 */
	@Override
	public MasterAirWayBillModel getMasterAWBInformation(MasterAirWayBillModel masterAWBRequestModel)
			throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GET_MAWB_HAWB_INFO.getQueryId(), masterAWBRequestModel,
				sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#getHouseAWBInformation(com
	 * .ngen.cosys.shipment.house.model.HouseModel)
	 */
	// @Override
	public HouseModel getHouseAWBInformation(HouseModel houseRequestModel) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_INFO.getQueryId(), houseRequestModel,
				sqlSessionShipment);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveMasterAWB(com.ngen.
	 * cosys.shipment.house.model.MasterAirWayBillModel)
	 */
//   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public MasterAirWayBillModel saveMasterAWB(MasterAirWayBillModel masterAWBRequestModel) throws CustomException {
		MasterAirWayBillModel existingMAWBData = super.fetchObject(
				MaintainHouseQueryIds.SQL_GET_HOUSE_MAWB_ID.getQueryId(), masterAWBRequestModel, sqlSessionShipment);

		// Check if entity exists
		Optional<MasterAirWayBillModel> o = Optional.ofNullable(existingMAWBData);

		if (!o.isPresent()) {

			insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_MAWB.getQueryId(), masterAWBRequestModel,
					sqlSessionShipment);

			// set the indicator
			masterAWBRequestModel.setFirstTimeSave(true);

			if (!o.isPresent()) {
				// derive Domestic && International
				DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
				domesticInternationalHelperRequest.setOrigin(masterAWBRequestModel.getOrigin());
				domesticInternationalHelperRequest.setDestination(masterAWBRequestModel.getDestination());
				String domesticOrInternational = domesticInternationalHelper
						.getDOMINTHandling(domesticInternationalHelperRequest);

				// set domesticOrInternational value
				masterAWBRequestModel.setHandledByDOMINT(domesticOrInternational);

				// derive HandledByMasterHouse
				HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
				hAWBHandlingHelperRequest.setShipmentNumber(masterAWBRequestModel.getAwbNumber());
				hAWBHandlingHelperRequest.setShipmentDate(masterAWBRequestModel.getAwbDate());
				hAWBHandlingHelperRequest.setOrigin(masterAWBRequestModel.getOrigin());
				hAWBHandlingHelperRequest.setDestination(masterAWBRequestModel.getDestination());
				String handledByMasterHouse = hawbHandlingHelper.getHandledByMasterHouse(hAWBHandlingHelperRequest);

				// validate AWB document and shipmentInvenory and freightOut
				Boolean dataExist = super.fetchObject("sqlValidateAWBDocInvFreightInfo", masterAWBRequestModel,
						sqlSessionShipment);

				// set handledByMasterHouse value
				if (!dataExist && !StringUtils.isEmpty(handledByMasterHouse)) {
					masterAWBRequestModel.setHandledByMasterHouse(handledByMasterHouse);
				} else {
					masterAWBRequestModel.setHandledByMasterHouse(null);
				}

				this.updateData("updateHouseInfoForAWBThroughMAWB", masterAWBRequestModel, sqlSessionShipment);
			}
		} else {
			// Set the id
			masterAWBRequestModel.setId(o.get().getId());
		}
		return masterAWBRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveHouse(com.ngen.cosys.
	 * shipment.house.model.HouseModel)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public HouseModel saveHouse(HouseModel houseRequestModel) throws CustomException {

		int hawbCount = super.fetchObject("getHAWBCount", houseRequestModel, sqlSessionShipment);
		if (hawbCount > 0) {
			super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE.getQueryId(), houseRequestModel,
					sqlSessionShipment);
		} else {
			// Set the apply charge for manual creation
			houseRequestModel.setApplyCharge(Boolean.TRUE);
			super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE.getQueryId(), houseRequestModel,
					sqlSessionShipment);
		}
		return houseRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveHouseCustomer(com.ngen
	 * .cosys.shipment.house.model.HouseCustomerModel)
	 */
	@Override
	public HouseCustomerModel saveHouseCustomer(HouseCustomerModel houseCustomerRequestModel) throws CustomException {
		// Customer Info
		Integer updateRecordCount = super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_CUSTOMER.getQueryId(),
				houseCustomerRequestModel, sqlSessionShipment);
		if (updateRecordCount == 0) {
			super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_CUSTOMER.getQueryId(), houseCustomerRequestModel,
					sqlSessionShipment);
		}

		houseCustomerRequestModel.getAddress().setHouseCustomerId(houseCustomerRequestModel.getId());
		// Address Info
		Integer updateAddressRecordCount = super.updateData(
				MaintainHouseQueryIds.SQL_UPDATE_HOUSE_CUSTOMER_ADDRESS.getQueryId(),
				houseCustomerRequestModel.getAddress(), sqlSessionShipment);

		if (updateAddressRecordCount == 0) {
			super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_CUSTOMER_ADDRESS.getQueryId(),
					houseCustomerRequestModel.getAddress(), sqlSessionShipment);
		}

		// Delete all contacts
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_CUSTOMER_ADDRESS_CONTACT.getQueryId(),
				houseCustomerRequestModel.getAddress(), sqlSessionShipment);

		// Contact Info
		for (HouseCustomerContactsModel contact : houseCustomerRequestModel.getAddress().getContacts()) {
			if (!StringUtils.isEmpty(contact.getType()) && !StringUtils.isEmpty(contact.getDetail())) {
				contact.setHouseCustomerAddressId(houseCustomerRequestModel.getAddress().getId());
				super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_CUSTOMER_ADDRESS_CONTACT.getQueryId(), contact,
						sqlSessionShipment);
			}
		}
		return houseCustomerRequestModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#
	 * saveHouseDescriptionOfGoods(java.util.List)
	 */
	@Override
	public void saveHouseDescriptionOfGoods(List<HouseDescriptionOfGoodsModel> houseDescriptionOfGoodsRequestModel)
			throws CustomException {
		for (HouseDescriptionOfGoodsModel t : houseDescriptionOfGoodsRequestModel) {
			if (!StringUtils.isEmpty(t.getContent())) {
				switch (t.getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_DESCRIPTIONOFGOODS.getQueryId(), t,
							sqlSessionShipment);
					break;
				case CRUDStatus.CRUDType.UPDATE:
					super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_DESCRIPTIONOFGOODS.getQueryId(), t,
							sqlSessionShipment);
					break;
				case CRUDStatus.CRUDType.DELETE:
					super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_DESCRIPTIONOFGOODS.getQueryId(), t,
							sqlSessionShipment);
					break;
				default:
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveHouseOtherCustomInfo(
	 * java.util.List)
	 */
	@Override
	public void saveHouseOtherCustomInfo(
			List<HouseOtherCustomsInformationModel> houseOtherCustomsInformationRequestModel) throws CustomException {
		for (HouseOtherCustomsInformationModel otherInfo : houseOtherCustomsInformationRequestModel) {
			switch (otherInfo.getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_OTHERCUSTOMSINFORMATION.getQueryId(), otherInfo,
						sqlSessionShipment);
				break;
			case CRUDStatus.CRUDType.UPDATE:
				super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_OTHERCUSTOMSINFORMATION.getQueryId(), otherInfo,
						sqlSessionShipment);
				break;
			case CRUDStatus.CRUDType.DELETE:
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_OTHERCUSTOMSINFORMATION.getQueryId(), otherInfo,
						sqlSessionShipment);
				break;
			default:
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#
	 * saveHarmonisedTariffScheduleInfo(java.util.List)
	 */
	@Override
	public void saveHarmonisedTariffScheduleInfo(
			List<HouseHarmonisedTariffScheduleModel> houseHarmonisedTariffScheduleRequestModel) throws CustomException {
		for (HouseHarmonisedTariffScheduleModel t : houseHarmonisedTariffScheduleRequestModel) {
			if (!StringUtils.isEmpty(t.getCode())) {
				switch (t.getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_TARIFF.getQueryId(), t, sqlSessionShipment);
					break;
				case CRUDStatus.CRUDType.UPDATE:
					super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_TARIFF.getQueryId(), t, sqlSessionShipment);
					break;
				case CRUDStatus.CRUDType.DELETE:
					super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_TARIFF.getQueryId(), t, sqlSessionShipment);
					break;
				default:
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveHouseSHC(com.ngen.
	 * cosys.shipment.house.model.HouseModel, java.util.List)
	 */
	@Override
	public void saveHouseSHC(HouseModel houseModel,
			List<HouseSpecialHandlingCodeModel> houseSpecialHandlingCodeRequestModel) throws CustomException {
		// Delete the SHC
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_HOUSE_SHC.getQueryId(), houseModel, sqlSessionShipment);

		for (HouseSpecialHandlingCodeModel houseSpecialHandlingCodeModel : houseSpecialHandlingCodeRequestModel) {
			if (!StringUtils.isEmpty(houseSpecialHandlingCodeModel.getCode())) {
				// update the SHC
				Integer updateRecordCount = super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_SHC.getQueryId(),
						houseSpecialHandlingCodeModel, sqlSessionShipment);
				// Re-insert the SHC
				if (updateRecordCount == 0) {
					super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_SHC.getQueryId(),
							houseSpecialHandlingCodeModel, sqlSessionShipment);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#saveHouseChargeDeclaration
	 * (com.ngen.cosys.shipment.house.model.HouseOtherChargeDeclarationModel)
	 */
	@Override
	public void saveHouseChargeDeclaration(HouseOtherChargeDeclarationModel houseOtherChargeDeclarationModel)
			throws CustomException {
		if (houseOtherChargeDeclarationModel.getId() != null) {
			super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_OTHERCHARGES.getQueryId(),
					houseOtherChargeDeclarationModel, sqlSessionShipment);
		} else {
			super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_OTHERCHARGES.getQueryId(),
					houseOtherChargeDeclarationModel, sqlSessionShipment);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#delete(com.ngen.cosys.
	 * shipment.house.model.MasterAirWayBillModel)
	 */
	@Override
//   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public void delete(MasterAirWayBillModel masterAWBRequestModel) throws CustomException {

		for (HouseModel t : masterAWBRequestModel.getMaintainHouseDetailsList()) {
			if (t.getSelectHAWB()) {
				// Delete all the house information along with MAWB on deletion of last house
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_HOUSE_SHC.getQueryId(), t, sqlSessionShipment);

				// Delete FreeText
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_HOUSE_DESCRIPTIONOFGOODS.getQueryId(), t,
						sqlSessionShipment);
				// Delete Tariffs
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_HOUSE_TARIFF.getQueryId(), t, sqlSessionShipment);
				// Delete OCI
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_OTHERCUSTOMSINFORMATION.getQueryId(), t,
						sqlSessionShipment);

				// Delete Customer
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_ALL_HOUSE_CUSTOMER_ADDRESS_CONTACT.getQueryId(), t,
						sqlSessionShipment);
				// Delete customer address info
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_CUSTOMER_ADDRESS.getQueryId(), t,
						sqlSessionShipment);
				// Delete shipper info
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_CUSTOMER.getQueryId(), t, sqlSessionShipment);

				// Delete Other Charges Info
				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_OTHERCHARGES.getQueryId(), t,
						sqlSessionShipment);

				deleteHouse(t);

				// Delete and Update MAWB
				masterAWBRequestModel.setPieces(BigInteger.ZERO);
				masterAWBRequestModel.setWeight(BigDecimal.ZERO);
				this.saveUpdateAWB(masterAWBRequestModel);

				super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE_MAWB.getQueryId(), masterAWBRequestModel,
						sqlSessionShipment);
			}
		}
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public void deleteHouse(HouseModel t) throws CustomException {
		// Delete House
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HOUSE.getQueryId(), t, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#isHAWBNumber(com.ngen.
	 * cosys.shipment.house.model.HouseModel)
	 */
	@Override
	public boolean checkDuplicateHAWB(HouseModel houseRequestModel) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_CHECK_HAWB_EXISTS.getQueryId(), houseRequestModel,
				sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#
	 * getShipperAndconsigneeInfoForFirstHouse(com
	 * .ngen.cosys.shipment.house.model.HouseModel)
	 */
	public HouseModel getShipperAndconsigneeInfoForFirstHouse(HouseModel houseRequestModel) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GETSHIPPER_AND_CONSIGNEEINFO_FOR_FIRSTHOUSE.getQueryId(),
				houseRequestModel, sqlSessionShipment);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#getShipmentInfoForCharges(
	 * com.ngen.cosys.shipment.house.model.HouseModel)
	 */
	@Override
	public HouseModel getShipmentInfoForCharges(HouseModel houseRequestModel) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GET_SHIPMENT_INFO_FOR_CHARGES.getQueryId(),
				houseRequestModel, sqlSessionShipment);
	}

	@Override
//   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public MasterAirWayBillModel saveUpdateAWB(MasterAirWayBillModel masterAWBRequestModel) throws CustomException {

		int updateRecordCount = 0;

		if (!masterAWBRequestModel.isFirstTimeSave()) {
			// Get sum house piece weight
			MasterAirWayBillModel housePieceWeight = super.fetchObject(
					MaintainHouseQueryIds.SQL_GET_SUM_HOUSE_PCS_WT.getQueryId(), masterAWBRequestModel,
					sqlSessionShipment);

			if (!ObjectUtils.isEmpty(housePieceWeight)) {
				masterAWBRequestModel.setPieces(housePieceWeight.getPieces().add(masterAWBRequestModel.getPieces()));
				masterAWBRequestModel.setWeight(housePieceWeight.getWeight().add(masterAWBRequestModel.getWeight()));
			} else {
				masterAWBRequestModel.setPieces(masterAWBRequestModel.getPieces());
				masterAWBRequestModel.setWeight(masterAWBRequestModel.getWeight());
			}
			updateRecordCount = updateData(MaintainHouseQueryIds.SQL_UPDATE_PCS_WT_HOUSE_MAWB.getQueryId(),
					masterAWBRequestModel, sqlSessionShipment);
		}

		// Update the origin and destination
		if (updateRecordCount == 0) {
			updateData(MaintainHouseQueryIds.SQL_UPDATE_ORIGIN_DESTINATION_HOUSE_MAWB.getQueryId(),
					masterAWBRequestModel, sqlSessionShipment);
		}
		return masterAWBRequestModel;

	}

// HAWB MASTERS STARTS HERE

	@Override
	public MasterAirWayBillModel getHouseWayBillMaster(HouseSearch houseModel) throws CustomException {
		MasterAirWayBillModel masterAirWayBillModel = super.fetchObject(
				MaintainHouseQueryIds.SQL_GET_HAWB_MASTERS.getQueryId(), houseModel, sqlSessionShipment);
		HouseModel houseModel1 = masterAirWayBillModel.getHouseModel();
		if (houseModel1.getId() != null) {
			HouseCustomerModel houseCustomerModel = super.fetchObject("getConsigneeDetails", houseModel1,
					sqlSessionShipment);
			HouseCustomerModel consignee = houseModel1.getConsignee();
			if (!Objects.isNull(houseCustomerModel)) {
				consignee.setAppointedAgentCode(houseCustomerModel.getAppointedAgentCode());
			}
			if (!Objects.isNull(consignee)) {
				houseModel1.setConsignee(consignee);
			}
			masterAirWayBillModel.setHouseModel(houseModel1);
		}
		return masterAirWayBillModel;
	}

	@Override
	public void validShc(HouseSpecialHandlingCodeModel request) throws CustomException {
		Boolean validShc = super.fetchObject(MaintainHouseQueryIds.SQL_GET_VALID_SHC.getQueryId(), request,
				sqlSessionShipment);
		if (!validShc) {
			throw new CustomException("ERR_HAWB_07", null, ErrorType.ERROR);
		}
	}

	@Override
	public void onSaveHouseShc(HouseSpecialHandlingCodeModel houseModel) throws CustomException {
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_NEW_HOUSE_SHC.getQueryId(), houseModel, sqlSessionShipment);
	}

	@Override
	public void onSaveHouseShcUpdate(HouseSpecialHandlingCodeModel houseModel) throws CustomException {
		super.insertData(MaintainHouseQueryIds.SQL_UPDATE_NEW_HOUSE_SHC.getQueryId(), houseModel, sqlSessionShipment);
	}

	@Override
	public void onSaveHouseShcDelete(HouseSpecialHandlingCodeModel houseModel) throws CustomException {
		super.insertData(MaintainHouseQueryIds.SQL_DELETE_NEW_HOUSE_SHC.getQueryId(), houseModel, sqlSessionShipment);
	}

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveConsigneeInformationInsert(HouseCustomerModel houseModel) throws CustomException {

		if (houseModel.getAddress().getCountry() == null) {
			throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
		}
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_CONSIGNEE_INFO.getQueryId(), houseModel, sqlSessionShipment);
		houseModel.getAddress().setHouseCustomerId(houseModel.getId());
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_CONSIGNEE_ADDRESS_INFO.getQueryId(), houseModel.getAddress(),
				sqlSessionShipment);
	}

	@Override
	public void onSaveConsigneeInformationUpdateMaster(HouseCustomerModel request) throws CustomException {
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_CONSIGNEE_ADDRESS_INFO_MASTER.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_CONSIGNEE_INFO_MASTER.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	public void onSaveConsigneeInformationDelete(HouseCustomerModel request) throws CustomException {
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONTACT.getQueryId(), request.getAddress(),
				sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_ADDRESS_CONSIGNEE.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONSIGNEE.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveShipperInformationInsert(HouseCustomerModel houseModel) throws CustomException {
		if (houseModel.getAddress().getCountry() == null) {
			throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
		}
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_SHIPPER_INFO.getQueryId(), houseModel, sqlSessionShipment);
		houseModel.getAddress().setHouseCustomerId(houseModel.getId());
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_SHIPPER_ADDRESS_INFO.getQueryId(), houseModel.getAddress(),
				sqlSessionShipment);
	}

	@Override
	public void onSaveShipperInformationUpdateMaster(HouseCustomerModel request) throws CustomException {
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_CONSIGNEE_ADDRESS_INFO_MASTER.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_SHIPPER_INFO_MASTER.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	public void onSaveShipperInformationDelete(HouseCustomerModel request) throws CustomException {
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONTACT.getQueryId(), request.getAddress(),
				sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_ADDRESS_SHIPPER.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_SHIPPER.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	public void houseValidation(MasterAirWayBillModel request) throws CustomException {
		if ((boolean) super.fetchObject(MaintainHouseQueryIds.SQL_VERIFY_FREIGHT_OUT_ALREADY_EXISTS.getQueryId(),
				request, sqlSessionShipment)) {
			request.addError("House is already freight-out for this shipment, and house cannot be edited now", "",
					ErrorType.APP);
		}
		if ((boolean) super.fetchObject(MaintainHouseQueryIds.SQL_CHECK_ISSUE_PODO_ISSUED.getQueryId(), request,
				sqlSessionShipment)) {
			request.addError("PO/DO issued for this house, and the existing info cannot be further edited", "",
					ErrorType.APP);
		}

		BigInteger pieceHouse = super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_PIECES.getQueryId(),
				request.getHouseModel(), sqlSessionShipment);
		int pieceHouseCheckData = request.getPieces().compareTo(pieceHouse.add(request.getHouseModel().getPieces()));
		if (pieceHouseCheckData == -1) {
			throw new CustomException("ERR_HAWB_03", null, ErrorType.ERROR);
		}
		BigDecimal weightHouse = super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_WEIGHT.getQueryId(),
				request.getHouseModel(), sqlSessionShipment);
		int weightHouseCheckData = request.getWeight().compareTo(weightHouse.add(request.getHouseModel().getWeight()));
		if (weightHouseCheckData == -1) {
			throw new CustomException("ERR_HAWB_04", null, ErrorType.ERROR);
		}
		BigDecimal chargeWeightHouse = super.fetchObject(
				MaintainHouseQueryIds.SQL_GET_HAWB_CHARGEABLE_WEIGHT.getQueryId(), request.getHouseModel(),
				sqlSessionShipment);
		if (ObjectUtils.isEmpty(request.getChargeableWeight())) {
			throw new CustomException("ERR_HAWB_09", null, ErrorType.ERROR);
		} else {
			int chargeWeightHouseCheckData = request.getChargeableWeight()
					.compareTo(chargeWeightHouse.add(request.getHouseModel().getChargeableWeight()));
			if (pieceHouseCheckData == -1) {
				// throw new CustomException("ERR_HAWB_05", null, ErrorType.ERROR);
			}
		}
	}

	@Override
	// hawb record update audit
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void setHouseWayBillMaster(MasterAirWayBillModel request) throws CustomException {
		super.updateData(MaintainHouseQueryIds.SQL_SET_HAWB_MASTERS.getQueryId(), request.getHouseModel(),
				sqlSessionShipment);
	}

	// hawb list starts here
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.house.dao.MaintainHouseDAO#getHouseWayBillList(
	 * com.ngen.cosys.shipment.house.model.HouseSearch)
	 */
	@Override
	public MasterAirWayBillModel getHouseWayBillList(HouseSearch houseSearchRequest) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_LIST_INFO.getQueryId(), houseSearchRequest,
				sqlSessionShipment);
	}

	@Override
	public void houseListValidation(HouseModel request) throws CustomException {
		BigInteger pieceHouse = super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_PIECES.getQueryId(), request,
				sqlSessionShipment);
		int pieceHouseCheckData = request.getAwbPieces().compareTo(pieceHouse.add(request.getPieces()));
		if (pieceHouseCheckData == -1) {
			throw new CustomException("ERR_HAWB_03", null, ErrorType.ERROR);
		}
		BigDecimal weightHouse = super.fetchObject(MaintainHouseQueryIds.SQL_GET_HAWB_WEIGHT.getQueryId(), request,
				sqlSessionShipment);
		int weightHouseCheckData = request.getAwbWeight().compareTo(weightHouse.add(request.getWeight()));
		if (weightHouseCheckData == -1) {
			throw new CustomException("ERR_HAWB_04", null, ErrorType.ERROR);
		}
		BigDecimal chargeWeightHouse = super.fetchObject(
				MaintainHouseQueryIds.SQL_GET_HAWB_CHARGEABLE_WEIGHT.getQueryId(), request, sqlSessionShipment);
		if (request.getAwbChargeableWeight() != null) {
			int chargeWeightHouseCheckData = request.getAwbChargeableWeight()
					.compareTo(chargeWeightHouse.add(request.getChargeableWeight()));
			if (chargeWeightHouseCheckData == -1) {
				// throw new CustomException("ERR_HAWB_05", null, ErrorType.ERROR);
			}
		}
		// Check Duplicate Record
		if (request.getFlagCRUD().equalsIgnoreCase(CRUDStatus.CRUDType.CREATE)) {
			Boolean checkDuplicacy = super.fetchObject(
					MaintainHouseQueryIds.SQL_CHECK_HAWB_DUPLICATE_RECORD.getQueryId(), request, sqlSessionShipment);
			if (checkDuplicacy) {
				throw new CustomException("ERR_HAWB_06", null, ErrorType.ERROR);
			}
		}
	}

	@Override
	// hawb list insert audit
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveHouseNumber(HouseModel houseModel) throws CustomException {
		super.insertData(MaintainHouseQueryIds.SQL_INSERT_HOUSE_INFO.getQueryId(), houseModel, sqlSessionShipment);
	}

	/*
	 * changes for bug-81
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveHouseNumberUpdate(HouseModel houseModel) throws CustomException {
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_HOUSE_INFO.getQueryId(), houseModel, sqlSessionShipment);
	}
	/*
	 * changes end here for bug-81
	 */

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveConsigneeInformationUpdate(HouseCustomerModel houseModel) throws CustomException {
		if (houseModel.getAddress().getCountry() == null) {
			throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
		}
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_CONSIGNEE_INFO.getQueryId(), houseModel, sqlSessionShipment);
		houseModel.getAddress().setHouseId(houseModel.getHouseId());
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_CONSIGNEE_ADDRESS_INFO.getQueryId(), houseModel.getAddress(),
				sqlSessionShipment);
	}

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveShipperInformationUpdate(HouseCustomerModel houseModel) throws CustomException {
		if (houseModel.getAddress().getCountry() == null) {
			throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
		}
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_SHIPPERE_INFO.getQueryId(), houseModel, sqlSessionShipment);
		houseModel.getAddress().setHouseId(houseModel.getHouseId());
		super.updateData(MaintainHouseQueryIds.SQL_UPDATE_SHIPPER_ADDRESS_INFO.getQueryId(), houseModel.getAddress(),
				sqlSessionShipment);
	}

	@Override
	public HouseModel getConsigneeShipperDetails(HouseSearch houseModel) throws CustomException {
		return super.fetchObject(MaintainHouseQueryIds.SQL_GET_AGENT_INFO.getQueryId(), houseModel, sqlSessionShipment);
	}

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveConsigneeInformationDeleteData(HouseCustomerModel request) throws CustomException {
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONTACT_DATA_CONSIGNEE.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_ADDRESS_CONSIGNEE_DATA.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONSIGNEE_DATA.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	// bug-81 fix
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HWB_LIST)
	public void onSaveShipperInformationDeleteData(HouseCustomerModel request) throws CustomException {
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_CONTACT_DATA_SHIPPER.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_ADDRESS_SHIPPER_DATA.getQueryId(),
				request.getAddress(), sqlSessionShipment);
		super.deleteData(MaintainHouseQueryIds.SQL_DELETE_HAWB_MASTERS_SHIPPER_DATA.getQueryId(), request,
				sqlSessionShipment);
	}

	@Override
	public void updateMainMastersSHCData(MasterAirWayBillModel information) throws CustomException {
		super.deleteData("deleteMasterSHC", information, sqlSessionShipment);
		super.insertData("updateMasterSHC", information, sqlSessionShipment);
	}

	@Override
	public Integer insertHouseDimension(HouseDimensionModel houseModel) throws CustomException {
		return super.insertData(MaintainHouseQueryIds.INSERT_HOUSE_DIMENSION.getQueryId(), houseModel,
				sqlSessionShipment);

	}

	@Override
	public void insertHouseDimensionDetails(HouseDimensionDetailsModel dimensionModel) throws CustomException {
		super.insertData("sqlInsertHouseDimensionDetails", dimensionModel, sqlSessionShipment);

	}

	@Override
	public void deleteDimension(BigInteger houseDimensionDtlsId) throws CustomException {
		super.deleteData("sqlDeleteDimensionDtls", houseDimensionDtlsId, sqlSessionShipment);

	}

	@Override
	public void updateHouseDimension(HouseDimensionModel houseModel) throws CustomException {
		super.updateData("sqlupdateDimensionData", houseModel, sqlSessionShipment);
	}

	@Override
	public void updateDimensionalDetails(HouseDimensionDetailsModel dimensionModel) throws CustomException {
		super.updateData("sqlupdateDimensionDtlsData", dimensionModel, sqlSessionShipment);
	}

	@Override
	public void updateHouseVolumetricWeight(HouseDimensionModel houseDimension) throws CustomException {
		super.updateData("sqlUpdateVolumetricWeight", houseDimension, sqlSessionShipment);

	}

	@Override
	public void updateHouseChargeableWeight(HouseModel houseModel) throws CustomException {
		super.updateData("sqlUpdateHouseChargeableWeight", houseModel, sqlSessionShipment);

	}

	@Override
	public void updateAWBChargeableWeight(HouseModel houseModel) throws CustomException {
		super.updateData("sqlUpdateAWBChargeableWeight", houseModel, sqlSessionShipment);

	}

	@Override
	public void updateDimension(HouseDimensionDetailsModel dimensionDtlsModel) throws CustomException {
		super.updateData("sqlupdateDimension", dimensionDtlsModel, sqlSessionShipment);
	}

	@Override
	public Integer getAppointmentAgent(BigInteger appointedAgentCode) throws CustomException {
		Integer result = super.fetchObject("checkAppointedAgent", appointedAgentCode, sqlSessionShipment);
		return result;
	}

	@Override
	public Integer isFlightCompleted(BigInteger flightId) throws CustomException {
		return fetchObject("sqlCheckFlightCompleted", flightId, sqlSessionShipment);
	}

	@Override
	public BigInteger getFlightId(BigInteger shipmentId) throws CustomException {
		return fetchObject("sqlGetFlightId", shipmentId, sqlSessionShipment);
	}

	public BigInteger fetchAppointedAgent() throws CustomException {
		return fetchObject("sqlFetchAppointedAgent", null, sqlSessionShipment);
	}

}