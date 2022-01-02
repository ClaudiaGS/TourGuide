package mgpsutil.controller;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import mgpsutil.UUIDException;
import mgpsutil.service.GpsUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class GpsUtilController {
    
    @Autowired
    GpsUtilService gpsUtilService;
    
    /**
     * Get the location of a user endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @param userId
     * @return VisitedLocation
     */
    @GetMapping("/getLocationGpsUtil")
    public VisitedLocation getLocation(@RequestParam String userId) throws UUIDException {
        UUID userIdUuid = null;
        try {
            userIdUuid = UUID.fromString(userId);
            VisitedLocation visitedLocation = gpsUtilService.getLocation(userIdUuid);
        } catch (IllegalArgumentException e) {
            throw new UUIDException(userId);
        }
        return gpsUtilService.getLocation(userIdUuid);
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
        return gpsUtilService.getFiveNearByAttractions(userIdUuid);
    }
    
    /**
     * Get the list of every user's most recent location in an endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @return HashMap<String, HashMap < String, Double>>
     */
    @GetMapping("/getAllCurrentLocationsGpsUtil")
    public HashMap<String, HashMap<String, Double>> getAllCurrentLocations(@RequestParam List<UUID> userIdList) throws UUIDException {
        return gpsUtilService.getAllCurrentLocations(userIdList);
    }
    
    /**
     * Get all the attractions in an endpoint from the microservice-gpsutil, to supply to the tourguidemodule
     *
     * @return HashMap<String, HashMap < String, Double>>
     */
    @GetMapping("/getAttractionsGpsUtil")
    public List<Attraction> getAttractions() {
        return gpsUtilService.getAttractions();
    }
    
    
}