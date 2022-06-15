package com.ngen.cosys.impbd.damage.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.damage.model.DamageReportModel;
import com.ngen.cosys.impbd.damage.model.DamageSearchModel;

public interface CreateDamageReportDao {

   Object getDamageInformation(DamageSearchModel damageSearchModel) throws CustomException;

   void saveDamageInformation(DamageReportModel damageReportModel) throws CustomException;

   void finalizeDamage(DamageReportModel damageReportModel) throws CustomException;

}