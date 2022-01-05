package com.example.tourguidemodule.service;

import com.example.tourguidemodule.beans.*;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserReward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RewardsService {
    
    private static final Logger logger = LogManager.getLogger("RewardsService");
    
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    
    private GpsUtilBean gpsUtilBean;
    private RewardCentralBean rewardCentralBean;
    
    private int attractionProximityRange = 200;
    
    /**
     * Constructor
     *
     * @param gpsUtilBean
     * @param rewardCentralBean
     *
     */
    public RewardsService(GpsUtilBean gpsUtilBean, RewardCentralBean rewardCentralBean) {
        this.gpsUtilBean = gpsUtilBean;
        this.rewardCentralBean = rewardCentralBean;
    }
    
    /**
     * Set proximity
     *
     * @param proximityBuffer
     * @return void
     */
    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }
    
    /**
     * Get the rewards for an user
     *
     * @param user
     * @return void
     */
    public void calculateRewards(User user) {
        
        CopyOnWriteArrayList<VisitedLocationBean> userLocations = new CopyOnWriteArrayList<>();
        userLocations.addAll(user.getVisitedLocations());
        CopyOnWriteArrayList<AttractionBean> attractions = new CopyOnWriteArrayList<>();
        attractions.addAll(gpsUtilBean.getAttractions());
        
        for (VisitedLocationBean visitedLocationBean : userLocations) {
            for (AttractionBean attractionBean : attractions) {
                if (user.getUserRewards().stream().filter(r -> r.attractionBean.attractionName.equals(attractionBean.attractionName)).count() == 0) {
                    if (nearAttraction(visitedLocationBean, attractionBean)) {
                        user.addUserReward(new UserReward(visitedLocationBean, attractionBean, getRewardPoints(attractionBean, user)));
                    }
                }
            }
        }
    }
    
    
    /**
     * Check if the attraction is near a location
     *
     * @param visitedLocationBean
     * @param attractionBean
     * @return boolean
     */
    private boolean nearAttraction(VisitedLocationBean visitedLocationBean, AttractionBean attractionBean) {
        return getDistance(attractionBean, visitedLocationBean.locationBean) > proximityBuffer ? false : true;
    }
    
    /**
     * Check if location is within attraction proximity
     *
     * @param locationBean
     * @param attractionBean
     * @return boolean
     */
    public boolean isWithinAttractionProximity(AttractionBean attractionBean, LocationBean locationBean) {
        return getDistance(attractionBean, locationBean) > attractionProximityRange ? false : true;
    }
    
    /**
     * Get the reward points
     *
     * @param user
     * @param attractionBean
     * @return int
     */
    public int getRewardPoints(AttractionBean attractionBean, User user) {
        return rewardCentralBean.getAttractionRewardPoints(attractionBean.attractionId, user.getUserId());
    }
    
    /**
     * Get distance between 2 locations
     *
     * @param loc1
     * @param loc2
     * @return double
     */
    public double getDistance(LocationBean loc1, LocationBean loc2) {
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
