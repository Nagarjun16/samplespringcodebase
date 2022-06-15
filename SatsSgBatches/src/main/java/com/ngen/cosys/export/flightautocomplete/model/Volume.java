package com.ngen.cosys.export.flightautocomplete.model;

import java.math.BigDecimal;

import com.ngen.cosys.framework.model.BaseBO;



public class Volume extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;
	private String awbColumnIdentifier;
	private String goodsDataIdentifier;
	private String volumeCode;
	private BigDecimal volumeAmount;

	/**
	 * @return the awbColumnIdentifier
	 */
	public String getAwbColumnIdentifier() {
		return awbColumnIdentifier;
	}

	/**
	 * @param awbColumnIdentifier
	 *            the awbColumnIdentifier to set
	 */
	public void setAwbColumnIdentifier(String awbColumnIdentifier) {
		this.awbColumnIdentifier = awbColumnIdentifier;
	}

	/**
	 * @return the goodsDataIdentifier
	 */
	public String getGoodsDataIdentifier() {
		return goodsDataIdentifier;
	}

	/**
	 * @param goodsDataIdentifier
	 *            the goodsDataIdentifier to set
	 */
	public void setGoodsDataIdentifier(String goodsDataIdentifier) {
		this.goodsDataIdentifier = goodsDataIdentifier;
	}

	/**
	 * @return the volumeCode
	 */
	public String getVolumeCode() {
		return volumeCode;
	}

	/**
	 * @param volumeCode
	 *            the volumeCode to set
	 */
	public void setVolumeCode(String volumeCode) {
		this.volumeCode = volumeCode;
	}

	/**
	 * @return the volumeAmount
	 */
	public BigDecimal getVolumeAmount() {
		return volumeAmount;
	}

	/**
	 * @param volumeAmount
	 *            the volumeAmount to set
	 */
	public void setVolumeAmount(BigDecimal volumeAmount) {
		this.volumeAmount = volumeAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Volume [awbColumnIdentifier=" + awbColumnIdentifier + ", goodsDataIdentifier=" + goodsDataIdentifier
				+ ", volumeCode=" + volumeCode + ", volumeAmount=" + volumeAmount + "]";
	}

}