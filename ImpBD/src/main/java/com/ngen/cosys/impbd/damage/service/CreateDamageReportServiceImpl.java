package com.ngen.cosys.impbd.damage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.damage.dao.CreateDamageReportDao;
import com.ngen.cosys.impbd.damage.model.DamageReportModel;
import com.ngen.cosys.impbd.damage.model.DamageSearchModel;

@Service
public class CreateDamageReportServiceImpl implements CreateDamageReportService {

   @Autowired
   CreateDamageReportDao dao;

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public Object getDamageInformation(DamageSearchModel damageSearchModel) throws CustomException {
      if (!StringUtils.isEmpty(damageSearchModel.getFlight()) && damageSearchModel.getFlightDate() == null) {
         damageSearchModel.addError("emptyFlightDateErr", "flightDate", ErrorType.ERROR);
      }
      if (damageSearchModel.getFlightDate() != null && StringUtils.isEmpty(damageSearchModel.getFlight())) {
         damageSearchModel.addError("emptyFlightkeyErr", "flight", ErrorType.ERROR);
      }
      return dao.getDamageInformation(damageSearchModel);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public void saveDamageInformation(DamageReportModel damageReportModel) throws CustomException {
      dao.saveDamageInformation(damageReportModel);
   }

   @Override
   public void finalizeDamage(DamageReportModel damageReportModel) throws CustomException {
      dao.finalizeDamage(damageReportModel);
   }

}