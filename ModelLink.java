package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelLink {

    private String hostModelCode;
    
    public ModelLink() {
    }
    public ModelLink(String hostModelCode) {
    	this.hostModelCode=hostModelCode;
    }

    public String getHostModelCode() {
        return hostModelCode;
    }
}
