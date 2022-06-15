package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ULD;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;
import com.ngen.cosys.validator.AssignULDQuery;

@Repository("AssignULDDAO")
public class AssignULDDAOImpl extends BaseDAO implements AssignULDDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   public SqlSession sqlSession;

   @Override
   public AssignULD addULD(AssignULD assignULD) throws CustomException {

      insertData(AssignULDQuery.SQL_FOR_INSERT_ULD.getQueryId(), assignULD, sqlSession);
      return assignULD;
   }

   @Override
   public AssignULD updateULD(AssignULD assignULD) throws CustomException {

      updateData(AssignULDQuery.SQL_FOR_UPDATE_ULD.getQueryId(), assignULD, sqlSession);
      return assignULD;
   }

   @Override
   public AssignULD deleteULD(AssignULD assignULD) throws CustomException {
      deleteData(AssignULDQuery.SQL_FOR_DELETE_PIGGYBACKULD_LIST.getQueryId(), assignULD, sqlSession);
      deleteData(AssignULDQuery.SQL_FOR_DELETE_ULD.getQueryId(), assignULD, sqlSession);
      return assignULD;
   }

   @Override
   public AssignULD updatePiggyBackList(AssignULD assignULD) throws CustomException {

      updateData(AssignULDQuery.SQL_FOR_UPDATE_PIGGYBACKULD_LIST.getQueryId(), assignULD, sqlSession);
      return assignULD;
   }

   @Override
   public AssignULD insertPiggyBackList(AssignULD assignULD) throws CustomException {
      insertData(AssignULDQuery.SQL_FOR_INSERT_PIGGYBACKULD_LIST.getQueryId(), assignULD, sqlSession);
      return assignULD;
   }

   @Override
   public AssignULD deletePiggyBackList(AssignULD assignULD) throws CustomException {

      deleteData(AssignULDQuery.SQL_FOR_DELETE_PIGGYBACKULD_LIST.getQueryId(), assignULD, sqlSession);
      return null;
   }

   @Override
   public AssignULD deleteSomePiggyBackList(AssignULD assignULD) throws CustomException {
      deleteData(AssignULDQuery.SQL_FOR_DELETE_SOMEPIGGYBACKULD_LIST.getQueryId(), assignULD, sqlSession);
      return null;
   }


   

   @Override
   public AssignULD checkIfAssignUldExists(AssignULD uld) throws CustomException {

	   AssignULD assignUldTrolleyId = fetchObject(AssignULDQuery.SQL_CHECK_IF_ASSIGNULD_EXITS.getQueryId(), uld,
            sqlSession);
      return assignUldTrolleyId;
   }
   
   
   
   @Override
	public AssignULD checkIfAssignUldBulkExists(AssignULD uld) throws CustomException {
		return fetchObject(AssignULDQuery.SQL_CHECK_IF_ASSIGNULD_BULK_EXISTS.getQueryId(), uld,
	            sqlSession);
	}

   
   @Override
   public BigInteger getFlightID(AssignULD uld) throws CustomException {
	   //SQL_FOR_FETCH_FLIGHTID
      return fetchObject(AssignULDQuery.SQL_FOR_FETCH_FLIGHTID.getQueryId(), uld, sqlSession);
   }

   @Override
   public boolean checkIfUldExistsInInventory(AssignULD uld) throws CustomException {
      int count = fetchObject(AssignULDQuery.SQL_CHECK_INVENTORY.getQueryId(), uld, sqlSession);
      return count > 0;
   }

   @Override
   public boolean checkIfPiggyBackUldExistsInInventory(AssignULD uld) throws CustomException {
      int count = fetchObject(AssignULDQuery.SQL_FOR_FETCH_PIGGYBACK_ULD.getQueryId(), uld, sqlSession);
      return count > 0;
   }

   @Override
   public AssignULD insertUldInventory(AssignULD uld) throws CustomException {
      if (uld != null && uld.getUld() != null && uld.getUld().getUldTrolleyNo() != null
            && !uld.getUld().isTrolleyInd()) {
         String uldType = uld.getUld().getUldTrolleyNo().substring(0, 3);
         String uldCarrier = uld.getUld().getUldTrolleyNo().substring(uld.getUld().getUldTrolleyNo().length() - 3);
         Boolean carrierlengthflag=Pattern.compile( "[0-9]" ).matcher( uldCarrier ).find();
         if(!carrierlengthflag) {
        	 uldCarrier = uld.getUld().getUldTrolleyNo().substring(uld.getUld().getUldTrolleyNo().length() - 3);
         }else{
        	 uldCarrier = uld.getUld().getUldTrolleyNo().substring(uld.getUld().getUldTrolleyNo().length() - 2);
         }
         uld.getUld().setUldType(uldType);
         uld.getUld().setUldCarrier(uldCarrier);
         uld.getUld()
               .setUldNumber(uld.getUld().getUldTrolleyNo().substring(3, uld.getUld().getUldTrolleyNo().length() - 2));
      }
      insertData(AssignULDQuery.SQL_FOR_INSERT_ULD_INVENTORY.getQueryId(), uld, sqlSession);
      return uld;
   }

	@Override
	public DLSULD fetchDLSId(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_FOR_FETCH_DLSID.getQueryId(), uld, sqlSession);
	}

	@Override
	public boolean searchInULDInventory(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		int count = fetchObject(AssignULDQuery.SQL_FOR_SEARCH_IN_ULDINVENTORY.getQueryId(), uld, sqlSession);
	     return count > 0;
	}

	@Override
	public BigInteger getBulkUldID(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		BigInteger uldId = fetchObject(AssignULDQuery.SQL_GET_BULK_ULD_ID.getQueryId(), uld, sqlSession);
		return uldId;
	}

	@Override
	public String getContourCode(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_GET_CONTOUR_CODE.getQueryId(), uld, sqlSession);
	}

	@Override
	public Integer isPerishableCargo(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_IS_PERISHABLE_CARGO.getQueryId(), uld, sqlSession);
	}

	@Override
	public String getContentCode(String contentCode) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_GET_CONTENT_CODE.getQueryId(), contentCode, sqlSession);
	}

	@Override
	public String getDestinationCode(Integer flightSegmentId) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_GET_DESTINATION_CODE.getQueryId(), flightSegmentId, sqlSession);
	}


	@Override
	public Integer isDamaged(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_IS_DAMAGED.getQueryId(), uld, sqlSession);
	}

	@Override
	public Integer isCarrierCompatible(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_IS_CARRIER_COMPATIBLE.getQueryId(), uld, sqlSession);
	}

	@Override
	public Integer isExceptionULD(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_IS_EXCEPTION_ULD.getQueryId(), uld, sqlSession);
	}


	@Override
	public Integer isUldLoaded(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_IS_ULD_LOADED.getQueryId(), uld, sqlSession);
	}

	@Override
	public String getContourCodeFfromUldCharacterStics(AssignULD uld) throws CustomException {
		// TODO Auto-generated method stub
		 return fetchObject(AssignULDQuery.SQL_GET_HEIGHT_CODE_FROM_ULDCHAR.getQueryId(), uld, sqlSession);
	}

	@Override
	public BigDecimal getTareWeight(ULDIInformationDetails uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQL_FOR_FETCH_TAREWEIGHT.getQueryId(), uld, sqlSession);
	}

	@Override
	public BigDecimal getActualGrossWeight(ULDIInformationDetails uld) throws CustomException {
		// TODO Auto-generated method stub
		return fetchObject(AssignULDQuery.SQ_GET_ACTUAL_GROSS_WEIGHT.getQueryId(), uld, sqlSession);
	}

	@Override
	public Boolean isUldExistInUldMaster(String obj) throws CustomException {
		return fetchObject(AssignULDQuery.IS_ULD_TYPE_EXIST.getQueryId(), obj, sqlSession);
	}
}
