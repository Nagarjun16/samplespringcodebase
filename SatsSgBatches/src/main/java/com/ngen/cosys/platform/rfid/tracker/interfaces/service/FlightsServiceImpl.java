package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilitySearchCriteriaService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.dao.CosysRFIDMapperDAO;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.FormatUtils;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.SearchFilterUtils;

@Service
@Transactional
public class FlightsServiceImpl implements FlightsService {

	private static final Logger LOG = LoggerFactory.getLogger(FlightsServiceImpl.class);

	@Autowired
	CosysRFIDMapperDAO cosysRFIDMapperDAO;

	@Value("${tracer.platform.search-criteria}")
	private String searchCriteriaEndPoint;

	@Autowired
	private PostHttpUtilitySearchCriteriaService postHttpUtilitySearchCriteriaService;

	public List<FlightsModel> getFlightData(AuthModel auth) {
		List<FlightsModel> flights = new ArrayList<FlightsModel>();

		try {
			List<SearchFilterModel> searchFilterModels = postHttpUtilitySearchCriteriaService
				.onPostExecute(searchCriteriaEndPoint, auth);

			LOG.debug("onPostExecute============  getFlightData" + searchFilterModels);

			SearchFilterModel searchFilterBO = SearchFilterUtils.getSearchFilter(searchFilterModels);

			LOG.debug("Filter============  getFlightData" + searchFilterBO);

			if (null == searchFilterBO) {
				searchFilterBO = new SearchFilterModel();
			}

			// getbooking list
			ArrayList<AwbModel> bkgList = new ArrayList<AwbModel>();

			if (searchFilterBO.getCarrierList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getFlightBookingCarrShcData(searchFilterBO));
				LOG.info("getFlightBookingCarrShcData ============  " + bkgList.size());
			}

			if (searchFilterBO.getShcList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getFlightBookingShcData(searchFilterBO));
			}

			// Manifest
			if (searchFilterBO.getCarrierList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getFlightManifestedCarrShcData(searchFilterBO));
				LOG.info("getFlightManifestedCarrShcData ============  " + bkgList.size());
			}

			if (searchFilterBO.getShcList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getFlightManifestedShcData(searchFilterBO));
			}

			// FFM
			if (searchFilterBO.getCarrierList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getImportFFMCarrShcData(searchFilterBO));
			}

			if (searchFilterBO.getShcList().size() > 0) {
				bkgList.addAll(cosysRFIDMapperDAO.getImportFFMShcData(searchFilterBO));
			}

			
			searchFilterBO.getBkgList().addAll(bkgList);

			ArrayList<String> arrayList = new ArrayList<String>();
			if (null != searchFilterBO.getBkgList()) {
				for (int i = 0; i < searchFilterBO.getBkgList().size(); i++) {
					arrayList.add(searchFilterBO.getBkgList().get(i).getFlightKeyDate());
				}
			}
			Set<String> set = new HashSet<String>(arrayList);
			ArrayList<AwbModel> bos = new ArrayList<AwbModel>();

			for (String localSet : set) {
				AwbModel bo = new AwbModel();
				bo.setFlightKeyDate(localSet);
				String val = String.valueOf(localSet.charAt(localSet.length() - 1));
				if("I".equals(val)) {
					String str1 = localSet.substring(0, localSet.length() - 1);	
					String s1 = str1.substring(str1.length() - 11);
					bo.setImpFlightKeyDate(s1);
					String str2 = localSet.substring(0,localSet.length() - 12);	
					bo.setImpFlightKey(str2);					
				}
				
				if("E".equals(val)) {
					String str1 = localSet.substring(0, localSet.length() - 1);	
					String s1 = str1.substring(str1.length() - 11);
					bo.setExpFlightKeyDate(s1);
					String str2 = localSet.substring(0,localSet.length() - 12);	
					bo.setExpFlightKey(str2);
				}
				
				bos.add(bo);
			}
			
			
			if (null != searchFilterBO.getBkgList()) {
				searchFilterBO.getBkgList().clear();
				searchFilterBO.setBkgList(bos);
			}

			if (bos.size() > 0) {
				flights = groupFlightDataPush(searchFilterBO, 100);
				// LOG.info("flights ============ " + flights.size());
				// flights = cosysRFIDMapperDAO.getFlightData(searchFilterBO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null == cosysRFIDMapperDAO) {
				LOG.error("DataSourceConfiguration sqlSession error ");
			}
		}
		return flights;
	}

	public List<FlightsModel> groupFlightDataPush(SearchFilterModel searchFilterBO, int groupNumber)
			throws IOException {
		int arraySize = searchFilterBO.getBkgList().size();
//		int arraySize2 = searchFilterBO.getBkgList().size();
//		int count = 0;
		List<FlightsModel> flights = new ArrayList<>();
//		ArrayList<AwbModel> subResult = new ArrayList<>(); 
		try {
			/*for (int i = 0; i < arraySize; i++) {
				if (count == groupNumber) {
					SearchFilterModel filterBO = new SearchFilterModel();
					filterBO.setBkgList(subResult);

					for (int j = 0; i < arraySize2; j++) {
					if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(j).getImpFlightKey())) {
						filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(j).getImpFlightKey());
						filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(j).getImpFlightKeyDate());	
					}					
					if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(j).getExpFlightKey())) {
						filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(j).getExpFlightKey());
						filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(j).getExpFlightKeyDate());
					}
					
					filterBO.setImportDataPushMinusFrom(searchFilterBO.getImportDataPushMinusFrom());
					filterBO.setImportDataPushPlusTo(searchFilterBO.getImportDataPushPlusTo());
					filterBO.setExportDataPushMinusFrom(searchFilterBO.getExportDataPushMinusFrom());
					filterBO.setExportDataPushPlusTo(searchFilterBO.getExportDataPushPlusTo());
					flights.addAll(cosysRFIDMapperDAO.getFlightData(filterBO));
					if(searchFilterBO.getBkgList().size()>0) {
					searchFilterBO.getBkgList().remove(j);
					}
					}
					count = 0;
					subResult = new ArrayList<>();
				}
				subResult.add(searchFilterBO.getBkgList().get(i));
				count++;
				if (count == arraySize) {
					SearchFilterModel filterBO = new SearchFilterModel();
					
					filterBO.setBkgList(subResult);

					for (int k = 0; i < arraySize2; k++) {
					if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(k).getImpFlightKey())) {
						filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(k).getImpFlightKey());
						filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(k).getImpFlightKeyDate());	
					}					
					if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(k).getExpFlightKey())) {
						filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(k).getExpFlightKey());
						filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(k).getExpFlightKeyDate());
					}
					
					filterBO.setImportDataPushMinusFrom(searchFilterBO.getImportDataPushMinusFrom());
					filterBO.setImportDataPushPlusTo(searchFilterBO.getImportDataPushPlusTo());
					filterBO.setExportDataPushMinusFrom(searchFilterBO.getExportDataPushMinusFrom());
					filterBO.setExportDataPushPlusTo(searchFilterBO.getExportDataPushPlusTo());
					flights.addAll(cosysRFIDMapperDAO.getFlightData(filterBO));
					if(searchFilterBO.getBkgList().size()>0) {
					searchFilterBO.getBkgList().remove(k);
					}
					}
					count = 0;
				}
			}
			*/
			for (int i = 0; i < arraySize; i++) {
			//if (count != 0) {
				SearchFilterModel filterBO = new SearchFilterModel();
				//filterBO.setBkgList(subResult);

				if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(i).getImpFlightKey())) {
					filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(i).getImpFlightKey());
					filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(i).getImpFlightKeyDate());	
				}					
				if(!FormatUtils.isEmpty(searchFilterBO.getBkgList().get(i).getExpFlightKey())) {
					filterBO.setFlightKeyNew(searchFilterBO.getBkgList().get(i).getExpFlightKey());
					filterBO.setFlightKeyDateNew(searchFilterBO.getBkgList().get(i).getExpFlightKeyDate());
				}
				
				filterBO.setImportDataPushMinusFrom(searchFilterBO.getImportDataPushMinusFrom());
				filterBO.setImportDataPushPlusTo(searchFilterBO.getImportDataPushPlusTo());
				filterBO.setExportDataPushMinusFrom(searchFilterBO.getExportDataPushMinusFrom());
				filterBO.setExportDataPushPlusTo(searchFilterBO.getExportDataPushPlusTo()); 
				flights.addAll(cosysRFIDMapperDAO.getFlightData(filterBO));
			//}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return flights;
	}
 

}
