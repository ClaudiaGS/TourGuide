package mtrippricer.controller;

import mtrippricer.UUIDException;
import mtrippricer.service.TripPricerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;

@RestController
public class TripPricerController {
    @Autowired
    TripPricerService tripPricerService;
    
    
    /**
     * Get the tripdeals endpoint from the microservice-trippricer, to supply to the tourguidemodule
     *
     * @param data
     * @return VisitedLocation
     */
    @GetMapping("/getTripDealsTripPricer")
    public List<Provider> getTripDealsTripPricer(@RequestParam List<String> data) throws UUIDException  {
    
        try {
            return tripPricerService.getTripDeals(data);
        } catch (IllegalArgumentException e) {
            throw new UUIDException(data.get(1));
        }
    
    }
    
}
