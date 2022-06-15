/**
 * This is the enum for holding query id's break down summary function
 */
package com.ngen.cosys.impbd.summary.constant;

public enum BreakDownSummarySqlId {

	SQL_GET_BREAK_DOWN_SUMMARY_INFO("sqlGetBreakDownSummaryInfo"),

	SQL_GET_INBOUND_BREAK_TONNAGE_SUMMARY("sqlGetInboundBreakDownTonnageSummary"),

	SQL_GET_INBOUND_BREAK_OTHER_TONNAGE_SUMMARY("sqlGetInboundBreakDownOtherTonnageSummary"),

	SQL_GET_IMP_BREAK_DOWN_SUMMARY_ID("sqlGetBreakDownSummaryId"), SQL_CREATE_IMP_BREAK_DOWN_SUMMARY(
			"sqlInsertBreakDownSummary"), SQL_UPDATE_IMP_BREAK_DOWN_SUMMARY("sqlUpdateBreakDownSummary"),

	SQL_GET_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO_ID(
			"sqlGetBreakDownTonnageULDTrolleySummaryInfoId"), SQL_CREATE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO(
					"sqlCreateBreakDownTonnageULDTrolleySummaryInfo"), SQL_UPDATE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO(
							"sqlUpdateBreakDownTonnageULDTrolleySummaryInfo"), SQL_DELETE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO(
									"sqlDeleteBreakDownTonnageULDTrolleySummaryInfo"),

	SQL_CREATE_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO(
			"sqlCreateBreakDownTonnageULDTrolleySHCSummaryInfo"), SQL_DELETE_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO(
					"sqlDeleteBreakDownTonnageULDTrolleySHCSummaryInfo"), SQL_DELETE_ALL_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO(
							"sqlDeleteAllBreakDownTonnageULDTrolleySHCSummaryInfo"),

	SQL_CREATE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO(
			"sqlCreateBreakDownTonnageSummaryInfo"), SQL_UPDATE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO(
					"sqlUpdateBreakDownTonnageSummaryInfo"), SQL_DELETE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO(
							"sqlDeleteBreakDownTonnageSummaryInfo"), SQL_DELETE_ALL_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO(
									"sqlDeleteAllBreakDownTonnageSummaryInfo"),

	SQL_UPDATE_SERVICE_CONTRACTOR_FEEDBACK("sqlUpdateFeedBackForServiceContractor");

	private String value;

	BreakDownSummarySqlId(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

}