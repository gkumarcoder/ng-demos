package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PlantMapping {

    private String hostSystemPlant;

    @JsonCreator
    public PlantMapping(@JsonProperty("hostSystemPlant") String hostSystemPlant) {
        this.hostSystemPlant = hostSystemPlant;
    }

    public String getHostSystemPlant() {
        return hostSystemPlant;
    }
}
