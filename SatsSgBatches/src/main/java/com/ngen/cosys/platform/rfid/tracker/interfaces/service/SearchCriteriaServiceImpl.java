package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilitySearchCriteriaService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilityService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.dao.CosysRFIDMapperDAO;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchRequest;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.SearchFilterUtils;

@Service
@Transactional
public class SearchCriteriaServiceImpl implements SearchCriteriaService {

	private static final Logger LOG = LoggerFactory.getLogger(SearchCriteriaServiceImpl.class);

	@Autowired
	CosysRFIDMapperDAO cosysRFIDMapperDAO;

	@Value("${tracer.platform.search-criteria}")
	private String searchCriteriaEndPoint;
	
	@Autowired
	private PostHttpUtilityService postHttpUtilityService;

	@Autowired
	private PostHttpUtilitySearchCriteriaService postHttpUtilitySearchCriteriaService;

	@Override
	public int getConfirmSearchCriteria(SearchRequest request) throws CustomException {
		AuthModel auth = null;
		int result = 0;
		try {
			auth = postHttpUtilityService.getAuthUserPassword();

			List<SearchFilterModel> searchFilterModels = postHttpUtilitySearchCriteriaService
					.onPostExecute(searchCriteriaEndPoint, auth);
			LOG.debug("onPostExecute============  getConfirmSearchCriteria" + searchFilterModels);

			SearchFilterModel searchFilterBO = SearchFilterUtils.getSearchFilter(searchFilterModels);
			LOG.debug("Filter============  getConfirmSearchCriteria" + searchFilterBO);

			if(!ObjectUtils.isEmpty(searchFilterBO)) {
				if (searchFilterBO.getCarrierList().size() > 0) {
					// If SHC contains comma(,)
					List<String> shcs = Stream.of(request.getShc().split(",")).collect(Collectors.toList());
					if(request.getShc().contains(",")) {
						for (String carrShcRequest : searchFilterBO.getCarrierList()) {
							for (String shc : shcs) {
								if(carrShcRequest.substring(0, 2).equalsIgnoreCase(request.getCarrierCode()) 
										&& carrShcRequest.substring(2, 5).equalsIgnoreCase(shc.trim())) {
									result = 1;
									break;
								}
							}
						}
					} else {
						String shc=request.getShc().trim();
						boolean carrierShcMatch = searchFilterBO.getCarrierList().contains(request.getCarrierCode().concat(shc));
						if(carrierShcMatch) {
							result = 1;
						}
					}
				}
			}
		} finally {
			if (null == cosysRFIDMapperDAO) {
				LOG.error("DataSourceConfiguration sqlSession error ");
			}
		}
		return result;
	}

}
