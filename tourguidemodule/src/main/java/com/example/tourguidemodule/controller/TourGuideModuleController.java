package com.example.tourguidemodule.controller;

import com.example.tourguidemodule.beans.AttractionBean;
import com.example.tourguidemodule.beans.ProviderBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.proxy.GpsUtilProxy;
import com.example.tourguidemodule.proxy.RewardCentralProxy;
import com.example.tourguidemodule.proxy.TripPricerProxy;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsoniter.output.JsonStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class TourGuideModuleController {
    
    private static final Logger logger = LogManager.getLogger("TourGuideModuleController");
    
    @Autowired
    GpsUtilProxy gpsUtilProxy;
    @Autowired
    RewardCentralProxy rewardCentralProxy;
    @Autowired
    TripPricerProxy tripPricerProxy;
    @Autowired
    HelperService helperService;
    @Autowired
    TourGuideService tourGuideService;
    @Autowired
    RewardsService rewardsService;
    
    /**
     * Get the location of an user, calling microservice-gpsutil
     *
     * @param userName
     * @return String
     */
    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {
        logger.info("getting location for user " + userName);
        return JsonStream.serialize(gpsUtilProxy.getLocationGpsUtil(tourGuideService.getUser(userName).getUserId().toString()));
    }
    
    /**
     * Get five nearest attractions, calling microservice-gpsutil and microservice-rewardcentral
     *
     * @param userName
     * @return String
     */
    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) {
        User user = tourGuideService.getUser(userName);
        VisitedLocationBean visitedLocationBean = tourGuideService.trackUserLocation(user);
        List<AttractionBean> fiveNearestAttractions = gpsUtilProxy.getFiveNearByAttractions(user.getUserId().toString());
        
        String jsonString = "";
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            ArrayNode attractionNode = mapper.createArrayNode();
            for (AttractionBean attractionBean : fiveNearestAttractions) {
                ObjectNode attractionDataNode = mapper.createObjectNode();
                attractionDataNode.put("attractionName", attractionBean.attractionName);
                attractionDataNode.put("attractionLatitude", attractionBean.latitude);
                attractionDataNode.put("attractionLongitude", attractionBean.longitude);
                attractionDataNode.put("distanceUserAttraction", rewardsService.getDistance(visitedLocationBean.locationBean, attractionBean));
                attractionDataNode.put("rewardPoints", rewardCentralProxy.getRewardPoints(attractionBean.attractionId.toString(), user.getUserId().toString()));
                attractionNode.add(attractionDataNode);
            }
            
            ObjectNode usersDataNode = mapper.createObjectNode();
            usersDataNode.put("usersLatitude", visitedLocationBean.locationBean.latitude);
            usersDataNode.put("usersLongitude", visitedLocationBean.locationBean.longitude);
            
            ObjectNode completeNode = mapper.createObjectNode();
            completeNode.set("user", usersDataNode);
            completeNode.set("attractions", attractionNode);
            
            jsonString = mapper.writeValueAsString(completeNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonString;
    }
    
    /**
     * Get locations of all users, calling microservice-gpsutil
     *
     * @return String
     */
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        List<User> userList = tourGuideService.getAllUsers();
        List<UUID> userIdList = new ArrayList<>();
        for (User user : userList) {
            userIdList.add(user.getUserId());
        }
        return JsonStream.serialize(gpsUtilProxy.getAllCurrentLocations(userIdList));
    }
    
    /**
     * Get user rewards
     *
     * @param userName
     * @return String
     */
    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {
        User user = tourGuideService.getUser(userName);
        return JsonStream.serialize(tourGuideService.getUserRewards(user));
    }
    
    /**
     * Get trip deals for user, calling microservice-trippricer
     *
     * @param userName
     * @return String
     */
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
        User user = tourGuideService.getUser(userName);
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        List<String> data = new ArrayList<>();
        data.add(helperService.tripPricerApiKey);
        data.add(user.getUserId().toString());
        data.add(String.valueOf(user.getUserPreferences().getNumberOfAdults()));
        data.add(String.valueOf(user.getUserPreferences().getNumberOfChildren()));
        data.add(String.valueOf(user.getUserPreferences().getTripDuration()));
        data.add(String.valueOf(cumulativeRewardPoints));
        List<ProviderBean> providers = tripPricerProxy.getTripDealsTripPricer(data);
        return JsonStream.serialize(providers);
    }
}
