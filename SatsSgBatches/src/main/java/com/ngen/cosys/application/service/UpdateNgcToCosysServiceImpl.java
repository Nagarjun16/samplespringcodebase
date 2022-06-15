package com.ngen.cosys.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.UpdateNgcToCosysDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class UpdateNgcToCosysServiceImpl implements UpdateNgcToCosysService {

   @Autowired
   private UpdateNgcToCosysDAO dao;

	
   @Override
   public void getPreLodgeData() throws CustomException {
	   this.dao.getPreLodgeData();
      }
   }