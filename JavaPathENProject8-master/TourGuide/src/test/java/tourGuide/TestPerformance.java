package tourGuide;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPerformance {
	
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
	
	StopWatch stopWatch = new StopWatch();
//	public void process() {
//		System.out.println(Thread.currentThread() + " Process");
//		InternalTestHelper.setInternalUserNumber(1000);
//
//
//
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		// Users should be incremented up to 100,000, and test finishes within 15 minutes
//		InternalTestHelper.setInternalUserNumber(1000);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//
//		List<User> allUsers = new ArrayList<>();
//		allUsers = tourGuideService.getAllUsers();
//
//
//		stopWatch.start();
//		for(User user : allUsers) {
//			tourGuideService.trackUserLocation(user);
//		}
//		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();
//
//
//	}
//public CompletableFuture<Integer> setInternalUserNumber() throws ExecutionException, InterruptedException {
//	System.out.println(Thread.currentThread() + " set internal user number to ");
//	try {
//		sleep(1);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		// Users should be incremented up to 100,000, and test finishes within 15 minutes
//		//InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//	InternalTestHelper.setInternalUserNumber(1000);
//	CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> tourGuideService.getAllUsers());
////	for(User u:runAsync2.get()){
//	CompletableFuture<Void> runAsync3=CompletableFuture.runAsync(()->(tourGuideService.getAllUsers().forEach(user->tourGuideService.trackUserLocation(user)));
////	}
//	tourGuideService.getAllUsers().forEach(item->runAsync3)
//	CompletableFuture<Void>completableFuture=CompletableFuture.runAsync(() -> tourGuideService.getAllUsers());
//	CompletableFuture<Void>future=completableFuture.thenApply(tourGuideService.trackUserLocation())
//
//}

	@Test
	public CompletableFuture<Integer> testOne(){
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		//InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		CompletableFuture.supplyAsync(()->tourGuideService.getAllUsers()).thenApply(
				tourGuideService.getAllUsers().forEach(user->tourGuideService.trackUserLocation(user)));
		
	}
//	@Test
//	public void completableTest(){
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		// Users should be incremented up to 100,000, and test finishes within 15 minutes
//		//InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//
//		List<User> allUsers = new ArrayList<>();
//		allUsers = tourGuideService.getAllUsers();
//
//
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for(User user : allUsers) {
//			tourGuideService.trackUserLocation(user);
//		}
//		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();
//
//		CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() ->tourGuideService.getAllUsers());
//		CompletableFutureCompletableFuture.supplyAsync(allUsers);
//
//		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//	}
	@Test
	public void completableFutureRunAsync() {
		
			InternalTestHelper.setInternalUserNumber(1);
			
			CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> highVolumeTrackLocation());
			
			runAsync.join();
		
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		
		assertEquals(10000, InternalTestHelper.getInternalUserNumber());
		assertTrue(TimeUnit.MINUTES.toSeconds(0) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	
	}
	@Ignore
	@Test
	public void highVolumeTrackLocation() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(1000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		tourGuideService.getAllUsers().forEach(user->tourGuideService.trackUserLocation(user));
		for(User user : allUsers) {
			tourGuideService.trackUserLocation(user);
		}
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	@Ignore
	@Test
	public void highVolumeGetRewards() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		InternalTestHelper.setInternalUserNumber(100);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
	    Attraction attraction = gpsUtil.getAttractions().get(0);
		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
	     
	    allUsers.forEach(u -> rewardsService.calculateRewards(u));
	    
		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}
