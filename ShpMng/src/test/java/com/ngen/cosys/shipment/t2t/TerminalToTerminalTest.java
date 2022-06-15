package com.ngen.cosys.shipment.t2t;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.terminaltoterminalhandover.dao.TerminalToTerminalHandoverDao;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.Flight;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.HandoverTerminalShp;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.ReceipentDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchGroup;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchKey;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SubscriberDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPoint;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPointDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.service.TerminalToTerminalHandoverServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class TerminalToTerminalTest {
   @Before
   public void config() {
      MockitoAnnotations.initMocks(this);
   }

   @Mock
   private TerminalToTerminalHandoverDao terminalToTerminalHandoverDao;

   @InjectMocks
   private TerminalToTerminalHandoverServiceImpl terminalToTerminalHandoverService;

   @InjectMocks
   private SearchKey searchKey;

   @InjectMocks
   private SearchGroup searchGroup;

   @Test
   public void getDetailsOfShipmentFromTerminal()
         throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException,
         InvocationTargetException, NoSuchMethodException, CustomException {
      SearchKey searchKey = new SearchKey();
      searchKey.setAwbUldnumber("61811041881");
      TerminalPointDetails terminalPointDetails = new TerminalPointDetails();

      List<HandoverTerminalShp> handoverTerminalShpList = new ArrayList<HandoverTerminalShp>();
      HandoverTerminalShp e = new HandoverTerminalShp();
      handoverTerminalShpList.add(e);
      terminalPointDetails.setHandoverTerminalShpList(handoverTerminalShpList);
      when(terminalToTerminalHandoverDao.getDetailsOfShipmentFromTerminal(searchKey)).thenReturn(terminalPointDetails);

      terminalToTerminalHandoverService.getDetailsOfShipmentFromTerminal(searchKey);
      verify(terminalToTerminalHandoverDao, atLeastOnce()).getDetailsOfShipmentFromTerminal(searchKey);
   }

   @Test
   public void getDetailsOfShipmentToTerminal()
         throws IllegalArgumentException, SecurityException, ReflectiveOperationException, CustomException {
      SearchGroup searchGroup = new SearchGroup();
      ReceipentDetails receipentDetails = new ReceipentDetails();
      receipentDetails.setReceivedBy("Prateek");
      receipentDetails.setReceivedDate(LocalDateTime.now());
      receipentDetails.setReceiverSignature("receiverSignature");

      SubscriberDetails subscriberDetails = new SubscriberDetails();
      subscriberDetails.setHandedOverBy("handedOverBy");
      subscriberDetails.setHandedOverOn(LocalDateTime.now().minusHours(12));

      TerminalPointDetails terminalPointDetails = new TerminalPointDetails();
      terminalPointDetails.setPurpose("Storage");
      terminalPointDetails.setShpNumber("61811041881");
      terminalPointDetails.setReceipentDetails(receipentDetails);
      terminalPointDetails.setSubscriberDetails(subscriberDetails);
      HandoverTerminalShp handoverTerminalShp = new HandoverTerminalShp();
      Flight flight = new Flight();
      handoverTerminalShp.setFlight(flight);
      terminalPointDetails.setHandoverTerminalShp(handoverTerminalShp);
      List<HandoverTerminalShp> handoverTerminalShpList = new ArrayList<HandoverTerminalShp>();
      handoverTerminalShpList.add(handoverTerminalShp);
      terminalPointDetails.setHandoverTerminalShpList(handoverTerminalShpList);
      TerminalPoint terminalPoint = new TerminalPoint();
      terminalPoint.setToTrml("Mail");
      terminalPoint.setFromTrml("ATF1");

      terminalPoint.setTerminalPointDetails(terminalPointDetails);

      searchGroup.setTerminalPoint(terminalPoint);
      List<TerminalPoint> terminalPointList = new ArrayList<TerminalPoint>();
      terminalPointList.add(terminalPoint);
      searchGroup.setTerminalPointList(terminalPointList);

      when(terminalToTerminalHandoverDao.getDetailsOfShipmentToTerminal(searchGroup)).thenReturn(searchGroup);

      terminalToTerminalHandoverService.getDetailsOfShipmentToTerminal(searchGroup);
      verify(terminalToTerminalHandoverDao, atLeastOnce()).getDetailsOfShipmentToTerminal(searchGroup);
   }

   @Test
   public void getRequestData() throws IllegalArgumentException, SecurityException, ReflectiveOperationException {
      assertNotNull(this);
   }

   @Test
   public void transferShipmentToTerminal()
         throws IllegalArgumentException, SecurityException, ReflectiveOperationException, CustomException {

      SearchGroup searchGroup = new SearchGroup();
      ReceipentDetails receipentDetails = new ReceipentDetails();
      receipentDetails.setReceivedBy("Prateek");
      receipentDetails.setReceivedDate(LocalDateTime.now());
      receipentDetails.setReceiverSignature("receiverSignature");

      SubscriberDetails subscriberDetails = new SubscriberDetails();
      subscriberDetails.setHandedOverBy("handedOverBy");
      LocalDateTime.now().minusHours(12);
      subscriberDetails.setHandedOverOn(LocalDateTime.now().minusHours(12));

      TerminalPointDetails terminalPointDetails = new TerminalPointDetails();
      terminalPointDetails.setPurpose("Storage");
      terminalPointDetails.setShpNumber("61811041881");
      terminalPointDetails.setReceipentDetails(receipentDetails);
      terminalPointDetails.setSubscriberDetails(subscriberDetails);

      TerminalPoint terminalPoint = new TerminalPoint();
      terminalPoint.setToTrml("Mail");
      terminalPoint.setFromTrml("ATF1");

      terminalPoint.setTerminalPointDetails(terminalPointDetails);

      searchGroup.setTerminalPoint(terminalPoint);
      List<TerminalPoint> terminalPointList = new ArrayList<TerminalPoint>();
      terminalPointList.add(terminalPoint);
      searchGroup.setTerminalPointList(terminalPointList);

      when(terminalToTerminalHandoverDao.transferShipmentToTerminal(searchGroup)).thenReturn(searchGroup);

      terminalToTerminalHandoverService.transferShipmentToTerminal(searchGroup);
      verify(terminalToTerminalHandoverDao, atLeastOnce()).transferShipmentToTerminal(searchGroup);
   }
}