package com.ngen.cosys.satssg.interfaces.util;

public class MessageParserHelper {

	private static final String[] MONTH = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV",
	"DEC" };

	private MessageParserHelper() {
		// Avoid object instantiation
	}

	public static boolean anyMatches(char src, char[] delimiter) {
		for (int i = 0; i < delimiter.length; i++)
			if (src == delimiter[i])
				return true;

		return false;
	}

	public static boolean isAlphaNumeric(char ch) {
		return isNumber(ch) || isAlphabet(ch);
	}

	public static boolean isAlphabet(char ch) {


		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <='z' )||( ch==' ');
	}

	public static boolean isAlphabetString(String checkStr) {
		if (checkStr == null)
			return false;
		for (int i = 0; i < checkStr.length(); i++)
			if (!isAlphabet(Character.toUpperCase(checkStr.charAt(i))))
				return false;

		return true;
	}

	public static boolean isNumber(char ch) {
		return Character.isDigit(ch);
	}

	public static int isStringInArray(String[] strArray, String key) {
		if (key != null) {
			for (int i = 0; i < strArray.length; i++)
				if (strArray[i].compareTo(key) == 0)
					return i;
		}
		return -1;
	}

	public static int isValidMonth(String mth) {
		return isStringInArray(MONTH, mth);

	}

	public static boolean validateIntegerRange(Number value, Number min, Number max, boolean allowFractions) {
		return minValue(value, min, allowFractions) && maxValue(value, max, allowFractions);
	}

	private static boolean minValue(Number value, Number min, boolean allowFractions) {
		if (allowFractions) {
			return value.doubleValue() >= min.doubleValue();
		} else {
			return value.longValue() >= min.longValue();
		}
	}

	private static boolean maxValue(Number value, Number max, boolean allowFractions) {
		if (allowFractions) {
			return value.doubleValue() <= max.doubleValue();
		} else {
			return value.longValue() <= max.longValue();
		}
	}
}
