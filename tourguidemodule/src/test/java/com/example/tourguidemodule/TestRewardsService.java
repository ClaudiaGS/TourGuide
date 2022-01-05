package com.example.tourguidemodule;

import com.example.tourguidemodule.beans.AttractionBean;
import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.RewardCentralBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.helper.InternalTestHelper;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserReward;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRewardsService {
    
    @Test
    public void userGetRewards() {
        GpsUtilBean gpsUtil = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
        
        InternalTestHelper.setInternalUserNumber(0);
        HelperService helperService = new HelperService();
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
        
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        AttractionBean attraction = gpsUtil.getAttractions().get(0);
        user.addToVisitedLocations(new VisitedLocationBean(user.getUserId(), attraction, new Date()));
        tourGuideService.trackUserLocation(user);
        List<UserReward> userRewards = user.getUserRewards();
        tourGuideService.tracker.stopTracking();
        assertTrue(userRewards.size() == 1);
    }
    
    @Test
    public void isWithinAttractionProximity() {
        GpsUtilBean gpsUtil = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
        AttractionBean attraction = gpsUtil.getAttractions().get(0);
        assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
    }
    
    //@Ignore // Needs fixed - can throw ConcurrentModificationException
    @Test
    public void nearAllAttractions() {
        GpsUtilBean gpsUtil = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);
        InternalTestHelper.setInternalUserNumber(1);
        HelperService helperService = new HelperService();
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService,rewardsService);
        rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
        List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
        assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
        tourGuideService.tracker.stopTracking();
    }
    
}
