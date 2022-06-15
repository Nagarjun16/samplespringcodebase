package com.ngen.cosys.shipment.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.shipment.service.MaintainRemarkService;

/**
 * This class is Unit Testing for Maintain Remark Controller.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class MaintainRemarkControllerTest {

   @InjectMocks
   private MaintainRemarkController maintainRemarkController;
   @Mock
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   @Mock
   private MaintainRemarkService maintainRemarkService;
   @InjectMocks
   private BaseResponse baseResponse;
   @InjectMocks
   private ResponseSearchRemarksBO maintainRemarkMock1;
   @InjectMocks
   private DeleteRemarkBO deleteRemark;
   @InjectMocks
   private RequestSearchRemarksBO searchRemarks1;

   /**
    * This method tests the fetch method of Maintain Remark.
    * 
    * @throws SQLException
    * @throws CustomException
    */
   @Test
   public void testFetch() throws CustomException {

      // maintainRemarks = Arrays.asList(maintainRemarkMock1,maintainRemarkMock2);

      when(maintainRemarkService.getRemark(searchRemarks1)).thenReturn(maintainRemarkMock1);
      when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);

      BaseResponse<ResponseSearchRemarksBO> result = maintainRemarkController.fetchRemarkList(searchRemarks1);
      assertNotNull(result.getData());
   }
}