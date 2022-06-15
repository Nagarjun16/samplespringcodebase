package com.ngen.cosys;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestSample {

   @Mock
   private DummyModel dummyModel;

   @Before
   public void setupMock() {
      MockitoAnnotations.initMocks(dummyModel);
   }

   @Test
   public void testMockCreation() {
      Assert.assertNotNull(dummyModel);
   }

}
