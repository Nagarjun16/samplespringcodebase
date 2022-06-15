package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;



public interface AssignULDDAO {

	public AssignULD addULD(AssignULD assignULD)throws CustomException;
	public AssignULD updateULD (AssignULD assignULD) throws CustomException;
	public AssignULD updatePiggyBackList(AssignULD assignULD)throws CustomException;
	public AssignULD insertPiggyBackList(AssignULD assignULD)throws CustomException;
	public AssignULD deletePiggyBackList(AssignULD assignULD)throws CustomException;
	public AssignULD deleteSomePiggyBackList(AssignULD assignULD)throws CustomException;
	public AssignULD deleteULD(AssignULD assignULD)throws CustomException;
	public AssignULD checkIfAssignUldExists(AssignULD uld)throws CustomException;
	public BigDecimal getTareWeight(ULDIInformationDetails uldiInformationDetails)throws CustomException;
	public BigDecimal getActualGrossWeight(ULDIInformationDetails uldiInformationDetails)throws CustomException;
	public String getContourCode(AssignULD uld)throws CustomException;
	public String getContourCodeFfromUldCharacterStics(AssignULD uld)throws CustomException;
	public boolean checkIfUldExistsInInventory(AssignULD uld)throws CustomException;
	public AssignULD insertUldInventory(AssignULD uld) throws CustomException;
	public BigInteger getFlightID(AssignULD uld) throws CustomException;
    boolean checkIfPiggyBackUldExistsInInventory(AssignULD uld) throws CustomException;
    DLSULD fetchDLSId (AssignULD uld) throws CustomException;
    public boolean searchInULDInventory(AssignULD uld)throws CustomException;
	AssignULD checkIfAssignUldBulkExists(AssignULD uld)throws CustomException;
	public BigInteger getBulkUldID(AssignULD uld)throws CustomException;
	public Integer isPerishableCargo(AssignULD uld) throws CustomException;
	public String getContentCode (String contentCode) throws CustomException;
	public String getDestinationCode (Integer flightSegmentId) throws CustomException;
	public Integer isDamaged (AssignULD uld) throws CustomException;
	public Integer isCarrierCompatible (AssignULD uld) throws CustomException;
	public Integer isExceptionULD (AssignULD uld) throws CustomException;
	public Integer isUldLoaded (AssignULD uld) throws CustomException;
	
	Boolean isUldExistInUldMaster(String obj) throws CustomException;
	
	
	
}
