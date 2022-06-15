/**
 * This is a class which holds entire functionality for managing of shipment
 * verification
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.verification.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.verification.constant.ShipmentVerificationSqlId;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

@Repository
public class ShipmentVerificationDAOImpl extends BaseDAO implements ShipmentVerificationDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#create
    * (com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
   public void create(ShipmentVerificationModel shipmentVerification) throws CustomException {
	   System.out.println("triggered");
      this.insertData(ShipmentVerificationSqlId.SQL_INSERT_SHIPMENT_VERIFICATION.toString(), shipmentVerification,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#update
    * (com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION)
   public void update(ShipmentVerificationModel shipmentVerification) throws CustomException {
      if (shipmentVerification.getInvokedFromBreakDown()) {
         this.updateData(ShipmentVerificationSqlId.SQL_UPDATE_SHIPMENT_VERIFICATION_BREAK_DOWN.toString(),
               shipmentVerification, sqlSessionTemplate);
      } else {
         this.updateData(ShipmentVerificationSqlId.SQL_UPDATE_SHIPMENT_VERIFICATION_DOCUMENT.toString(),
               shipmentVerification, sqlSessionTemplate);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO#get(
    * com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel)
    */
   @Override
   public ShipmentVerificationModel get(ShipmentVerificationModel shipmentVerification) throws CustomException {
      return this.fetchObject(ShipmentVerificationSqlId.SQL_GET_SHIPMENT_VERIFICATION.toString(), shipmentVerification,
            sqlSessionTemplate);
   }

   @Override
   public Integer updateDgList(ShipmentVerificationModel shipmentVerification) throws CustomException {
      return this.updateData(ShipmentVerificationSqlId.SQL_UPDATE_DG_CHECKLIST.toString(), shipmentVerification,
            sqlSessionTemplate);
   }

   @Override
   public void insertDgList(ShipmentVerificationModel shipmentVerification) throws CustomException {
      this.insertData(ShipmentVerificationSqlId.SQL_INSERT_DG_CHECKLIST.toString(), shipmentVerification,
            sqlSessionTemplate);

   }

}