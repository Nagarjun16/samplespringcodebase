/**
 * Configuration Constant
 */
package com.ngen.cosys.common;

import java.io.FileWriter;
import java.math.BigInteger;

/**
 * Configuration Constant
 */
public final class ESCPrinter {
	public static int MAX_ADVANCE_9PIN = 216; //for 24/48 pin esc/p2 printers this should be 180
    public static int MAX_ADVANCE_24PIN = 180;
    public static int MAX_UNITS = 127; //for vertical positioning range is between 0 - 255 (0 <= n <= 255) according to epson ref. but 255 gives weird errors at 1.5f, 127 as max (0 - 128) seems to be working
    public static final float CM_PER_INCH = 2.54f;
	FileWriter fw;
	 /* decimal ascii values for epson ESC/P commands */
    public static final char ESC = 27; //escape
    public static final char AT = 64; //@
    public static final char LINE_FEED = 10; //line feed/new line
    public static final char PARENTHESIS_LEFT = 40;
    public static final char BACKSLASH = 92;
    public static final char DLE = 16; // Data link Escape (BARCODE)
    public static final char BS = 8; // Backspace (BARCODE)
    public static final char STX =2; // font type - courier (BARCODE)
    public static final char ETX = 3;// end of text (BARCODE)
    public static final char SOH = 1; // start of header (BARCODE)
    public static final char VT = 17; //  (BARCODE)
    public static final char SET = 65;
    public static final char CR = 13; //carriage return
    public static final char TAB = 9; //horizontal tab
    public static final char FF = 12; //form feed
    public static final char p = 112; //used for choosing proportional mode or fixed-pitch
    public static final char t = 116; //used for character set assignment/selection
    public static final char l = 108; //used for setting left margin
    public static final char x = 120; //used for setting draft or letter quality (LQ) printing
    public static final char E = 69; //bold font on
    public static final char F = 70; //bold font off
    public static final char J = 74; //used for advancing paper vertically
    public static final char P = 80; //10cpi pitch
    public static final char M = 77; //12cpi pitch
    public static final char g = 103; //15cpi pitch
    public static final char Q = 81; //used for setting right margin
    public static final char W = 87;
    public static final char w = 119;
    public static final char $ = 36; //used for absolute horizontal positioning
    public static final char B = 66;
    public static final char ASTERISK = 42;
    public static final char ARGUMENT_0 = 0;
    public static final char ARGUMENT_1 = 1;
    public static final char ARGUMENT_2 = 2;
    public static final char ARGUMENT_3 = 3;
    public static final char ARGUMENT_4 = 4;
    public static final char ARGUMENT_5 = 5;
    public static final char ARGUMENT_6 = 6;
    public static final char ARGUMENT_7 = 7;
    public static final char ARGUMENT_14 = 14;
    public static final char ARGUMENT_25 = 25;
    public static final char[] NUL = new String("0x00").toCharArray();
    public static final char HYPHEN = 45;
    public static final char FOURTYNINE = 49;
    public static final char FOURTYEIGHT = 48;
    /* character sets */
    public static final char USA = ARGUMENT_1;
    public static final char BRAZIL = ARGUMENT_25;
    public static final char SINGLEQUOTES = 39;
    
   
    public static StringBuilder setCharacterSet(char charset){
    	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append(PARENTHESIS_LEFT);
    	sb.append(t);
    	sb.append(ARGUMENT_3); //always 3
    	sb.append(ARGUMENT_0); //always 0
    	sb.append(ARGUMENT_1); //selectable character table 1
    	sb.append(charset); //registered character table (arg_25 is brascii)
    	sb.append(ARGUMENT_0); //always 0
    	sb.append(ESC);
    	sb.append(t);
    	sb.append(ARGUMENT_1); //selectable character table 1
    	return sb;
    }
    
    public static StringBuilder setCharacterSet2(char charset){
    	StringBuilder sb = new StringBuilder();    	
    	return sb;
    }
    
    public static StringBuilder selectLQPrinting(){ //set letter quality printing
    	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append(x);
    	sb.append((char) 48);
    	return sb;
    }
    static boolean escp24pin = false;
    public static StringBuilder advanceVertical(float centimeters){
    	StringBuilder sb = new StringBuilder();
        //pre: centimeters >= 0 (cm)
        //post: advances vertical print position approx. y centimeters (not precise due to truncation)
        float inches = centimeters / CM_PER_INCH;
        int units = (int) (inches * (escp24pin ? MAX_ADVANCE_24PIN : MAX_ADVANCE_9PIN));
        
        while (units > 0) {
            char n;
            if (units > MAX_UNITS)
                n = (char) MAX_UNITS; //want to move more than range of parameter allows (0 - 255) so move max amount
            else
                n = (char) units; //want to move a distance which fits in range of parameter (0 - 255)
                        
            sb.append(ESC);
            sb.append(J);
            sb.append(n);
            
            units -= MAX_UNITS;
        }
		return sb;
    }
    
    public static StringBuilder resetPrinter() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append("E");
    	return sb;
    }
    
    public static StringBuilder setAbsoluteHorizontalPosition(float centimeters){
    	StringBuilder sb = new StringBuilder();
        //pre: centimenters >= 0 (cm)
        //post: sets absolute horizontal print position to x centimeters from left margin
        float inches = centimeters / CM_PER_INCH;
        int units_low = (int) (inches * 60) % 256;
        int units_high = (int) (inches * 60) / 256;
        
        sb.append(ESC);
        sb.append($);
        sb.append((char) units_low);
        sb.append((char) units_high);
    return sb;
    }
    
    // Not in use
 /*   public static String setAbsoluteHVerticalPosition(float verticalPosition, float topMarginPosition){
    	StringBuilder sb = new StringBuilder();
	        float inches = verticalPosition / CM_PER_INCH;
	        int units_low =  (int)((verticalPosition - topMarginPosition)*(1/CM_PER_INCH))  % 256;
	        int units_high = (int)((verticalPosition - topMarginPosition)*(1/CM_PER_INCH)) / 256;
	        
	        sb.append(ESC);
	        sb.append(PARENTHESIS_LEFT);
	        sb.append('V');
	        sb.append('2');
	        sb.append('0');
	        sb.append((char) units_low);
	        sb.append((char) units_high);
        return sb.toString();
    }*/
    
    public static StringBuilder bold(boolean bold) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
        if (bold)
        	sb.append(E);
        else
        	sb.append(F);
        return sb;
    }

    public static StringBuilder print(String text) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(text);
    	return sb;
    }
    
    public static StringBuilder printLight(String text) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(superSubscript(0));
    	sb.append(text);
    	sb.append(cancelSuperSubscript());
    	return sb;
    }

    public static StringBuilder printBold(String text) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(bold(true));
    	sb.append(text);
    	sb.append(bold(false));
    	return sb;
    }
    
    public static StringBuilder setBarcode(String barcodeNumber){
    	StringBuilder sb = new StringBuilder();
//    	barcodeNumber="B" + barcodeNumber;
	    	sb.append(ESC);
	    	sb.append(DLE);
	    	sb.append("A");
	    	sb.append(BS);
	    	sb.append(STX);
	    	sb.append("  ");
	    	sb.append(ETX);
	    	sb.append(SOH);
	    	sb.append(SOH);
	    	sb.append(SOH);
	    	sb.append(" ");// barcode configuration setting
	    	sb.append(ESC);
	    	sb.append(DLE);
	    	sb.append("B");
	    	sb.append(VT); // amount of data in bytes
	    	sb.append(barcodeNumber); // setting of barcode data
	    	sb.append(ESC);
	    	sb.append("k");
	    	sb.append(STX);// font type
	    	sb.append(ESC);
	    	sb.append("J");
	    	sb.append(VT); // line feed 
    	
//    		sb.append(ESC);
//    		sb.append("(");
//    		sb.append("B");
//    		sb.append(Character.toChars(Math.floorMod((6+barcodeNumber.length()),256))); // Bytes of the bar code string
//    		sb.append(Character.toChars((6+barcodeNumber.length())/256)); // Bytes of the bar code string
//    		sb.append(Character.toChars(6)); // k barcode 6 - Code128
//    		sb.append(Character.toChars(1)); // m barcode width (02 to 05)
//    		sb.append(Character.toChars(0)); // s barcode space between (-3 to +3)
//    		sb.append(Character.toChars(20)); // v1 barcode length 
//    		sb.append(Character.toChars(0)); // v2 barcode length
//    		sb.append(Character.toChars(2)); //control fag... 3 control character and human readable
//    		sb.append(barcodeNumber);
//    		
//    		System.out.println(String.format("%040x", new BigInteger(1, sb.toString().getBytes())));
    		
    	return sb;
   }
    public static StringBuilder setNawbBarcode(String barcodeNumber) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
		sb.append("(");
		sb.append("B");
		sb.append(Character.toChars(Math.floorMod((6+barcodeNumber.length()),256))); // Bytes of the bar code string
		sb.append(Character.toChars((6+barcodeNumber.length())/256)); // Bytes of the bar code string
		sb.append(Character.toChars(6)); // k barcode 6 - Code128
		sb.append(Character.toChars(1)); // m barcode width (02 to 05)
		sb.append(Character.toChars(0)); // s barcode space between (-3 to +3)
		sb.append(Character.toChars(20)); // v1 barcode length 
		sb.append(Character.toChars(0)); // v2 barcode length
		sb.append(Character.toChars(2)); //control fag... 3 control character and human readable
		sb.append(barcodeNumber);	
		
	return sb;

    }
    public static StringBuilder setBarcode39(String barcodeNumber){
    	StringBuilder sb = new StringBuilder();
    		sb.append(ESC);
    		sb.append("(");
    		sb.append("B");
    		sb.append(Character.toChars(Math.floorMod((6+barcodeNumber.length()),256))); // Bytes of the bar code string
    		sb.append(Character.toChars((6+barcodeNumber.length())/256)); // Bytes of the bar code string

    		sb.append(Character.toChars(5)); // k barcode 5 - Code39
    		sb.append(Character.toChars(1)); // m barcode width (02 to 05)
    		sb.append(0); // s barcode space between (-3 to +3) [254 equivalent -2]
    		sb.append(Character.toChars(15)); // v1 barcode length 
    		sb.append(Character.toChars(0)); // v2 barcode length
    		sb.append(Character.toChars(3)); //control fag... 3 control character and human readable
    		sb.append(barcodeNumber);
    		
    		System.out.println(String.format("%040x", new BigInteger(1, sb.toString().getBytes())));
    		
    	return sb;
   }
    public static StringBuilder formFeed() {
    	StringBuilder sb = new StringBuilder();
        //post: ejects single sheet
    	sb.append(CR); //according to epson esc/p ref. manual it is recommended to send carriage return before form feed
    	sb.append(FF);
    	return sb;
    }
    
    
    
    public static StringBuilder select10CPI() { //10 characters per inch (condensed available)
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(P);
        return sb;
    }

    public static StringBuilder select12CPI() { //12 characters per inch (condensed available)
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(M);
        return sb;
    }
    
    public static StringBuilder select15CPI() { //15 characters per inch (condensend not available)
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(g);
        return sb;
    }
    
    public static StringBuilder selectDraftPrinting() { //set draft quality printing
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(x);
        sb.append((char) 48);
        return sb;
    }
    
    public static StringBuilder doubleWidth() { //10 characters per inch (condensed available)
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(W);
        sb.append(1);
        return sb;
    }

    public static StringBuilder doubleWidthOff() { //10 characters per inch (condensed available)
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(W);
        sb.append(0);
        return sb;
    }
    
    public static StringBuilder doubleHeight() {
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(w);
        sb.append('n');
        return sb;
    }
    
    public static StringBuilder lineFeed() {
    	StringBuilder sb = new StringBuilder();
        //post: performs new line
        sb.append(CR); //according to epson esc/p ref. manual always send carriage return before line feed
        sb.append(LINE_FEED);
        return sb;
    }
    

    
    public static void proportionalMode(boolean proportional) {
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(p);
        if (proportional)
            sb.append((char) 49);
        else
            sb.append((char) 48);
    }
    

    
    public static String advanceHorizontal(float centimeters) {
    	StringBuilder sb = new StringBuilder();
        //pre: centimeters >= 0
        //post: advances horizontal print position approx. centimeters
        float inches = centimeters / CM_PER_INCH;
        int units_low = (int) (inches * 120) % 256;
        int units_high = (int) (inches * 120) / 256;
        
        sb.append(ESC);       
        sb.append(BACKSLASH);
        sb.append((char) units_low);
        sb.append((char) units_high);
        
        return sb.toString();
    }
    

    
    public static String horizontalTab(int tabs) {
    	StringBuilder sb = new StringBuilder();
        //pre: tabs >= 0
        //post: performs horizontal tabs tabs number of times
        for (int i = 0; i < tabs; i++)
            sb.append(TAB);
        return sb.toString();
    }
    
    public static void setMargins(int columnsLeft, int columnsRight) {
        //pre: columnsLeft > 0 && <= 255, columnsRight > 0 && <= 255
        //post: sets left margin to columnsLeft columns and right margin to columnsRight columns
        //left
    	StringBuilder sb = new StringBuilder();
        sb.append(ESC);
        sb.append(l);
        sb.append((char) columnsLeft);
        
        //right
        sb.append(ESC);
        sb.append(Q);
        sb.append((char) columnsRight);
    }


	public static Object space() {
		return ' ';
	}
    
	public static String space(int s) {
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++)
            sb.append(" ");
        return sb.toString();
	}
    
    public static StringBuilder underLineOn() {
    	StringBuilder sb = new StringBuilder();
	    	sb.append(ESC);
	    	sb.append(HYPHEN);
	    	sb.append(FOURTYNINE);
        return sb;
    }
    
    public static StringBuilder underLineOff() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append(HYPHEN);
    	sb.append(FOURTYEIGHT);
    return sb;
    }
    
    public static StringBuilder draftQuality() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append(0);
    return sb;
    }
    
    public static StringBuilder letterQuality() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append(1);
    return sb;
    }
    
    public static StringBuilder superSubscript(int i) {
    	//0 - subscript
    	//1 - superscript
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('S');
    	sb.append(i);
    	return sb;
    }
        
    public static StringBuilder cancelSuperSubscript() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('T');
    	return sb;
    }
    
    public static StringBuilder fontRoman() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('k');
    	sb.append('0');//Roman
    	return sb;
    }
    
    
    
    public static StringBuilder fontSansserif() {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('k');
    	sb.append('1');//Sans
    	return sb;
    }
    
    
    public static StringBuilder setPageSizeLines(int lines) {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('C');
    	sb.append((char)lines);//Sans
    	return sb;
    }
    
    public static StringBuilder setPageSizeInches(int lines) {
   	StringBuilder sb = new StringBuilder();
    	sb.append(ESC);
    	sb.append('C');
    	sb.append(NUL);
    	sb.append((char)lines);//Sans
    	return sb;
    }
    
        
}

