package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantModels {
    private Model model;
    private Plant plant;

    @JsonCreator
    public PlantModels(@JsonProperty("model") Model model, @JsonProperty("plant") Plant plant) {
        this.model = model;
        this.plant = plant;
    }

    public Model getModel() {
        return model;
    }

    public Plant getPlant() {
        return plant;
    }
}
