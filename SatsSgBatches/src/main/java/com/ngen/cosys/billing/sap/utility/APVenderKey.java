package com.ngen.cosys.billing.sap.utility;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class APVenderKey {

	
	public int getAPVendorKey(String apVendorCode,String payeeName,String accountNo,BigDecimal sgdAmount) {
		 int vdrTotal = 0;
		 int pnmTotal = 0;
		 int accTotal = 0;
		 int total;
		 int apVendorKey;
		
		if(StringUtils.isEmpty(apVendorCode)) {
			return -1;	
		}
		//Processing of Vendor Code
		if(!StringUtils.isEmpty(apVendorCode)) {
			vdrTotal = apVendorkeyDrSum (apVendorCode);
	     }
	     //Processing of Payee Name
	    if(!StringUtils.isEmpty(payeeName)) {
	    	pnmTotal = apVendorKeyothSum(payeeName);
	     }	 
	     //Processing of Account Number
	    if(!StringUtils.isEmpty(accountNo)) {
	    	accTotal = apVendorKeyothSum(accountNo);
	     }
	     
	     total = vdrTotal + pnmTotal + accTotal;
	     apVendorKey = Math.abs(sgdAmount.intValue()) - apVendorKeyHash (total);
		
	     return apVendorKey;
	}
	
	private int apVendorKeyHash(int total) {
		//Hash value code is pending due to table is not there
		int retval;
		int modeValue=total % 53;
		retval=900+modeValue;
		return retval;
	}


	private int apVendorKeyothSum(String payeeName) {
	     payeeName=payeeName.replace("0","");
		 payeeName=payeeName.replace("-", "");
		 payeeName=payeeName.replace(" ", "");
		
		//String mpynv=payeeName.replace(payeeName.replace(payeeName.replace("0",""),"-")," ");
		int length = payeeName.length();
		int keyValue;
		int total = 0;
		
		for(int index=0;index<length;index++) {
			keyValue = getKeyDecodeValue(payeeName.substring(index, index+1));
			total = keyValue + total;
		}
		
		return total;
	}


	private int apVendorkeyDrSum(String vendorCode) {
		 String vdrcd =vendorCode.replace("0","");
		 int vdLenght =vdrcd.length();
		 int revctr = vdLenght - 1;
		 
		 int keyValue;
		 int productValue;
		 int vdrTotal = 0;
		 
		 for(int index=0;index<vdLenght;index++) {
			 keyValue = getKeyDecodeValue(vdrcd.substring(index, index+1));
			 productValue = (int) (keyValue *  Math.pow(3, revctr));
			 revctr = revctr - 1;
			 vdrTotal = vdrTotal+productValue;
		 }
		return vdrTotal;
	}
	
	
	private int getKeyDecodeValue(String s1) {
		int t = 0;
		
		if(s1.equalsIgnoreCase("A")) {
			t=1;
		}else if(s1.equalsIgnoreCase("B")) {
			t=2;
		}if(s1.equalsIgnoreCase("C")) {
			t=3;
		}else if(s1.equalsIgnoreCase("D")) {
			t=4;
		}if(s1.equalsIgnoreCase("E")) {
			t=5;
		}else if(s1.equalsIgnoreCase("F")) {
			t=6;
		}if(s1.equalsIgnoreCase("G")) {
			t=7;
		}else if(s1.equalsIgnoreCase("H")) {
			t=8;
		}if(s1.equalsIgnoreCase("I")) {
			t=9;
		}else if(s1.equalsIgnoreCase("J")) {
			t=10;
		}if(s1.equalsIgnoreCase("K")) {
			t=11;
		}else if(s1.equalsIgnoreCase("L")) {
			t=12;
		}if(s1.equalsIgnoreCase("M")) {
			t=13;
		}else if(s1.equalsIgnoreCase("N")) {
			t=14;
		}if(s1.equalsIgnoreCase("O")) {
			t=15;
		}else if(s1.equalsIgnoreCase("P")) {
			t=16;
		}if(s1.equalsIgnoreCase("Q")) {
			t=17;
		}else if(s1.equalsIgnoreCase("R")) {
			t=18;
		}if(s1.equalsIgnoreCase("S")) {
			t=19;
		}else if(s1.equalsIgnoreCase("T")) {
			t=20;
		}if(s1.equalsIgnoreCase("U")) {
			t=21;
		}else if(s1.equalsIgnoreCase("V")) {
			t=22;
		}if(s1.equalsIgnoreCase("W")) {
			t=23;
		}else if(s1.equalsIgnoreCase("X")) {
			t=24;
		}else if(s1.equalsIgnoreCase("Y")) {
			t=25;
		}else if(s1.equalsIgnoreCase("Z")) {
			t=26;
		}if(s1.equalsIgnoreCase("1")) {
			t=1;
		}else if(s1.equalsIgnoreCase("2")) {
			t=2;
		}if(s1.equalsIgnoreCase("3")) {
			t=3;
		}else if(s1.equalsIgnoreCase("4")) {
			t=4;
		}else if(s1.equalsIgnoreCase("5")) {
			t=5;
		}if(s1.equalsIgnoreCase("6")) {
			t=6;
		}else if(s1.equalsIgnoreCase("7")) {
			t=7;
		}if(s1.equalsIgnoreCase("8")) {
			t=8;
		}else if(s1.equalsIgnoreCase("9")) {
			t=9;
		}
		
		return t;
		
	}
	
}
