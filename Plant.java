package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Plant {

    private String code;
    private String description;
    private List<PlantMapping> plantMappings;
    private TransitNodeMapping transitNode;

	@JsonCreator
    public Plant(@JsonProperty("plant") String code, @JsonProperty("description") String description, @JsonProperty("plantMapping") List<PlantMapping> plantMappings,@JsonProperty("transitNode")TransitNodeMapping transitNode) {
        this.code = code;
        this.description = description;
        this.plantMappings = plantMappings;
        this.transitNode =transitNode;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<PlantMapping> getPlantMappings() {
        return plantMappings;
    }
    
    public TransitNodeMapping getTransitNode() {
		return transitNode;
	}

	public void setTransitNode(TransitNodeMapping transitNode) {
		this.transitNode = transitNode;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plant plant = (Plant) o;

        return code.equals(plant.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
