package com.ngen.cosys.report.event;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

import com.ngen.cosys.multitenancy.context.TenantContext;

public class NumberToWordsConverter extends DataItemEventAdapter {
	private static final String[] units = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight",
			" Nine" };
	private static final String[] twoDigits = { " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen",
			" Sixteen", " Seventeen", " Eighteen", " Nineteen" };
	private static final String[] tenMultiples = { "", "", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
			" Seventy", " Eighty", " Ninety" };
	private static final String[] placeValues = { "", " Thousand", " Lakh", " Crore", " Arab", " Kharab" };

	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {

		String value = "";
		if ("INR".equals(TenantContext.get().getTenantConfig().getFormat().getCurrencySymbol())
				&& Objects.nonNull(data.getValue())) {
			value = !StringUtils.isBlank(data.getValue().toString()) ? convertNumber(data.getValue().toString()) : "";
		}
		data.setDisplayValue(value);

	}

	private static String convertNumber(String str1) {
		if (str1.contains(".")) {
			str1 = str1.substring(0, str1.indexOf('.'));
		}
		long number = Long.parseLong(str1);
		String word = "";
		int index = 0;
		boolean firstIteration = true;
		int divisor;
		do {
			divisor = firstIteration ? 1000 : 100;
			// take 3 or 2 digits based on iteration
			int num = (int) (number % divisor);
			if (num != 0) {
				String str = ConversionForUptoThreeDigits(num);
				word = str + placeValues[index] + word;
			}
			index++;
			// next batch of digits
			number = number / divisor;
			firstIteration = false;
		} while (number > 0);
		return word + " Rupees Only";
	}

	private static String ConversionForUptoThreeDigits(int number) {
		String word = "";
		int num = number % 100;
		if (num < 10) {
			word = word + units[num];
		} else if (num < 20) {
			word = word + twoDigits[num % 10];
		} else {
			word = tenMultiples[num / 10] + units[num % 10];
		}

		word = (number / 100 > 0) ? units[number / 100] + " hundred" + word : word;
		return word;
	}

}
