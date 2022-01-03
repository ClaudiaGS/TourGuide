package mtrippricer.controller;

import com.jsoniter.output.JsonStream;
import mtrippricer.exception.UUIDException;
import mtrippricer.service.TripPricerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;

@RestController
public class TripPricerController {
    
    private static final Logger logger = LogManager.getLogger("TripPricerController");
    
    @Autowired
    TripPricerService tripPricerService;
    
    /**
     * Get the tripdeals endpoint from the microservice-trippricer, to supply to the tourguidemodule
     *
     * @param data
     * @return List<Provider>
     */
    @GetMapping("/getTripDealsTripPricer")
    public List<Provider> getTripDealsTripPricer(@RequestParam List<String> data) throws UUIDException  {
        List<Provider>providers=null;
        try {
            providers=tripPricerService.getTripDeals(data);
        } catch (IllegalArgumentException e) {
            throw new UUIDException(data.get(1));
        }
        
        logger.info("Trip deals for user with id "+data.get(1)+" are "+ JsonStream.serialize(providers));
        
        return providers;
    }
    
}
