package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantFirstTransitNode {
	
	private String source;
	private String hostSystemPlant;
	private String louisaPlant;
	private String description;
	private String hostModelCode;
	private String countryCode;
	private String louisaCode;
	private String exceptionReference;
	private String hostFirstTransitNode;
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the hostSystemPlant
	 */
	public String getHostSystemPlant() {
		return hostSystemPlant;
	}
	/**
	 * @param hostSystemPlant the hostSystemPlant to set
	 */
	public void setHostSystemPlant(String hostSystemPlant) {
		this.hostSystemPlant = hostSystemPlant;
	}
	/**
	 * @return the louisaCode
	 */
	public String getLouisaCode() {
		return louisaCode;
	}
	/**
	 * @param louisaCode the louisaCode to set
	 */
	public void setLouisaCode(String louisaCode) {
		this.louisaCode = louisaCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the hostModelCode
	 */
	public String getHostModelCode() {
		return hostModelCode;
	}
	/**
	 * @param hostModelCode the hostModelCode to set
	 */
	public void setHostModelCode(String hostModelCode) {
		this.hostModelCode = hostModelCode;
	}
	
	/**
	 * @return the exceptionReference
	 */
	public String getExceptionReference() {
		return exceptionReference;
	}
	/**
	 * @param exceptionReference the exceptionReference to set
	 */
	public void setExceptionReference(String exceptionReference) {
		this.exceptionReference = exceptionReference;
	}
	/**
	 * @return the hostFirstTransitNode
	 */
	public String getHostFirstTransitNode() {
		return hostFirstTransitNode;
	}
	/**
	 * @param hostFirstTransitNode the hostFirstTransitNode to set
	 */
	public void setHostFirstTransitNode(String hostFirstTransitNode) {
		this.hostFirstTransitNode = hostFirstTransitNode;
	}
	/**
	 * @return the louisaPlant
	 */
	public String getLouisaPlant() {
		return louisaPlant;
	}
	/**
	 * @param louisaPlant the louisaPlant to set
	 */
	public void setLouisaPlant(String louisaPlant) {
		this.louisaPlant = louisaPlant;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	

}
