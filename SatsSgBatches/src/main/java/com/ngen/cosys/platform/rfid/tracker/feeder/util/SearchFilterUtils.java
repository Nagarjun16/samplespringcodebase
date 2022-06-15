
package com.ngen.cosys.platform.rfid.tracker.feeder.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;

public class SearchFilterUtils {

	private static final Logger LOG = LoggerFactory.getLogger(SearchFilterUtils.class);
	
	public static SearchFilterModel getSearchFilter(List<SearchFilterModel> searchFilterModel) {
		
		SearchFilterModel criteriaBO= new SearchFilterModel(); 
		Set<String> valStr = new HashSet<String>();
		
		for (SearchFilterModel filter : searchFilterModel) {			
			if(!filter.getDatPushMap().isEmpty()){
				criteriaBO.setImportDataPushMinusFrom(filter.getDatPushMap().get("IMPORT_DATA_PUSH_MINUS_HOURS"));
				criteriaBO.setImportDataPushPlusTo(filter.getDatPushMap().get("IMPORT_DATA_PUSH_PLUS_HOURS"));
				criteriaBO.setExportDataPushMinusFrom(filter.getDatPushMap().get("EXPORT_DATA_PUSH_MINUS_HOURS"));
				criteriaBO.setExportDataPushPlusTo(filter.getDatPushMap().get("EXPORT_DATA_PUSH_PLUS_HOURS")); 
				break;
			}else if(filter.getDatPushMap().isEmpty()){
				criteriaBO.setImportDataPushMinusFrom(1400);
				criteriaBO.setImportDataPushPlusTo(1400);
				criteriaBO.setExportDataPushMinusFrom(1400);
				criteriaBO.setExportDataPushPlusTo(1400); 
				break;
		}
		}
		
		
		/*for (SearchFilterModel filter : searchFilterModel) {			
			if(!filter.getDatPushMap().isEmpty()){
				criteriaBO.setImportDataPushMinusFrom(50000);
				criteriaBO.setImportDataPushPlusTo(5400);
				criteriaBO.setExportDataPushMinusFrom(50000);
				criteriaBO.setExportDataPushPlusTo(5400); 
				break;
		}
		}*/
		
		
		/*SearchFilterModel criteriaBO1= new SearchFilterModel(); 
		searchFilterModel = new ArrayList<SearchFilterModel>();
		criteriaBO1.setCarrier("SQ");
		criteriaBO1.setShc("XPS");		
		searchFilterModel.add(criteriaBO1);*/
		
		ArrayList<String> carrierList = new ArrayList<String>();		
		for (SearchFilterModel filter : searchFilterModel) {
			if(null != filter.getCarrier() && !"".equalsIgnoreCase(filter.getCarrier())){
			carrierList.add(filter.getCarrier() + filter.getShc());
			}
		}
		if(valStr!=null){
		valStr.clear();
		valStr = new HashSet<String>(carrierList); 
		criteriaBO.setCarrierList(new ArrayList<String>(valStr));
		}
		
		
	
		/*ArrayList<String> satsCarShcList = new ArrayList<String>();		
		for (SearchFilterModel filter : searchFilterModel) {
			if(null != filter.getCarrier() && !"".equalsIgnoreCase(filter.getCarrier())){
				satsCarShcList.add(filter.getShc() + filter.getCarrier());
			}
		}
		if(valStr!=null){
		valStr.clear();
		valStr = new HashSet<String>(satsCarShcList); 
		criteriaBO.setSatsCarShcList(new ArrayList<String>(valStr));
		}*/
		
		 
		
		
		ArrayList<String> shcList1 = new ArrayList<String>();		
		for (SearchFilterModel filter : searchFilterModel) {
			if(null != filter.getShc() && (null == filter.getCarrier() || "".equalsIgnoreCase(filter.getCarrier()))){
				shcList1.add(filter.getShc());
			}
		}
		if(valStr!=null){
			valStr.clear();
			valStr = new HashSet<String>(shcList1);  
			criteriaBO.setShcList(new ArrayList<String>(valStr));
		}
		
	
		/*ArrayList<String> shcList2 = new ArrayList<String>();		
		for (SearchFilterModel filter : searchFilterModel) {
			if(null != filter.getShc() && (null == filter.getCarrier() || "".equalsIgnoreCase(filter.getCarrier()))){
				shcList2.add(filter.getShc());
			}
		}
		if(valStr!=null){
			valStr.clear();
			valStr = new HashSet<String>(shcList2);  
			criteriaBO.setSatsShcList(new ArrayList<String>(valStr));
		}*/
	 
		return criteriaBO;
	}
}
