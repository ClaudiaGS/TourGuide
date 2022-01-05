package com.example.tourguidemodule.beans;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class TripPricerTaskBean implements Callable<List<ProviderBean>> {
    private final UUID attractionId;
    private final String apiKey;
    private final int adults;
    private final int children;
    private final int nightsStay;
    
    public TripPricerTaskBean(String apiKey, UUID attractionId, int adults, int children, int nightsStay) {
        this.apiKey = apiKey;
        this.attractionId = attractionId;
        this.adults = adults;
        this.children = children;
        this.nightsStay = nightsStay;
    }
    
    public List<ProviderBean> call() throws Exception {
        return (new TripPricerBean()).getPrice(this.apiKey, this.attractionId, this.adults, this.children, this.nightsStay, 5);
    }
}

