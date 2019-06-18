package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryNode {

	private String id;

	private String isoCountry;
	
	private String message;
	
	public String getId() {
		return id;
	}
	
	public String getMeassgee(){
	return message;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsoCountry() {
		return isoCountry;
	}

	public void setIsoCountry(String isoCountry) {
		this.isoCountry = isoCountry;
	}

}
