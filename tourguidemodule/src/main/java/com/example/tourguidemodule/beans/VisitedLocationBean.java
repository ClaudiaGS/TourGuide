package com.example.tourguidemodule.beans;



import java.util.Date;
import java.util.UUID;

public class VisitedLocationBean {
    public final UUID userId;
    public final LocationBean locationBean;
    public final Date timeVisited;
    
    public VisitedLocationBean(UUID userId, LocationBean locationBean, Date timeVisited) {
        this.userId = userId;
        this.locationBean = locationBean;
        this.timeVisited = timeVisited;
    }

    

}

