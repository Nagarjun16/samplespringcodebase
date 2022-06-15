package com.ngen.cosys.shipment.dao;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;
import com.ngen.cosys.shipment.model.SearchRemarks;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class MaintainRemarkDAOImplTest {
	@InjectMocks
	MaintainRemarkDAOImpl maintainRemarkDao;
	@InjectMocks
	private MaintainRemark maintainRemark1;
	@InjectMocks
	private SearchRemarks searchRemark;
	@InjectMocks
    private RequestSearchRemarksBO request;
	@InjectMocks
	private ResponseSearchRemarksBO requests;
	@InjectMocks
	private DeleteRemarkBO  delete;
	@InjectMocks
    private DeleteRemarkBO  delete1;
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.maintainRemarkDao = Mockito.mock(MaintainRemarkDAOImpl.class);
	}
	
	@Test
	public void testFetch() throws SQLException, CustomException {
		when(this.maintainRemarkDao.fetch(request)).thenReturn(requests);
		assertNotNull(this.maintainRemarkDao.fetch(request));
	}
}
