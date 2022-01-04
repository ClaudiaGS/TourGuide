package com.example.tourguidemodule.integration;

import com.example.tourguidemodule.beans.*;
import com.example.tourguidemodule.helper.InternalTestHelper;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTourGuideService {
	
	@Test
	public void getUserLocation() {
		GpsUtilBean gpsUtil = new GpsUtilBean();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
		InternalTestHelper.setInternalUserNumber(0);
		HelperService helperService=new HelperService();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocationBean visitedLocation = tourGuideService.trackUserLocation(user);
		tourGuideService.tracker.stopTracking();
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}
	
	@Test
	public void addUser() {
		GpsUtilBean gpsUtil = new GpsUtilBean();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
		InternalTestHelper.setInternalUserNumber(0);
		HelperService helperService=new HelperService();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		
		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		GpsUtilBean gpsUtil = new GpsUtilBean();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
		InternalTestHelper.setInternalUserNumber(0);
		HelperService helperService=new HelperService();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		
		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();
		
		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	
	@Test
	public void trackUser() {
		GpsUtilBean gpsUtil = new GpsUtilBean();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralBean());
		InternalTestHelper.setInternalUserNumber(0);
		HelperService helperService=new HelperService();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, helperService, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocationBean visitedLocation = tourGuideService.trackUserLocation(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user.getUserId(), visitedLocation.userId);
	}
	
	
}
