package com.ngen.cosys.platform.rfid.tracker.feeder.util;

public enum STATUS {
	OK(200), FAILED(100);
	public int statusCode;

	STATUS(int statusCode) {
		this.statusCode = statusCode;
	}
}
