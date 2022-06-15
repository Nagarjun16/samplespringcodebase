package com.ngen.cosys.shipment.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.dao.CommonFlightIdDAO;
import com.ngen.cosys.shipment.dao.MaintainRemarkDAO;
import com.ngen.cosys.shipment.model.CommonFlightId;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.validator.dao.ShipmentValidationDao;

@Service
public class MaintainRemarkServiceImpl implements MaintainRemarkService {
   
	private static String queryMode = "READ";
	private static String formGroupRemarks = "maintainRemarkFormGroup";
	private static String noAWBFoundEx = "no.record";
	private static String invalidFlight = "INFLT001";
	private static String fillAllField = "AWBREUSE01";
	private static String noRemark = "no.record";

	@Autowired
	private MaintainRemarkDAO maintainRemarkdao;

	@Autowired
	private CommonFlightIdDAO commonFlightIdDAO;

	@Autowired
	private ShipmentProcessorService shipmentProcessorService;
	
	@Autowired
	private ShipmentValidationDao shipmentValidationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.service.MaintainRemarkService#getRemark(com.ngen.
	 * cosys.shipment.model.RequestSearchRemarksBO)
	 */
	@Override
	public ResponseSearchRemarksBO getRemark(RequestSearchRemarksBO searchRemarks) throws CustomException {
		if (StringUtils.isEmpty(searchRemarks.getShipmentNumber())) {
			throw new CustomException(fillAllField, formGroupRemarks, ErrorType.ERROR);
		}
		// Fetch Flight ID based on Flight Key
		if (StringUtils.isEmpty(searchRemarks.getFlightKey())) {
			searchRemarks.setFlightKey(null);
		}
		// In case of Search by Flight Key only display Flight-based remarks
		fetchRemark(searchRemarks);
		// Fetch both Flight/No-Flight remarks
		searchRemarks.setShipmentDate(shipmentProcessorService.getShipmentDate(searchRemarks.getShipmentNumber()));
		ResponseSearchRemarksBO retObj = maintainRemarkdao.fetch(searchRemarks);
		
		boolean awbBlackListedFlag = shipmentValidationDao.getAwbBlacklistedCheckFlag(searchRemarks.getShipmentNumber());

		if (awbBlackListedFlag) {
			retObj.addError("EXPIMPEXT05", "", ErrorType.WARNING);
		}
		
		if (retObj.getFlightBasedRemarks().get(0) == null && retObj.getNoFlightRemarks().isEmpty()) {
			retObj.setExistRemarks(true);
		} else {
			if (!CollectionUtils.isEmpty(retObj.getNoFlightRemarks())) {
				retObj.getNoFlightRemarks().stream().forEach(obj -> obj.setFlagCRUD(queryMode));
				return retObj;
			}
			if (!CollectionUtils.isEmpty(retObj.getFlightBasedRemarks())) {
				retObj.getFlightBasedRemarks().stream()
						.forEach(fltobj -> fltobj.getRemarks().stream().forEach(obj -> obj.setFlagCRUD(queryMode))

				);
				return retObj;

			}
		}
		return retObj;
	}

	private void fetchRemark(RequestSearchRemarksBO searchRemarks) throws CustomException {
		if (searchRemarks.getFlightKey() != null && searchRemarks.getFlightDate() != null) {
			if (CollectionUtils.isEmpty(maintainRemarkdao.fetch(searchRemarks).getFlightBasedRemarks())) {
				throw new CustomException(noAWBFoundEx, formGroupRemarks, ErrorType.ERROR);
			}
			CommonFlightId commonFlightId = new CommonFlightId();
			commonFlightId.setFlightKey(searchRemarks.getFlightKey());
			commonFlightId.setFlightDate(searchRemarks.getFlightDate());
			if (commonFlightIdDAO.getFlightId(commonFlightId) == null) {
				throw new CustomException(invalidFlight, formGroupRemarks, ErrorType.ERROR);
			} else {
				searchRemarks.setFlightId(commonFlightIdDAO.getFlightId(commonFlightId));
			}
			if (searchRemarks.getHawbNumber()==null && searchRemarks.getFlightId() != null
					&& maintainRemarkdao.fetch(searchRemarks).getFlightBasedRemarks().get(0) == null ) {
				
				
					throw new CustomException(noRemark, formGroupRemarks, ErrorType.ERROR);
				}
			
			else {
				if(searchRemarks.getHawbNumber()!=null && maintainRemarkdao.fetch(searchRemarks).getFlightBasedRemarks().get(0) == null &&  CollectionUtils.isEmpty(maintainRemarkdao.fetch(searchRemarks).getHwbRemarks()))
				{
				throw new CustomException(noRemark, formGroupRemarks, ErrorType.ERROR);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.service.MaintainRemarkService#insertRemark(com.ngen.
	 * cosys.shipment.model.ResponseSearchRemarksBO)
	 */
	@Override
	public void insertRemark(List<MaintainRemark> parmRemarkList) throws CustomException {
		for (MaintainRemark maintain : parmRemarkList) {
			if (StringUtils.isEmpty(maintain.getShipmentRemarks()) || StringUtils.isEmpty(maintain.getShipmentType())) {
				throw new CustomException("CUST003", formGroupRemarks, ErrorType.ERROR);
			}
			if (maintain.getShipmentRemarks().length() > 65) {
				throw new CustomException("INVDRMK", formGroupRemarks, ErrorType.ERROR);
			}
			if (!StringUtils.isEmpty(maintain.getFlightKey()) && maintain.getFlightDate() == null) {
				throw new CustomException("INVDT01", formGroupRemarks, ErrorType.ERROR);
			}
			if (maintain.getFlightDate() != null && StringUtils.isEmpty(maintain.getFlightKey())) {
				throw new CustomException("INVDFLT", formGroupRemarks, ErrorType.ERROR);
			}
			maintain.setShipmentDate(shipmentProcessorService.getShipmentDate(maintain.getShipmentNumber()));
			insertFlightBasedRemark(maintain);
		}
		if (parmRemarkList.size() > 0) {
			LocalDate shipmentDate = shipmentProcessorService
					.getShipmentDate(parmRemarkList.get(0).getShipmentNumber());
			parmRemarkList.forEach(obj -> obj.setShipmentDate(shipmentDate));
		}
		//HAWB Remarks Insert JV01-122
		if (parmRemarkList.size() > 0) {
		String hwbNumber=parmRemarkList.get(0).getHawbNumber();
		boolean handledByhouse= parmRemarkList.get(0).isHandledbyHouse();
		if(StringUtils.isEmpty(hwbNumber)) {
		maintainRemarkdao.insert(parmRemarkList);
		}else if(handledByhouse)
		{
			maintainRemarkdao.insertHWB(parmRemarkList);
		}
		}
	}

	private void insertFlightBasedRemark(MaintainRemark maintain) throws CustomException {
		if (maintain.getFlightKey() != null && maintain.getFlightDate() != null) {
			CommonFlightId commonFlightId = new CommonFlightId();
			commonFlightId.setFlightKey(maintain.getFlightKey());
			commonFlightId.setFlightDate(maintain.getFlightDate());
			commonFlightId.setSource(maintain.getFlightSource());
			commonFlightId.setDestination(maintain.getFlightDestination());
			String flightId = commonFlightIdDAO.getFlightId(commonFlightId);
			if (flightId == null) {
				throw new CustomException("INFLT001", formGroupRemarks, ErrorType.ERROR);
			} else {
				maintain.setFlightId(Long.parseLong(flightId));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.service.MaintainRemarkService#deleteRemark(com.ngen.
	 * cosys.shipment.model.DeleteRemarkBO)
	 */
	@Override
	public void deleteRemark(DeleteRemarkBO parmRemarkList) throws CustomException {
		if (null != parmRemarkList.getRemarkIdList()) {
			for (Integer ele : parmRemarkList.getRemarkIdList()) {
				MaintainRemark rmk = maintainRemarkdao.getRemarksDetails(ele);
				rmk.setModifiedBy(parmRemarkList.getCreatedBy());
				maintainRemarkdao.delete(rmk);
			}
			
		}
	}
}