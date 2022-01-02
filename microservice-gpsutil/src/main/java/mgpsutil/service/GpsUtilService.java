package mgpsutil.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GpsUtilService {
    private static final Logger logger = LogManager.getLogger("GpsUtilService");
    private final GpsUtil gpsUtil = new GpsUtil();
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
    
    /**
     * Get the location of a user
     *
     * @param userId
     * @return VisitedLocation
     */
    public VisitedLocation getLocation(UUID userId) {
        logger.info("getLocation in mgpsutil for user with id " + userId);
        return gpsUtil.getUserLocation(userId);
    }
    
    /**
     * Get 5 attractions near user position
     *
     * @param userId
     * @return List<Attraction>
     */
    public List<Attraction> getFiveNearByAttractions(UUID userId) {
    
        List<Attraction> fiveNearByAttractions = new ArrayList<>();
        Map<Double, Attraction> attractionsDistances = new HashMap<>();
        int countAttraction = 0;
     //   VisitedLocation visitedLocation = trackUserLocation(user);
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
    
//        Map<Double, Attraction> attractionsDistances = new HashMap<>();
//        for (Attraction attractionBean : gpsUtil.getAttractions()) {
//            Double distance = getDistance(attractionBean, (getLocation(userId).location));
//            attractionsDistances.put(distance, attractionBean);
//        }
//
//        List<gpsUtil.location.Attraction> fiveNearByAttractions = new ArrayList<>();
//        int countAttraction = 0;
//
//        List<Double> distances = new ArrayList<>();
//        for (HashMap.Entry<Double, gpsUtil.location.Attraction> entry : attractionsDistances.entrySet()) {
//            Double distance = entry.getKey();
//            distances.add(distance);
//        }
//        distances.sort(Comparator.naturalOrder());
//        for (Double distance : distances) {
//            if (countAttraction <= 4) {
//                fiveNearByAttractions.add(attractionsDistances.get(distance));
//                countAttraction += 1;
//            }
//        }
//
//        return fiveNearByAttractions;
//   }
    
    
    
    
    /**
     * Get a list of every user's most recent location
     *
     * @return HashMap<String, HashMap < String, Double>>
     */
    public HashMap<String, HashMap<String, Double>> getAllCurrentLocations(List<UUID> userIdList) {
        HashMap<String, HashMap<String, Double>> currentLocations = new HashMap<>();
      //  HashMap<String, Double> location = new HashMap<>();
        for (UUID id : userIdList) {
            HashMap<String, Double> location = new HashMap<>();
            location.put("latitude", getLocation(id).location.latitude);
            location.put("longitude", getLocation(id).location.longitude);
            currentLocations.put(String.valueOf(id), location);
        }
        return currentLocations;
    }
    
    public List<gpsUtil.location.Attraction> getAttractions() {
        return gpsUtil.getAttractions();
    }
    
    /**
     * Get distance between 2 locations
     *
     * @param loc1
     * @param loc2
     * @return Double
     */
    public Double getDistance(gpsUtil.location.Location loc1, gpsUtil.location.Location loc2) {
        
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

//    /**
//     *
//     * Check if location is within attraction proximity
//     * @param attraction
//     * @param location
//     * @return boolean
//     *
//     */
//    public boolean isWithinAttractionProximity(gpsUtil.location.Attraction attraction, gpsUtil.location.Location location) {
//        return getDistance(attraction, location) > attractionProximityRange ? false : true;
//    }
    
    /**
     * Get near attractions to visited location
     *
     * @param visitedLocation
     * @return List<Attraction>
     */
    public List<gpsUtil.location.Attraction> getNearByAttractions(VisitedLocation visitedLocation, boolean isWithinAttractionProximity) {
        List<gpsUtil.location.Attraction> nearbyAttractions = new ArrayList<>();
        for (gpsUtil.location.Attraction attraction : gpsUtil.getAttractions()) {
            if (isWithinAttractionProximity) {
                nearbyAttractions.add(attraction);
            }
        }
        
        return nearbyAttractions;
    }
    
}