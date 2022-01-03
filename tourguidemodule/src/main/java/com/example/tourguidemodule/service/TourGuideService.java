package com.example.tourguidemodule.service;


import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.tracker.Tracker;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserReward;
import com.jsoniter.output.JsonStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
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
        logger.info("Getting user with name "+userName);
        return helperService.internalUserMap.get(userName);
    }
    
    /**
     * Get a list of all users
     *
     * @return List<User>
     */
    public List<User> getAllUsers() {
        logger.info("Getting user list");
        return helperService.internalUserMap.values().stream().collect(Collectors.toList());
    }
    
    /**
     * Add an user
     *
     * @param user
     * @return User
     */
    public User addUser(User user) {
        logger.info("Adding user "+ JsonStream.serialize(user));
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
        logger.info("Tracking location for user "+JsonStream.serialize(user));
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
        logger.info("Getting reward list for user "+JsonStream.serialize(user));
        return user.getUserRewards();
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


