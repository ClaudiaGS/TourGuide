package com.example.tourguidemodule.service;


import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.tracker.Tracker;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserPreferences;
import com.example.tourguidemodule.user.UserReward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TourGuideService {
    
    private static final Logger logger = LogManager.getLogger("TourGuideService");
    
    private GpsUtilBean gpsUtilBean;
    private RewardsService rewardsService;
    private HelperService helperService;
    
    public final Tracker tracker;
    boolean testMode = true;
    /**
     *
     * TourGuideService constructor
     *
     * @param gpsUtilBean
     * @param helperService
     * @param rewardsService
     *
     */
    public TourGuideService(GpsUtilBean gpsUtilBean,HelperService helperService, RewardsService rewardsService) {
        this.gpsUtilBean = gpsUtilBean;
        this.helperService=helperService;
        this.rewardsService = rewardsService;
        
        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            helperService.initializeInternalUsers();
            
            logger.debug("Finished initializing users");
        }
        tracker = new Tracker(this);
        addShutDownHook();
    }
    
    /**
     * Get user data for given user name
     *
     * @param userName
     *
     * @return User
     */
    public User getUser(String userName) {
        return helperService.internalUserMap.get(userName);
    }
    
    /**
     * Get a list of all users
     *
     * @return List<User>
     */
    public List<User> getAllUsers() {
        return helperService.internalUserMap.values().stream().collect(Collectors.toList());
    }
    
    /**
     * Add an user
     *
     * @param user
     * @return User
     */
    public User addUser(User user) {
        boolean done = false;
        if (!helperService.internalUserMap.containsKey(user.getUserName())) {
            helperService.internalUserMap.put(user.getUserName(), user);
            done = true;
        }
        if (done) {
            return user;
        } else return null;
    }
    
    /**
     * Track location of an user
     *
     * @param user
     *
     * @return VisitedLocationBean
     */
    public VisitedLocationBean trackUserLocation(User user) {
        VisitedLocationBean visitedLocationBean = gpsUtilBean.getUserLocation(user.getUserId());
        user.addToVisitedLocations(visitedLocationBean);
        rewardsService.calculateRewards(user);
        return visitedLocationBean;
    }
    
    /**
     * Get reward list for user
     *
     * @param user
     * @return List<UserReward>
     */
    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }
    
    /**
     * Update user preferences
     *
     * @param userName
     * @param newPreferences
     * @return UserPreferences
     */
    public UserPreferences modifyUserPreferences(String  userName,HashMap<String,String>newPreferences) {
        
        User user=helperService.internalUserMap.get(userName);
        logger.info("user is "+user);
        UserPreferences userPreferences=helperService.internalUserMap.get(userName).getUserPreferences();
        for (Map.Entry<String, String> entry : newPreferences.entrySet()) {
            switch (entry.getKey()) {
                case "tripDuration":
                    userPreferences.setTripDuration(Integer.parseInt(entry.getValue()));
                    break;
                case "numberOfAdults":
                    userPreferences.setNumberOfAdults(Integer.parseInt(entry.getValue()));
                    break;
                case "numberOfChildren":
                    userPreferences.setNumberOfChildren(Integer.parseInt(entry.getValue()));
                    break;
                case "ticketQuantity":
                    userPreferences.setTicketQuantity(Integer.parseInt(entry.getValue()));
                    break;
                default:
                    System.out.println("Trying to modify unexisting parameter");
                    break;
            }
        }
            user.setUserPreferences(userPreferences);
        return userPreferences;
    }
    
    /**
     * Stop tracking user location
     *
     * @return void
     */
    private void addShutDownHook() {
        logger.info("Stop tracking location");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }
    
}


