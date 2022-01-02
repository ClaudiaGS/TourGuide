package com.example.tourguidemodule.proxy;

import com.example.tourguidemodule.beans.AttractionBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Declare the microservice endpoint
 */
@FeignClient(name = "microservice-gpsutil", url = "localhost:9001")
public interface GpsUtilProxy {
    
    @GetMapping(value = "/getLocationGpsUtil")
    VisitedLocationBean getLocation(@RequestParam("userId") String userId);
 
    @GetMapping(value = "/getFiveNearByAttractionsGpsUtil")
    List<AttractionBean> getFiveNearByAttractions(@RequestParam("userId") String userId);
    
    @GetMapping(value = "/getAllCurrentLocationsGpsUtil")
    HashMap<String, HashMap<String, Double>> getAllCurrentLocations(@RequestParam("userIdList") List<UUID> userIdList);
    
    
    @GetMapping("/getAttractionsGpsUtil")
    List<AttractionBean> getAttractions();
}


