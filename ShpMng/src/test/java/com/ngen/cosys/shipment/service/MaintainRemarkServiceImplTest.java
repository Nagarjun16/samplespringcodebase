/*package com.ngen.cosys.shipment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.dao.CommonFlightIdDAO;
import com.ngen.cosys.shipment.dao.CommonFlightIdDAOImpl;
import com.ngen.cosys.shipment.dao.MaintainRemarkDAO;
import com.ngen.cosys.shipment.model.CommonFlightId;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.shipment.model.SearchRemarks;

public class MaintainRemarkServiceImplTest {

   @Mock
   private MaintainRemarkDAO maintainRemarkDao;
   @InjectMocks
   private MaintainRemarkServiceImpl maintainRemarkService;
   @InjectMocks
   private MaintainRemark maintainRemark1;
   @InjectMocks
   private MaintainRemark maintainRemark2;
   @InjectMocks
   private SearchRemarks searchRemarks1;
   @Mock
   private CommonFlightIdDAO commonFlightIdDAO;
   @InjectMocks
   private CommonFlightIdDAOImpl commonFlightIdDAOImpl;
   @InjectMocks
   private CommonFlightId commonFlightId;
   @InjectMocks
   private RequestSearchRemarksBO request;
   @InjectMocks
   private ResponseSearchRemarksBO requests;
 

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
   }

   
   @Test
   public void testDeleteRemarkt() throws SQLException, CustomException {
      MaintainRemark maintainRemark = new MaintainRemark(1211, null, 0, (long) 0, null, null, null, null, null, null, null, null, null, null);

     // MaintainRemark mr = new MaintainRemark();
      List<MaintainRemark> maintainRemarkList = new ArrayList<>();
      maintainRemark1.setShipmentRemarkId(1211);
      maintainRemarkList.add(maintainRemark);
      when(this.maintainRemarkDao.fetch(request)).thenReturn(requests);
      assertNotNull(this.maintainRemarkDao.fetch(request));
 
      assertEquals(0, maintainRemark1.getShipmentId());
      assertNotEquals(121, maintainRemark1.getShipmentId());
   }

}
*/