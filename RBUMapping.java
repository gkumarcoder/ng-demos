package com.alliance.louisa.louisa2.louisa1;

import java.util.List;

import com.alliance.louisa.louisa2.domain.Country;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RBUMapping {

	private String rbu;
	private String shortName;
	private String name;
	private List<RbuCountry>rbuCountry;
	
	/**
	 * @return the rbuCountry
	 */
	public List<RbuCountry> getRbuCountry() {
		return rbuCountry;
	}
	/**
	 * @param rbuCountry the rbuCountry to set
	 */
	public void setRbuCountry(List<RbuCountry> rbuCountry) {
		this.rbuCountry = rbuCountry;
	}
	/**
	 * @return the rbu
	 */
	public String getRbu() {
		return rbu;
	}
	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param rbu the rbu to set
	 */
	public void setRbu(String rbu) {
		this.rbu = rbu;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
