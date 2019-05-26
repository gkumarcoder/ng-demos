package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlantTransitNode {

	private PlantNode plantNode;

	private ModelNode modelNode;

	private CountryNode countryNode;

	private TransitNodeMapping transitNode;

	@JsonCreator
	public PlantTransitNode(@JsonProperty("plant") PlantNode plantNode, @JsonProperty("model") ModelNode modelNode,
			@JsonProperty("country") CountryNode countryNode,
			@JsonProperty("firstTransitNode") TransitNodeMapping transitNode) {
		this.plantNode = plantNode;
		this.modelNode = modelNode;
		this.countryNode = countryNode;
		this.transitNode = transitNode;
	}

	public PlantNode getPlantNode() {
		return plantNode;
	}

	public void setPlantNode(PlantNode plantNode) {
		this.plantNode = plantNode;
	}

	public ModelNode getModelNode() {
		return modelNode;
	}

	public void setModelNode(ModelNode modelNode) {
		this.modelNode = modelNode;
	}

	public CountryNode getCountryNode() {
		return countryNode;
	}

	public void setCountryNode(CountryNode countryNode) {
		this.countryNode = countryNode;
	}

	public TransitNodeMapping getTransitNode() {
		return transitNode;
	}

	public void setTransitNode(TransitNodeMapping transitNode) {
		this.transitNode = transitNode;
	}

}
