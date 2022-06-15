package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.client.service.PostHttpUtilitySearchCriteriaService;
import com.ngen.cosys.platform.rfid.tracker.interfaces.dao.CosysRFIDMapperDAO;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.HawbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ULDModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.FormatUtils;
import com.ngen.cosys.platform.rfid.tracker.interfaces.util.SearchFilterUtils;

@Service
@Transactional
public class FlightBookingServiceImpl implements FlightBookingService {
	private static final Logger LOG = LoggerFactory.getLogger(FlightBookingServiceImpl.class);

	@Autowired
	CosysRFIDMapperDAO cosysRFIDMapperDAO;

	@Value("${tracer.platform.search-criteria}")
	private String searchCriteriaEndPoint;

	@Autowired
	private PostHttpUtilitySearchCriteriaService postHttpUtilitySearchCriteriaService;

	public List<AwbModel> getFlightBookingData(AuthModel auth) throws CustomException {
		List<AwbModel> awbs = new ArrayList<AwbModel>();
		List<AwbModel> awbsCarrShc = new ArrayList<AwbModel>();
		List<AwbModel> awbsShc = new ArrayList<AwbModel>();
		List<AwbModel> awbBOs = new ArrayList<AwbModel>();
		List<AwbModel> awbShcBOs = new ArrayList<AwbModel>();
		try {

			List<SearchFilterModel> searchFilterModels = postHttpUtilitySearchCriteriaService
					.onPostExecute(searchCriteriaEndPoint, auth);

			LOG.debug("onPostExecute============  getFlightBookingData" + searchFilterModels);

			SearchFilterModel searchFilterModel = SearchFilterUtils.getSearchFilter(searchFilterModels);

			LOG.debug("Filter============  getFlightBookingData" + searchFilterModel);

			if (null == searchFilterModel) {
				searchFilterModel = new SearchFilterModel();
			}

			if (searchFilterModel.getCarrierList().size() > 0) {
				awbsCarrShc = cosysRFIDMapperDAO.getFlightBookingCarrShcData(searchFilterModel);
				List<String> tempList = new ArrayList<String>();
				List<String> dupList = new ArrayList<String>();

				for (int i1 = 0; i1 < awbsCarrShc.size(); i1++) {
					AwbModel obj = (AwbModel) awbsCarrShc.get(i1);
					 //obj.setSHC_1("XPS");
					if (!tempList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						tempList.add((obj.getFlightKeyDate()) + obj.getAwbNo());
					} else if (!dupList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						dupList.add((obj.getFlightKeyDate()) + obj.getAwbNo());
					}

				}

				for (int i1 = 0; i1 < awbsCarrShc.size(); i1++) {
					AwbModel obj = (AwbModel) awbsCarrShc.get(i1);
					if (!FormatUtils.isEmpty(obj.getSHCS())) {
						StringTokenizer stt = new StringTokenizer(obj.getSHCS(), " ");
						while (stt.hasMoreTokens()) {
							String token = stt.nextToken();
							if (FormatUtils.isEmpty(obj.getSHC_1())) {
								obj.setSHC_1(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_1()) && !obj.getSHC_1().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_2())) {
								obj.setSHC_2(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_2()) && !obj.getSHC_2().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_3())) {
								obj.setSHC_3(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_3()) && !obj.getSHC_3().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_4())) {
								obj.setSHC_4(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_4()) && !obj.getSHC_4().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_5())) {
								obj.setSHC_5(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_5()) && !obj.getSHC_5().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_6())) {
								obj.setSHC_6(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_6()) && !obj.getSHC_6().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_7())) {
								obj.setSHC_7(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_7()) && !obj.getSHC_7().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_8())) {
								obj.setSHC_8(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_8()) && !obj.getSHC_8().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_9())) {
								obj.setSHC_9(token);
							}
						}
					}
				}

				List<ULDModel> uldbos = new ArrayList<ULDModel>();
				List<HawbModel> hawbbos = new ArrayList<HawbModel>();
				AwbModel awbBO = new AwbModel();

				for (int i = 0; i < dupList.size(); i++) {
					awbBO = new AwbModel();
					uldbos = new ArrayList<ULDModel>();
					hawbbos = new ArrayList<HawbModel>();
					for (int i1 = 0; i1 < awbsCarrShc.size(); i1++) {
						AwbModel obj = (AwbModel) awbsCarrShc.get(i1);

						if (dupList.get(i).contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {

							awbBO.setAwbNo(obj.getAwbNo());
							awbBO.setHawbNo(obj.getHawbNo());
							awbBO.setTotalPcs(obj.getTotalPcs());
							awbBO.setFlightNumber(obj.getFlightNumber());
							awbBO.setFlightDate(obj.getFlightDate());
							awbBO.setFlightKey(obj.getFlightKey());
							awbBO.setFlightKeyDate(obj.getFlightKeyDate());
							awbBO.setCarrier(obj.getCarrier());
							awbBO.setOrigin(obj.getOrigin());
							awbBO.setStation(obj.getStation());
							awbBO.setDestination(obj.getDestination());

							for (int j = 0; j < searchFilterModel.getSatsCarShcList().size(); j++) {

								String filter = searchFilterModel.getSatsCarShcList().get(j);
								String shc = filter.substring(0, 3);

								if (!FormatUtils.isEmpty(obj.getSHC_1()) && shc.contains(obj.getSHC_1())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_2()) && shc.contains(obj.getSHC_2())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_3()) && shc.contains(obj.getSHC_3())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_4()) && shc.contains(obj.getSHC_4())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_5()) && shc.contains(obj.getSHC_5())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_6()) && shc.contains(obj.getSHC_6())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_7()) && shc.contains(obj.getSHC_7())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_8()) && !shc.contains(obj.getSHC_8())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_9()) && !shc.contains(obj.getSHC_9())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									}
								}

							}

							if (!FormatUtils.isEmpty(awbBO.getTotalWt()) && !FormatUtils.isEmpty(obj.getTotalWt())) {
								Double weight = Double.parseDouble(awbBO.getTotalWt())
										+ Double.parseDouble(obj.getTotalWt());
								awbBO.setTotalWt(String.valueOf(weight));
							} else {
								awbBO.setTotalWt(obj.getTotalWt());
							}
							if (!FormatUtils.isEmpty(awbBO.getAssignedTotalWt()) && !FormatUtils.isEmpty(obj.getAssignedTotalWt())) {
								Double weight = Double.parseDouble(awbBO.getAssignedTotalWt())
										+ Double.parseDouble(obj.getAssignedTotalWt());
								awbBO.setAssignedTotalWt(String.valueOf(weight));
							} else {
								awbBO.setAssignedTotalWt(obj.getAssignedTotalWt());
							}
							awbBO.setBrdPoint(obj.getBrdPoint());
							awbBO.setOffPoint(obj.getOffPoint());
							awbBO.setWeightUnit(obj.getWeightUnit());
							awbBO.setShipmentType(obj.getShipmentType());
							if (awbBO.getAssignedTotalPcs() != null && obj.getAssignedTotalPcs() != null) {
								Integer assignedTotalPcs = awbBO.getAssignedTotalPcs() + obj.getAssignedTotalPcs();
								awbBO.setAssignedTotalPcs(assignedTotalPcs);
							} else {
								awbBO.setAssignedTotalPcs(obj.getAssignedTotalPcs());
							}

							awbBO.setFlowType(obj.getFlowType());
							awbBO.setTransitType(obj.getTransitType());
							awbBO.setCreated_DateTime(obj.getCreated_DateTime());
							awbBO.setLastUpdated_DateTime(obj.getLastUpdated_DateTime());
							awbBO.setCreatedUser(obj.getCreatedUser());
							awbBO.setLastUpdated_User(obj.getLastUpdated_User());
							awbBO.setStage(obj.getStage());
							awbBO.setUlds(null);
							awbBO.setHawbNos(null);
							if (null != obj.getUlds()) {
								uldbos.addAll(obj.getUlds());
							}
							if (null != obj.getHawbNos()) {
								hawbbos.addAll(obj.getHawbNos());
							}
						}
						int j = i1 + 1;
						if (j == awbsCarrShc.size()) {
							if (awbBO.getAwbNo() != null) {
								awbBO.setUlds(uldbos);
								awbBO.setHawbNos(hawbbos);
								awbBOs.add(awbBO);
							}
						}
					}
				}

				for (AwbModel obj : awbsCarrShc) {
					if (tempList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						if (!dupList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
							awbBOs.add(obj);
						}
					}
				}
				// LOG.debug("getFlightBookingCarrShcData============ awbsCarrShc" +
				// awbsCarrShc.size());
			}
			if (searchFilterModel.getShcList().size() > 0) {
				awbsShc = cosysRFIDMapperDAO.getFlightBookingShcData(searchFilterModel);

				List<String> tempList = new ArrayList<String>();
				List<String> dupList = new ArrayList<String>();

				for (int i1 = 0; i1 < awbsShc.size(); i1++) {
					AwbModel obj = (AwbModel) awbsShc.get(i1);
					// obj.setSHC_1("XPS");
					if (!tempList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						tempList.add((obj.getFlightKeyDate()) + obj.getAwbNo());
					} else if (!dupList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						dupList.add((obj.getFlightKeyDate()) + obj.getAwbNo());
					}
				}

				for (int i1 = 0; i1 < awbsCarrShc.size(); i1++) {
					AwbModel obj = (AwbModel) awbsCarrShc.get(i1);
					if (!FormatUtils.isEmpty(obj.getSHCS())) {
						StringTokenizer stt = new StringTokenizer(obj.getSHCS(), " ");
						while (stt.hasMoreTokens()) {
							String token = stt.nextToken();
							if (FormatUtils.isEmpty(obj.getSHC_1())) {
								obj.setSHC_1(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_1()) && !obj.getSHC_1().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_2())) {
								obj.setSHC_2(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_2()) && !obj.getSHC_2().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_3())) {
								obj.setSHC_3(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_3()) && !obj.getSHC_3().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_4())) {
								obj.setSHC_4(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_4()) && !obj.getSHC_4().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_5())) {
								obj.setSHC_5(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_5()) && !obj.getSHC_5().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_6())) {
								obj.setSHC_6(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_6()) && !obj.getSHC_6().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_7())) {
								obj.setSHC_7(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_7()) && !obj.getSHC_7().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_8())) {
								obj.setSHC_8(token);
							}
							if (!FormatUtils.isEmpty(obj.getSHC_8()) && !obj.getSHC_8().equalsIgnoreCase(token) && FormatUtils.isEmpty(obj.getSHC_9())) {
								obj.setSHC_9(token);
							}
						}
					}
				}

				List<ULDModel> uldbos = new ArrayList<ULDModel>();
				List<HawbModel> hawbbos = new ArrayList<HawbModel>();
				AwbModel awbBO = new AwbModel();

				for (int i = 0; i < dupList.size(); i++) {
					awbBO = new AwbModel();
					uldbos = new ArrayList<ULDModel>();
					hawbbos = new ArrayList<HawbModel>();
					for (int i1 = 0; i1 < awbsShc.size(); i1++) {
						AwbModel obj = (AwbModel) awbsShc.get(i1);

						if (dupList.get(i).contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {

							awbBO.setAwbNo(obj.getAwbNo());
							awbBO.setHawbNo(obj.getHawbNo());
							awbBO.setTotalPcs(obj.getTotalPcs());
							awbBO.setFlightNumber(obj.getFlightNumber());
							awbBO.setFlightDate(obj.getFlightDate());
							awbBO.setFlightKey(obj.getFlightKey());
							awbBO.setCarrier(obj.getCarrier());
							awbBO.setOrigin(obj.getOrigin());
							awbBO.setStation(obj.getStation());
							awbBO.setDestination(obj.getDestination());

							for (int j = 0; j < searchFilterModel.getShcList().size(); j++) {

								String filter = searchFilterModel.getShcList().get(j);
								String shc = filter.substring(0, 3);

								if (!FormatUtils.isEmpty(obj.getSHC_1()) && shc.contains(obj.getSHC_1())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_2()) && shc.contains(obj.getSHC_2())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_3()) && shc.contains(obj.getSHC_3())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_4()) && shc.contains(obj.getSHC_4())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_5()) && shc.contains(obj.getSHC_5())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_6()) && shc.contains(obj.getSHC_6())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_7()) && shc.contains(obj.getSHC_7())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_8()) && !shc.contains(obj.getSHC_8())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									}
								} else if (!FormatUtils.isEmpty(obj.getSHC_9()) && !shc.contains(obj.getSHC_9())) {
									if (FormatUtils.isEmpty(awbBO.getSHC_9())) {
										awbBO.setSHC_9(obj.getSHC_9());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_2())) {
										awbBO.setSHC_2(obj.getSHC_2());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_3())) {
										awbBO.setSHC_3(obj.getSHC_3());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_4())) {
										awbBO.setSHC_4(obj.getSHC_4());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_5())) {
										awbBO.setSHC_5(obj.getSHC_5());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_6())) {
										awbBO.setSHC_6(obj.getSHC_6());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_7())) {
										awbBO.setSHC_7(obj.getSHC_7());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_8())) {
										awbBO.setSHC_8(obj.getSHC_8());
									} else if (FormatUtils.isEmpty(awbBO.getSHC_1())) {
										awbBO.setSHC_1(obj.getSHC_1());
									}
								}

							}

							if (!FormatUtils.isEmpty(awbBO.getTotalWt()) && !FormatUtils.isEmpty(obj.getTotalWt())) {
								Double weight = Double.parseDouble(awbBO.getTotalWt())
										+ Double.parseDouble(obj.getTotalWt());
								awbBO.setTotalWt(String.valueOf(weight));
							} else {
								awbBO.setTotalWt(obj.getTotalWt());
							}
							if (!FormatUtils.isEmpty(awbBO.getAssignedTotalWt()) && !FormatUtils.isEmpty(obj.getAssignedTotalWt())) {
								Double weight = Double.parseDouble(awbBO.getAssignedTotalWt())
										+ Double.parseDouble(obj.getAssignedTotalWt());
								awbBO.setAssignedTotalWt(String.valueOf(weight));
							} else {
								awbBO.setAssignedTotalWt(obj.getAssignedTotalWt());
							}
							awbBO.setBrdPoint(obj.getBrdPoint());
							awbBO.setOffPoint(obj.getOffPoint());
							awbBO.setWeightUnit(obj.getWeightUnit());
							awbBO.setShipmentType(obj.getShipmentType());
							if (awbBO.getAssignedTotalPcs() != null && obj.getAssignedTotalPcs() != null) {
								Integer assignedTotalPcs = awbBO.getAssignedTotalPcs() + obj.getAssignedTotalPcs();
								awbBO.setAssignedTotalPcs(assignedTotalPcs);
							} else {
								awbBO.setAssignedTotalPcs(obj.getAssignedTotalPcs());
							}

							awbBO.setFlowType(obj.getFlowType());
							awbBO.setTransitType(obj.getTransitType());
							awbBO.setCreated_DateTime(obj.getCreated_DateTime());
							awbBO.setLastUpdated_DateTime(obj.getLastUpdated_DateTime());
							awbBO.setCreatedUser(obj.getCreatedUser());
							awbBO.setLastUpdated_User(obj.getLastUpdated_User());
							awbBO.setStage(obj.getStage());
							awbBO.setUlds(null);
							awbBO.setHawbNos(null);
							if (null != obj.getUlds()) {
								uldbos.addAll(obj.getUlds());
							}
							if (null != obj.getHawbNos()) {
								hawbbos.addAll(obj.getHawbNos());
							}
						}
						int j = i1 + 1;
						if (j == awbsCarrShc.size()) {
							if (awbBO.getAwbNo() != null) {
								awbBO.setUlds(uldbos);
								awbBO.setHawbNos(hawbbos);
								awbBOs.add(awbBO);
							}
						}
					}
				}

				for (AwbModel obj : awbsShc) {
					if (tempList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
						if (!dupList.contains((obj.getFlightKeyDate()) + obj.getAwbNo())) {
							awbShcBOs.add(obj);
						}
					}
				}

				// LOG.debug("getFlightBookingShcData============ awbsShc" + awbsShc.size());
			}
			awbs.addAll(awbBOs);
			awbs.addAll(awbShcBOs);
			// LOG.debug("getFlightBookingData ============ awbs" + awbs.size());
		} finally {
			if (null == cosysRFIDMapperDAO) {
				LOG.error("DataSourceConfiguration sqlSession error ");
			}
		}
		return awbs;
	}

}
