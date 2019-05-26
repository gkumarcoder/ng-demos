package com.alliance.louisa.louisa2.louisa1;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketCountryRatio {
	
	private String market;
	
	private String countryCode;
	
	private Map<Integer,BigDecimal> weekRatio;

	
	public MarketCountryRatio() {
	}

	public MarketCountryRatio(String market, String countryCode, Map<Integer, BigDecimal> weekRatio) {
		super();
		this.market = market;
		this.countryCode = countryCode;
		this.weekRatio = weekRatio;
	}
	
	public MarketCountryRatio(String market, String countryCode) {

		this.market = market;
		this.countryCode = countryCode;
	}
	
	/**
	 * @param market the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @param weekRatio the weekRatio to set
	 */
	public void setWeekRatio(Map<Integer, BigDecimal> weekRatio) {
		this.weekRatio = weekRatio;
	}

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @return the weekRatio
	 */
	public Map<Integer, BigDecimal> getWeekRatio() {
		return weekRatio;
	}
	
	

}
