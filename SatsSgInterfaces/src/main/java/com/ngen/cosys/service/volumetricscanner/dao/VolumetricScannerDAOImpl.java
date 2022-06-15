package com.ngen.cosys.service.volumetricscanner.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightErrorResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightImageResponse;
import com.ngen.cosys.service.volumetricscanner.model.CargoEyeVolWeightResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScannedImage;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricRequest;

@Repository("volumetricScannerDAO")
public class VolumetricScannerDAOImpl extends BaseDAO implements VolumetricScannerDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(VolumetricScannerDAOImpl.class);

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public void cancelScanVolWgtReq(CancelScanVolWgtRequest payload) throws CustomException {
      // Check for Existing Shipment Reference Id
      BigInteger messageId = fetchObject("checkScanVolWgtRequest", payload, sqlSession);
      if (Objects.isNull(messageId)) {
         messageId = sqlSession.selectOne("volumetricRequestMessageReferenceId");
         payload.setMessageId(messageId.longValue());
         insertData("cancelScanVolWgtReq", payload, sqlSession);
      } else {
         payload.setMessageId(messageId.longValue());
      }
   }

   @Override
   public void cancelScanVolWgtRes(CancelScanVolWgtResponse payload) throws CustomException {
      updateData("cancelScanVolWgtRes", payload, sqlSession);
   }

   @Override
   @Deprecated
   public void scanVolWgtReqLog(ScanVolWgtRequest payload) throws CustomException {
      BigInteger messageId = fetchObject("checkScanVolWgtRequest", payload, sqlSession);
      LOGGER.warn("Volumetric Scanner DAO :: ScanVolWgtRequest Message ID - {}, Availability - {}", messageId,
            Objects.nonNull(messageId) ? String.valueOf(true) : String.valueOf(false));
      if (Objects.isNull(messageId)) {
         messageId = sqlSession.selectOne("volumetricRequestMessageReferenceId");
         payload.setMessageId(messageId.longValue());
         insertData("scanVolWgtRequest", payload, sqlSession);
         LOGGER.warn("Volumetric Scanner DAO :: Message ID - {}", messageId);
      } else {
         // UPDATE - NULL Reference SET for New Request If requested again for Scanning
         payload.setMessageId(messageId.longValue());
         updateData("scanVolWgtRequestUpdateForNewRequest", payload, sqlSession);
         LOGGER.warn("Volumetric Scanner DAO :: Message ID Update - {}");
      }
   }

   @Override
   public void scanVolWgtResLog(ScanVolWgtResponse payload) throws CustomException {
      updateData("scanVolWgtResLog", payload, sqlSession);

   }

   @Override
   public void updateVolWgtReqLog(UpdateVolWgtRequest payload) throws CustomException {
      updateData("updateVolWgtReqLog", payload, sqlSession);

   }

   @Override
   public void updateVolWgtResLog(UpdateVolWgtResponse payload) throws CustomException {
      updateData("updateVolWgtResLog", payload, sqlSession);

   }

   @Override
   public BigInteger getVolumetricScannerReferenceLogId(String shipmentNumber) throws CustomException {
      BigInteger messageId = fetchObject("volumetricScannerReferenceLogId", shipmentNumber, sqlSession);
      return messageId;
   }

   @Override
   public void saveVolumetricScanRequest(VolumetricRequest request) throws CustomException {
      Long messageId = sqlSession.selectOne("getVolumetricScanRequestMessageId");
      request.setMessageId(messageId);
      insertData("saveVolumetricScanRequest", request, sqlSession);
   }

   @Override
   public void saveVolumetricScanResponse(CargoEyeVolWeightResponse volumetricScanResponse) throws CustomException {
      updateData("saveVolumetricScanResponse", volumetricScanResponse, sqlSession);
   }

   @Override
   public void saveVolumetricScanErrorResponse(CargoEyeVolWeightErrorResponse volumetricScanErrorResponse)
         throws CustomException {
      updateData("saveVolumetricScanErrorResponse", volumetricScanErrorResponse, sqlSession);
   }

   @Override
   public void saveVolumetricScanImageResponse(ArrayList<ScannedImage> images)
         throws CustomException {
      insertData("saveVolumetricScanImageResponse", images, sqlSession);
   }

   @Override
   public HashMap<String, String> getCargoEyeConfigurations() throws CustomException {
      return fetchObject("getCargoEyeConfigurations", null, sqlSession);
   }

   @Override
   public void deletePreviousVolumeWeightLogData(long previousMessageId) throws CustomException {
      deleteData("deleteUploadedDocsByAssociatedTo", previousMessageId, sqlSession);
      
      deleteData("deleteVolumeWeightLogImages", previousMessageId, sqlSession);
      deleteData("deleteVolumeWeightLog", previousMessageId, sqlSession);
   }

}
