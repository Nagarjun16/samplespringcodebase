package com.ngen.cosys.satssg.interfaces.util;

import com.ngen.cosys.satssg.interfaces.util.MessageParserHelper;

public class ExtractMessageDataParser {
	
	private ExtractMessageDataParser() {
		// Avoid object instantiation
	}

	public static String getFixLenAlphaNumeric(char[] data, int len, int offset) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			return null;
		}

		for (int i = 0; i < len; i++) {
			if (!MessageParserHelper.isAlphaNumeric(data[offset + i])) {
				return null;
			}
		}

		return new String(data, offset, len);
	}

	public static String getFixLenAlphabet(char[] data, int len, int offset) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			return null;
		}

		for (int i = 0; i < len; i++) {
			if (!MessageParserHelper.isAlphabet(data[offset + i])) {
				return null;
			}
		}

		return new String(data, offset, len);
	}

	public static String getFixLenDigit(char[] data, int len, int offset) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			return null;
		}

		for (int i = 0; i < len; i++) {
			if (!MessageParserHelper.isNumber(data[offset + i])) {
				return null;
			}
		}

		return new String(data, offset, len);
	}

	public static String getVarLenAlphaNumeric(char[] data, int len, int offset, char delimiter) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			len = data.length - offset;
		}

		int i;
		for (i = 0; i < len; i++) {
			if (data[offset + i] == delimiter || !MessageParserHelper.isAlphaNumeric(data[offset + i])) {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return new String(data, offset, i);
		}
	}

	public static String getVarLenAlphabet(char[] data, int len, int offset, char delimiter) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			len = data.length - offset;
		}

		int i;
		for (i = 0; i < len; i++) {
			if (data[offset + i] == delimiter || !MessageParserHelper.isAlphabet(data[offset + i])) {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return new String(data, offset, i);
		}
	}

	public static String getVarLenDecimal(char[] data, int len, int offset, int decimalPoint) {
		int decimalPos = 0;
		int noDecimal = 0;
		int i;
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			len = data.length - offset;
		}

		for (i = 0; i < len; i++) {
			if (!MessageParserHelper.isNumber(data[offset + i]) && data[offset + i] != '.') {
				break;
			}
			if (data[offset + i] == '.') {
				decimalPos = i;
				noDecimal++;
			}
		}

		if (i == 0 || noDecimal > 1 || noDecimal == 1 && decimalPos + decimalPoint + 1 < i) {
			return null;
		} else {
			return new String(data, offset, i);
		}
	}

	public static String getVarLenDigit(char data[], int len, int offset) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			len = data.length - offset;
		}
		int i;
		for (i = 0; i < len; i++) {
			if (!MessageParserHelper.isNumber(data[offset + i])) {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return new String(data, offset, i);
		}
	}

	public static String getVarLenFreeText(char[] data, int len, int offset, char delimiter) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length) {
			len = data.length - offset;
		}

		int i;
		for (i = 0; i < len; i++) {
			if (data[offset + i] == delimiter || !MessageParserHelper.isAlphaNumeric(data[offset + i])
					&& data[offset + i] != '-' && data[offset + i] != '.' && data[offset + i] != ' ') {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return new String(data, offset, i);
		}
	}

	public static String getVarLenText(String data, int len, int offset, char[] delimiter) {
		if (data == null) {
			return null;
		}

		if (len + offset > data.length()) {
			len = data.length() - offset;
		}

		int i;
		for (i = 0; i < len; i++) {
			if (MessageParserHelper.anyMatches(data.charAt(offset + i), delimiter)) {
				break;
			}
		}

		if (i == 0) {
			return null;
		} else {
			return data.substring(offset, i + offset);
		}
	}
}
