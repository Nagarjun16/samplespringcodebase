package com.ngen.cosys.message.resend.model;

public class TypeBMessageGenerator {

	// CR LF
	public static final byte[] LINEFEED_SITA = { 0x0D, 0x0A };
	// SOH
	public final static byte[] START_Of_HEADING = { 0x01 };
	// STX
	public final static byte[] START_Of_TEXT = { 0x02 };
	// ETX
	public final static byte[] END_Of_TEXT = { 0x03 };
	// FULL STOP
	public static final String FULL_STOP = ".";

	// FULL STOP
	public static final String FORWARD_SLASH = "/";

	// SPACE
	public static final byte[] SPACE = { 0x20 };
}
