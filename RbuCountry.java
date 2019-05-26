package com.alliance.louisa.louisa2.louisa1;

import com.alliance.louisa.louisa2.domain.Country;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RbuCountry {
	
	private Country countries;

	/**
	 * @return the countries
	 */
	public Country getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(Country countries) {
		this.countries = countries;
	}
	
	

}
