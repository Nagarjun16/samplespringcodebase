/**
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.shipment.nawb.enums;

/**
 * RateDescriptionOtherInfoForNawbType Enums
 */
public enum RateDescriptionOtherInfoForNawbType {

	/**
	* Commodity Properties & Desc
	*	NC - Consolidation
	*	ND - Dimension
	*	NG - Nature of Goods
	*	NH - Harmonised Code
	*	NO - Country Of Origin of Goods
	*	NS - Shippers Load and Count
	*	NU - ULD
	*	NV - Volume
	* **/


	NC(Type.NC),
	ND(Type.ND),
	NG(Type.NG),
	NH(Type.NH),
	NO(Type.NO),
	NS(Type.NS),
	NU(Type.NU),
	NV(Type.NV);

	public class Type{
		public static final String NC="NC";
		public static final String ND="ND";
		public static final String NG="NG";
		public static final String NH="NH";
		public static final String NO="NO";
		public static final String NS="NS";
		public static final String NU="NU";
		public static final String NV="NV";
		
		private Type() {
			//Do nothing
		}
	}
	
   private String rateDescriptionOtherInfoForNawbType;
   
	public String getRateDescriptionOtherInfoForNawbType() {
		return this.rateDescriptionOtherInfoForNawbType;
	}
	
	private RateDescriptionOtherInfoForNawbType(String rateDescriptionOtherInfoForNawbType) {
		this.rateDescriptionOtherInfoForNawbType = rateDescriptionOtherInfoForNawbType;
	}
}
