package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantModel {
    private List<PlantModels> plantModels;

    @JsonCreator
    public PlantModel(@JsonProperty("plantModels") List<PlantModels> plantModels) {
        this.plantModels = plantModels;
    }

    public List<PlantModels> getPlantModels() {
        return plantModels;
    }
}
