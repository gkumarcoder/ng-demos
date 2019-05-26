package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model {

    private String model;
    private String description;
    private List<ModelLink> modelLink;

    public Model(String model, String description, List<ModelLink> modelLink) {
        this.model = model;
        this.description = description;
        this.modelLink = modelLink;
    }

    public Model() {
    }

    public String getModel() {
        return model;
    }
    public String getDescription() {
        return description;
    }

    public List<ModelLink> getModelLink() {
        return modelLink;
    }
}
