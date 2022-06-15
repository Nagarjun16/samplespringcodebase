package com.ngen.cosys.satssg.interfaces.singpost.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.AutoWeigh;
import com.ngen.cosys.ics.model.AutoWeighDG;
import com.ngen.cosys.ics.model.PrintULDTagList;
import com.ngen.cosys.ics.model.PrintULDTagSubList;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagRequestModel;

@Repository("PrintUldTagDao")
public class PrintULDTagDaoImpl extends BaseDAO implements PrintULDTagDao {

   @Autowired
   private SqlSession sqlSessionTemplate;

   @Override
   public String getprinterIPAddressAppSystemParams(PrintULDTagRequestModel requestModel) throws CustomException {
      // if XPS Flag N then go to ULD Printer else RFIDULD Printer(printer type
      // fetching from system parameters)
      if (requestModel.getXpsFlag().equalsIgnoreCase("N")) {
         requestModel.setPrintertype(fetchObject("getPrinterTypeForULD", requestModel, sqlSessionTemplate));
      } else {
         requestModel.setPrintertype(fetchObject("getPrinterTypeForRFID", requestModel, sqlSessionTemplate));
      }
      return fetchObject("fetchPrinterIPaddress", requestModel, sqlSessionTemplate);
   }

   @Override
   public void insertPrintULDTagInfo(PrintULDTagRequestModel request) throws CustomException {
      // 1. Save the ULD information
      this.insertData("sqlInsertPrintULDTagRequest", request, sqlSessionTemplate);

      // 2. Save the ULD Tag information
      if (!CollectionUtils.isEmpty(request.getDgClassList())) {
         for (PrintULDTagList t : request.getDgClassList()) {
            t.setId(request.getId());
            t.setStaffLoginId(request.getStaffLoginId());
            this.insertData("sqlInsertPrintULDTagRequestTag", t, sqlSessionTemplate);
         }
      }

      // 3. Save the ULD Sub Tag information
      if (!CollectionUtils.isEmpty(request.getDgSubClassList())) {
         for (PrintULDTagSubList t : request.getDgSubClassList()) {
            t.setId(request.getId());
            t.setStaffLoginId(request.getStaffLoginId());
            this.insertData("sqlInsertPrintULDTagRequestSubTag", t, sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.satssg.interfaces.singpost.dao.PrintULDTagDao#
    * logMasterAuditData(java.util.Map)
    */
	@Override
	public void logMasterAuditData(Map<String, Object> auditMap) throws CustomException {
		this.insertData("sqlInsertUpdateAutoWeightRequestAuditData", auditMap, sqlSessionTemplate);
	}

	@Override
	public void insertAutoWeighInfo(AutoWeigh request) throws CustomException {
		this.insertData("insertAutoWeighDetailsICS", request, sqlSessionTemplate);
	}

	@Override
	public void insertAutoWeighDG(AutoWeighDG request) throws CustomException {
		this.insertData("insertAutoWeighDGICS", request, sqlSessionTemplate);
	}

}