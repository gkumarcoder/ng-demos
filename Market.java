package com.alliance.louisa.louisa2.louisa1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {

    private String name;
    private String market;

    public Market(){
    	
    }
    public Market(String name,String market){
    	this.name=name;
    	this.market=market;
    }
    public String getName() {
        return name;
    }

    public String getMarket() {
        return market;
    }
}
