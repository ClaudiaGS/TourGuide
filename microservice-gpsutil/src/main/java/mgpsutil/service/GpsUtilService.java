package mgpsutil.service;

import com.jsoniter.output.JsonStream;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GpsUtilService {
    
    private static final Logger logger = LogManager.getLogger("GpsUtilService");
    
    @Autowired
    GpsUtil gpsUtil;
    
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
    
    /**
     * Get the location of a user
     *
     * @param userId
     * @return VisitedLocation
     */
    public VisitedLocation getLocation(UUID userId) {
        
        logger.info("Getting location for user with id " + userId);
        
        return gpsUtil.getUserLocation(userId);
    }
    
    /**
     * Get 5 attractions near user position
     *
     * @param userId
     * @return List<Attraction>
     */
    public List<Attraction> getFiveNearByAttractions(UUID userId) {
        
        logger.info("Getting 5 nearest attractions for user with id: " + userId);
        
        List<Attraction> fiveNearByAttractions = new ArrayList<>();
        Map<Double, Attraction> attractionsDistances = new HashMap<>();
        int countAttraction = 0;
        List<Double> distances = new ArrayList<>();
        for (Attraction attraction : gpsUtil.getAttractions()) {
            Double distance = getDistance(attraction, getLocation(userId).location);
            attractionsDistances.put(distance, attraction);
            distances.add(distance);
        }
        distances.sort(Comparator.naturalOrder());
        
        for (Double distance : distances) {
            if (countAttraction <= 4) {
                fiveNearByAttractions.add(attractionsDistances.get(distance));
                countAttraction += 1;
            }
        }
        return fiveNearByAttractions;
    }
    
    /**
     * Get a list of every user's most recent location
     *
     * @param userIdList
     * @return HashMap<String, HashMap < String, Double>>
     */
    public HashMap<String, HashMap<String, Double>> getAllCurrentLocations(List<UUID> userIdList) {
        
        logger.info("Getting current locations for all users");
        
        HashMap<String, HashMap<String, Double>> currentLocations = new HashMap<>();
        for (UUID id : userIdList) {
            HashMap<String, Double> location = new HashMap<>();
            location.put("latitude", getLocation(id).location.latitude);
            location.put("longitude", getLocation(id).location.longitude);
            currentLocations.put(String.valueOf(id), location);
        }
        return currentLocations;
    }
    
    /**
     * Get all the attractions
     *
     * @return List<Attraction>
     */
    public List<Attraction> getAttractions() {
        
        logger.info("Getting attraction list");
        
        return gpsUtil.getAttractions();
    }
    
   
    public Double getDistance(gpsUtil.location.Location loc1, gpsUtil.location.Location loc2) {
        
        logger.info("Getting distance between " + JsonStream.serialize(loc1) + " and " + JsonStream.serialize(loc2));
        
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
}