package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngen.cosys.framework.model.BaseBO;

public class SearchFilterModel extends BaseBO {

	@JsonIgnore
	private String frequencyTimeFrom;
	@JsonIgnore
	private String frequencyTimeTo;
	@JsonIgnore
	private String frequencyBetween;
	@JsonIgnore
	private String flightType;

	String station;
	String carrier;
	String awbPrefix;
	String shc;

	String impFlightKey;
	String impFlightDate;

	String expFlightKey;
	String expFlightDate;

	private ArrayList<String> stationList = new ArrayList<String>();
	private ArrayList<String> carrierList = new ArrayList<String>();
	private ArrayList<String> awbPrefixList = new ArrayList<String>();
	private ArrayList<String> shcList = new ArrayList<String>();
	private ArrayList<String> shcBOList = new ArrayList<String>();
	private ArrayList<String> satsCarShcList = new ArrayList<String>();
	private ArrayList<String> satsShcList = new ArrayList<String>();

	private ArrayList<AwbModel> bkgList = new ArrayList<AwbModel>();
	private ArrayList<AwbModel> manList = new ArrayList<AwbModel>();
	private ArrayList<AwbModel> ffmList = new ArrayList<AwbModel>();

	List<SearchFilterModel> searchFilterBOs = new ArrayList<SearchFilterModel>();

	private ArrayList<AwbModel> confList = new ArrayList<AwbModel>();

	private ArrayList<String> carrierList2 = new ArrayList<String>();
	
	@JsonIgnore
	private Integer importDataPushMinusFrom;

	@JsonIgnore
	private Integer importDataPushPlusTo;

	@JsonIgnore
	private Integer exportDataPushMinusFrom;

	@JsonIgnore
	private Integer exportDataPushPlusTo;
	
	
	@JsonIgnore
	private String flightKeyNew;
	
	@JsonIgnore
	private String flightKeyDateNew;	 

	Map<String, Integer> datPushMap = new HashMap<String, Integer>();

	
	 
	public String getFlightKeyNew() {
		return flightKeyNew;
	}

	public void setFlightKeyNew(String flightKeyNew) {
		this.flightKeyNew = flightKeyNew;
	}

	public String getFlightKeyDateNew() {
		return flightKeyDateNew;
	}

	public void setFlightKeyDateNew(String flightKeyDateNew) {
		this.flightKeyDateNew = flightKeyDateNew;
	}

	public String getFrequencyTimeFrom() {
		return frequencyTimeFrom;
	}

	public void setFrequencyTimeFrom(String frequencyTimeFrom) {
		this.frequencyTimeFrom = frequencyTimeFrom;
	}

	public String getFrequencyTimeTo() {
		return frequencyTimeTo;
	}

	public void setFrequencyTimeTo(String frequencyTimeTo) {
		this.frequencyTimeTo = frequencyTimeTo;
	}

	public String getFrequencyBetween() {
		return frequencyBetween;
	}

	public void setFrequencyBetween(String frequencyBetween) {
		this.frequencyBetween = frequencyBetween;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getAwbPrefix() {
		return awbPrefix;
	}

	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	public String getShc() {
		return shc;
	}

	public void setShc(String shc) {
		this.shc = shc;
	}

	public String getImpFlightKey() {
		return impFlightKey;
	}

	public void setImpFlightKey(String impFlightKey) {
		this.impFlightKey = impFlightKey;
	}

	public String getImpFlightDate() {
		return impFlightDate;
	}

	public void setImpFlightDate(String impFlightDate) {
		this.impFlightDate = impFlightDate;
	}

	public String getExpFlightKey() {
		return expFlightKey;
	}

	public void setExpFlightKey(String expFlightKey) {
		this.expFlightKey = expFlightKey;
	}

	public String getExpFlightDate() {
		return expFlightDate;
	}

	public void setExpFlightDate(String expFlightDate) {
		this.expFlightDate = expFlightDate;
	}

	public ArrayList<String> getStationList() {
		return stationList;
	}

	public void setStationList(ArrayList<String> stationList) {
		this.stationList = stationList;
	}

	public ArrayList<String> getCarrierList() {
		return carrierList;
	}

	public void setCarrierList(ArrayList<String> carrierList) {
		this.carrierList = carrierList;
	}

	public ArrayList<String> getAwbPrefixList() {
		return awbPrefixList;
	}

	public void setAwbPrefixList(ArrayList<String> awbPrefixList) {
		this.awbPrefixList = awbPrefixList;
	}

	public ArrayList<String> getShcList() {
		return shcList;
	}

	public void setShcList(ArrayList<String> shcList) {
		this.shcList = shcList;
	}

	public ArrayList<String> getShcBOList() {
		return shcBOList;
	}

	public void setShcBOList(ArrayList<String> shcBOList) {
		this.shcBOList = shcBOList;
	}

	public ArrayList<String> getSatsCarShcList() {
		return satsCarShcList;
	}

	public void setSatsCarShcList(ArrayList<String> satsCarShcList) {
		this.satsCarShcList = satsCarShcList;
	}

	public ArrayList<String> getSatsShcList() {
		return satsShcList;
	}

	public void setSatsShcList(ArrayList<String> satsShcList) {
		this.satsShcList = satsShcList;
	}

	public ArrayList<AwbModel> getBkgList() {
		return bkgList;
	}

	public void setBkgList(ArrayList<AwbModel> bkgList) {
		this.bkgList = bkgList;
	}

	public ArrayList<AwbModel> getManList() {
		return manList;
	}

	public void setManList(ArrayList<AwbModel> manList) {
		this.manList = manList;
	}

	public ArrayList<AwbModel> getFfmList() {
		return ffmList;
	}

	public void setFfmList(ArrayList<AwbModel> ffmList) {
		this.ffmList = ffmList;
	}

	public List<SearchFilterModel> getSearchFilterBOs() {
		return searchFilterBOs;
	}

	public void setSearchFilterBOs(List<SearchFilterModel> searchFilterBOs) {
		this.searchFilterBOs = searchFilterBOs;
	}

	public ArrayList<AwbModel> getConfList() {
		return confList;
	}

	public void setConfList(ArrayList<AwbModel> confList) {
		this.confList = confList;
	}

	public Integer getImportDataPushMinusFrom() {
		return importDataPushMinusFrom;
	}

	public void setImportDataPushMinusFrom(Integer importDataPushMinusFrom) {
		this.importDataPushMinusFrom = importDataPushMinusFrom;
	}

	public Integer getImportDataPushPlusTo() {
		return importDataPushPlusTo;
	}

	public void setImportDataPushPlusTo(Integer importDataPushPlusTo) {
		this.importDataPushPlusTo = importDataPushPlusTo;
	}

	public Integer getExportDataPushMinusFrom() {
		return exportDataPushMinusFrom;
	}

	public void setExportDataPushMinusFrom(Integer exportDataPushMinusFrom) {
		this.exportDataPushMinusFrom = exportDataPushMinusFrom;
	}

	public Integer getExportDataPushPlusTo() {
		return exportDataPushPlusTo;
	}

	public void setExportDataPushPlusTo(Integer exportDataPushPlusTo) {
		this.exportDataPushPlusTo = exportDataPushPlusTo;
	}

	public Map<String, Integer> getDatPushMap() {
		return datPushMap;
	}

	public void setDatPushMap(Map<String, Integer> datPushMap) {
		this.datPushMap = datPushMap;
	}

	public ArrayList<String> getCarrierList2() {
		return carrierList2;
	}

	public void setCarrierList2(ArrayList<String> carrierList2) {
		this.carrierList2 = carrierList2;
	}

	 
}
