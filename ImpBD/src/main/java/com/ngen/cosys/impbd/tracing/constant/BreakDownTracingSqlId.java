package com.ngen.cosys.impbd.tracing.constant;

public enum BreakDownTracingSqlId {

	SQL_GET_BREAK_DOWN_TRACING_INFO("sqlGetBreakDownTracingInformation");

	private final String queryId;

	BreakDownTracingSqlId(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return this.queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}