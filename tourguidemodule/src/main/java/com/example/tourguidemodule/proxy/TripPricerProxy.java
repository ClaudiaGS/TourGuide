package com.example.tourguidemodule.proxy;

import com.example.tourguidemodule.beans.ProviderBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * Declare the microservice endpoint
 *
 */
@FeignClient(name = "microservice-trippricer", url = "localhost:9003")
public interface TripPricerProxy {
    
    @GetMapping("/getTripDealsTripPricer")
    List<ProviderBean> getTripDealsTripPricer(@RequestParam List<String> data);
    
}
