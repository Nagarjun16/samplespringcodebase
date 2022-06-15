package com.ngen.cosys.sqcsla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.sqcsla.dao.SqcSlaDAO;
import com.ngen.cosys.sqcsla.model.Emails;

@Service
public class SqcSlaServiceImpl implements SqcSlaService {
   
   @Autowired
   SqcSlaDAO sqcSlaDAO;

   @Override
   public List<Emails> getSqcGroupEmail() throws CustomException {
      return sqcSlaDAO.getSqcGroupEmail();
   }

   @Override
   public List<Emails> getCagGroupEmail() throws CustomException {
      return sqcSlaDAO.getCagGroupEmail();
   }
   
}
