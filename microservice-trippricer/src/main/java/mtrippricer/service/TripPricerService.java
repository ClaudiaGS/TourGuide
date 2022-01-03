package mtrippricer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripPricerService {
    
    private static final Logger logger = LogManager.getLogger("TripPricerService");
    
    @Autowired
    TripPricer tripPricer;
    
    /**
     * Get the trip deals
     *
     * @param data
     * @return List<Provider>
     */
    public List<Provider> getTripDeals(List<String> data) {
        
        logger.info("Getting trip deals for user with id " + data.get(1));
        
        UUID userIdUuid = null;
        String userId = data.get(1);
        userIdUuid = UUID.fromString(userId);
        List<Provider> providers = new ArrayList<>();
        providers = tripPricer.getPrice(data.get(0), userIdUuid, Integer.valueOf(data.get(2)), Integer.valueOf(data.get(3)), Integer.valueOf(data.get(4)), Integer.valueOf(data.get(5)));
        
        return providers;
    }
    
}
