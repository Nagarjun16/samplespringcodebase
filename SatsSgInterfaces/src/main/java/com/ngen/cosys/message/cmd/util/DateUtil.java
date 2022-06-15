package com.ngen.cosys.message.cmd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;    

public class DateUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	private DateUtil() {
		// Avoid object instantiation
	}

	public static java.util.Date getDateFromFormat(String sDate, String dateFormat) {
		java.util.Date date = null;
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(dateFormat);
			date = sdf.parse(sDate);
		} catch (Exception e) {
			LOG.error("Exception while formatting String to Date", e);
		}
		return date;
	}

	public static String getStringFromDate(Date date, String dateFormat) {
		String sDate = null;
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(dateFormat);
			sDate = sdf.format(date);
		} catch (Exception e) {
			LOG.error("Exception while formatting Date to String", e);
		}
		return sDate;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date, boolean setDefaultYear) {

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		// Set current year
		if (setDefaultYear) {
			gcal.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		}
		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for a date", e);
		}
		return xgcal;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(int day, int month, int time) {
		StringBuilder sTime = new StringBuilder();
		int timeLength = String.valueOf(time).length();
		if (timeLength <= 4) {
			for (int f = 0; f < timeLength; f++) {
				sTime.append("0");
			}
		}
		sTime.append(String.valueOf(time));

		// Derive Hours
		String hours = sTime.substring(0, 2);

		// Derive Minutes
		String minutes = sTime.substring(2, 4);

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(Calendar.DATE, day);
		gcal.set(Calendar.MONTH, month);
		gcal.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		gcal.set(Calendar.HOUR, Integer.parseInt(hours));
		gcal.set(Calendar.MINUTE, Integer.parseInt(minutes));

		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for day/month(i)/time",
					e);
		}
		return xgcal;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(int day, String month, int time) {
		StringBuilder sTime = new StringBuilder();
		int timeLength = String.valueOf(time).length();
		if (timeLength <= 4) {
			for (int f = 0; f < timeLength; f++) {
				sTime.append("0");
			}
		}
		sTime.append(String.valueOf(time));

		// Derive Hours
		String hours = sTime.substring(0, 2);

		// Derive Minutes
		String minutes = sTime.substring(2, 4);

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(Calendar.DATE, day);
		gcal.set(Calendar.MONTH, getMonth(month));
		gcal.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		gcal.set(Calendar.HOUR, Integer.parseInt(hours));
		gcal.set(Calendar.MINUTE, Integer.parseInt(minutes));

		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for day/month(s)/time",
					e);
		}
		return xgcal;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendarByDayMonth(int day, String month) {

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(Calendar.DATE, day);
		gcal.set(Calendar.MONTH, getMonth(month));
		gcal.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for day/month(s)", e);
		}
		return xgcal;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendarByDayMonthYear(int day, String month, int year) {

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(Calendar.DATE, day);
		gcal.set(Calendar.MONTH, getMonth(month));
		gcal.set(Calendar.YEAR, year);

		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for day/month(s)/year",
					e);
		}
		return xgcal;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendarByTime(int time) {
		StringBuilder sTime = new StringBuilder();
		int timeLength = String.valueOf(time).length();
		if (timeLength <= 4) {
			for (int f = 0; f < timeLength; f++) {
				sTime.append("0");
			}
		}
		sTime.append(String.valueOf(time));

		// Derive Hours
		String hours = sTime.substring(0, 2);

		// Derive Minutes
		String minutes = sTime.substring(2, 4);

		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(Calendar.HOUR, Integer.parseInt(hours));
		gcal.set(Calendar.MINUTE, Integer.parseInt(minutes));

		XMLGregorianCalendar xgcal = null;
		try {
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e) {
			LOG.error("DatatypeConfigurationException while deriving XMLGregorianCalendar object for time", e);
		}
		return xgcal;
	}

	public static int getMonth(String month) {
		try {
			Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH) + 1;
		} catch (ParseException parseException) {
			LOG.error("ParseException while deriving month " + month + " for a given string", parseException);
			return 13;
		}
	}

}