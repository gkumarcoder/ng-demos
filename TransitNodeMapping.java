package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransitNodeMapping {

	private String id;

	private String transitNode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransitNode() {
		return transitNode;
	}

	public void setTransitNode(String transitNode) {
		this.transitNode = transitNode;
	}

}
