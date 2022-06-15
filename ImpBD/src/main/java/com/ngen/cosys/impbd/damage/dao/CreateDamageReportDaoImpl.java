package com.ngen.cosys.impbd.damage.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.damage.model.DamageReportAWBDetails;
import com.ngen.cosys.impbd.damage.model.DamageReportMailDetails;
import com.ngen.cosys.impbd.damage.model.DamageReportModel;
import com.ngen.cosys.impbd.damage.model.DamageReportULDDetails;
import com.ngen.cosys.impbd.damage.model.DamageSearchModel;

@Repository
public class CreateDamageReportDaoImpl extends BaseDAO implements CreateDamageReportDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionShipment;

   @Override
   public Object getDamageInformation(DamageSearchModel damageSearchModel) throws CustomException {
      DamageReportModel damageReport = new DamageReportModel();
      if (!StringUtils.isEmpty(damageSearchModel.getFlight()) && null != damageSearchModel.getFlightDate()) {
         damageReport = fetchObject("DamageReportInfo", damageSearchModel, sqlSessionShipment);
         damageReport.setStatus(super.fetchObject("Damagestatus", damageSearchModel, sqlSessionShipment));
         List<DamageReportULDDetails> listDamageReportULDDetails = fetchList("DamageReportULDListInfo",
               damageSearchModel, sqlSessionShipment);
         if (!CollectionUtils.isEmpty(listDamageReportULDDetails)) {
            damageReport.setListDamageReportULDDetails(listDamageReportULDDetails);
         }
         List<DamageReportAWBDetails> listDamageReportAWBDetails = fetchList("DamageReportAWBListInfo",
               damageSearchModel, sqlSessionShipment);
         if (!CollectionUtils.isEmpty(listDamageReportAWBDetails)) {
            damageReport.setListDamageReportAWBDetails(listDamageReportAWBDetails);
         }
         List<DamageReportMailDetails> listDamageReportMailDetails = fetchList("DamageReportMailListInfo",
               damageSearchModel, sqlSessionShipment);
         if (!CollectionUtils.isEmpty(listDamageReportMailDetails)) {
            damageReport.setListDamageReportMailDetails(listDamageReportMailDetails);
         }
      }
      return damageReport;
   }

   @Override
   public void saveDamageInformation(DamageReportModel damageReportModel) throws CustomException {
      updateData("sqlUpdateDamageAddition", damageReportModel, sqlSessionShipment);
   }

   @Override
   public void finalizeDamage(DamageReportModel damageReportModel) throws CustomException {
      if ("finalize".equalsIgnoreCase(damageReportModel.getFinalizeflag())) {
         super.updateData("sqlUnFinalizeDamageCargo", damageReportModel, sqlSessionShipment);
      } else {
         super.updateData("sqlFinalizeDamageCargo", damageReportModel, sqlSessionShipment);
      }
   }

}