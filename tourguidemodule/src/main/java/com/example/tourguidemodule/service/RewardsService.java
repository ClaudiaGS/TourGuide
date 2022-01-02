package com.example.tourguidemodule.service;

import org.springframework.stereotype.Service;
import com.example.tourguidemodule.beans.*;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserReward;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    
    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    
    private GpsUtilBean gpsUtilBean;
    private RewardCentralBean rewardCentralBean;
    
    private int attractionProximityRange = 200;
    
    public RewardsService(GpsUtilBean gpsUtilBean, RewardCentralBean rewardCentralBean) {
        this.gpsUtilBean = gpsUtilBean;
        this.rewardCentralBean = rewardCentralBean;
    }
    
    
    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }
    
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
    
    
    private boolean nearAttraction(VisitedLocationBean visitedLocationBean, AttractionBean attractionBean) {
        return getDistance(attractionBean, visitedLocationBean.locationBean) > proximityBuffer ? false : true;
    }
    
    public boolean isWithinAttractionProximity(AttractionBean attractionBean, LocationBean locationBean) {
        return getDistance(attractionBean, locationBean) > attractionProximityRange ? false : true;
    }
    
    public int getRewardPoints(AttractionBean attractionBean, User user) {
        return rewardCentralBean.getAttractionRewardPoints(attractionBean.attractionId, user.getUserId());
    }
    
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
//


}
