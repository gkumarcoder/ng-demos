package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDestinationCode {
	private String vin;
	private String isoCountry;
	private String deliveryCode;
	private String stockType;
	private String companyDeliveryZone;
	
	public CountryDestinationCode() {}
	
	public CountryDestinationCode(String vin, String isoCountry, String deliveryCode, String stockType,String companyDeliveryZone) {
		this.vin = vin;
		this.isoCountry = isoCountry;
		this.deliveryCode = deliveryCode;
		this.stockType = stockType;
		this.companyDeliveryZone=companyDeliveryZone;
	}

	/**
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return the isoCountry
	 */
	public String getIsoCountry() {
		return isoCountry;
	}

	/**
	 * @param isoCountry the isoCountry to set
	 */
	public void setIsoCountry(String isoCountry) {
		this.isoCountry = isoCountry;
	}

	/**
	 * @return the deliveryCode
	 */
	public String getDeliveryCode() {
		return deliveryCode;
	}

	/**
	 * @param deliveryCode the deliveryCode to set
	 */
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	/**
	 * @return the stockType
	 */
	public String getStockType() {
		return stockType;
	}

	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	/**
	 * @return the companyDeliveryZone
	 */
	public String getCompanyDeliveryZone() {
		return companyDeliveryZone;
	}

	/**
	 * @param companyDeliveryZone the companyDeliveryZone to set
	 */
	public void setCompanyDeliveryZone(String companyDeliveryZone) {
		this.companyDeliveryZone = companyDeliveryZone;
	}	
	
}
