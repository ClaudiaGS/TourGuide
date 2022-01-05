package mgpsutil.controller;

import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import mgpsutil.exception.UUIDException;
import mgpsutil.service.GpsUtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class GpsUtilController {
    
    private static final Logger logger = LogManager.getLogger("GpsUtilController");
    
    @Autowired
    GpsUtilService gpsUtilService;
    
    /**
     * Get the location of a user endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @param userId
     * @return VisitedLocation
     */
    @GetMapping("/getLocationGpsUtil")
    public VisitedLocation getLocationGpsUtil(@RequestParam String userId) throws UUIDException {
        UUID userIdUuid = null;
        VisitedLocation visitedLocation=null;
        try {
            userIdUuid = UUID.fromString(userId);
            visitedLocation = gpsUtilService.getLocationGpsUtil(userIdUuid);
        } catch (IllegalArgumentException e) {
            throw new UUIDException(userId);
        }
        
        logger.info("Location for user with id "+userId+" is "+ JsonStream.serialize(visitedLocation));
        
        return visitedLocation;
    }
    
    /**
     * Get 5 nearest attractions endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @param userId
     * @return List<Attraction>
     */
    @GetMapping("/getFiveNearByAttractionsGpsUtil")
    public List<Attraction> getFiveNearByAttractions(@RequestParam String userId) throws UUIDException {
        UUID userIdUuid = null;
        try {
            userIdUuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new UUIDException(userId);
        }
        List<Attraction>fiveNearestAttractions=gpsUtilService.getFiveNearByAttractions(userIdUuid);
        
        logger.info("Five nearest attractions are "+JsonStream.serialize(fiveNearestAttractions));
        
        return fiveNearestAttractions;
    }
    
    /**
     * Get the list of every user's most recent location in an endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @param userIdList
     * @return HashMap<String, HashMap < String, Double>>
     */
    @GetMapping("/getAllCurrentLocationsGpsUtil")
    public HashMap<String, HashMap<String, Double>> getAllCurrentLocations(@RequestParam List<UUID> userIdList) throws UUIDException {
        HashMap<String,HashMap<String,Double>> locationsMap=gpsUtilService.getAllCurrentLocations(userIdList);
        
        logger.info("Location list of all users is: "+JsonStream.serialize(locationsMap));
        
        return locationsMap;
    }
    
    /**
     * Get all the attractions in an endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @return HashMap<String, HashMap < String, Double>>
     */
    @GetMapping("/getAttractionsGpsUtil")
    public List<Attraction> getAttractions() {
        List<Attraction>attractions=gpsUtilService.getAttractions();
        
        logger.info("Attraction list is :"+attractions);
        
        return attractions;
    }
    
    
}