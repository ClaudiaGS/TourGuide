package com.example.tourguidemodule.integration;

import com.example.tourguidemodule.beans.AttractionBean;
import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.RewardCentralBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.helper.InternalTestHelper;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class TestPerformance {
    private static final Logger logger = LogManager.getLogger("TestService");
    /*
     * A note on performance improvements:
     *
     *     The number of users generated for the high volume tests can be easily adjusted via this method:
     *
     *     		InternalTestHelper.setInternalUserNumber(100000);
     *
     *
     *     These tests can be modified to suit new solutions, just as long as the performance metrics
     *     at the end of the tests remains consistent.
     *
     *     These are performance metrics that we are trying to hit:
     *
     *     highVolumeTrackLocation: 100,000 users within 15 minutes:
     *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
     *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */
    @Test
    public void highVolumeTrackLocation() {
        GpsUtilBean gpsUtil = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
        HelperService helperService = new HelperService();
        // Users should be incremented up to 100,000, and test finishes within 15 minutes
        InternalTestHelper.setInternalUserNumber(100);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
        
        List<User> allUsers = new ArrayList<>();
        allUsers = tourGuideService.getAllUsers();
        
        List<CompletableFuture> lCompletable = new ArrayList<>();
        Executor executor = Executors.newFixedThreadPool(100);
        
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (User user : allUsers) {
            
            CompletableFuture completable = CompletableFuture.runAsync(
                    () -> {
                        tourGuideService.trackUserLocation(user);
                    }
                    , executor);
            
            lCompletable.add(completable);
        }
        
        CompletableFuture.allOf(lCompletable.toArray(new CompletableFuture[lCompletable.size()])).join();
        
        stopWatch.stop();
        tourGuideService.tracker.stopTracking();
        
        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }
    
    
    @Test
    public void highVolumeGetRewards() {
        GpsUtilBean gpsUtil = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
        HelperService helperService = new HelperService();
        // Users should be incremented up to 100,000, and test finishes within 20 minutes
        InternalTestHelper.setInternalUserNumber(100);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
        
        AttractionBean attraction = gpsUtil.getAttractions().get(0);
        logger.info("attraction " + attraction.attractionName);
        List<User> allUsers = tourGuideService.getAllUsers();
        
        List<CompletableFuture> lCompletable = new ArrayList<>();
        Executor executor = Executors.newFixedThreadPool(100);
        
        for (User user : allUsers) {
            CompletableFuture completable = CompletableFuture.runAsync(
                    () -> {
                        user.addToVisitedLocations(new VisitedLocationBean(user.getUserId(), attraction, new Date()));
                        logger.info("user " + user.getUserName());
                        rewardsService.calculateRewards(user);
                    }
                    , executor);
            
            lCompletable.add(completable);
            
            
        }
        CompletableFuture.allOf(lCompletable.toArray(new CompletableFuture[lCompletable.size()])).join();
        for (User user : allUsers) {
            assertTrue(user.getUserRewards().size() > 0);
        }
        stopWatch.stop();
        tourGuideService.tracker.stopTracking();
        
        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }
    
}
