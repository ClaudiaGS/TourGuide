package com.example.tourguidemodule.service;


import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.tracker.Tracker;
import com.example.tourguidemodule.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourGuideService {
    private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
    @Autowired
    private GpsUtilBean gpsUtilBean;
    @Autowired
    private RewardsService rewardsService;
    //@Autowired
  //  private TripPricerBean tripPricerBean;

    
    @Autowired
    HelperService helperService;
    
    public final Tracker tracker;
    boolean testMode = true;
    
    
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
    
    public User getUser(String userName) {
        return helperService.internalUserMap.get(userName);
    }
    
    public List<User> getAllUsers() {
        
        return helperService.internalUserMap.values().stream().collect(Collectors.toList());
    }
    
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
//
//
//    public VisitedLocation getUserLocation(User user) {
//        VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
//                user.getLastVisitedLocation() :
//                trackUserLocation(user);
//        return visitedLocation;
//    }


//    public List<ProviderBean> getTripDeals(User user) {
//        int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
//        List<ProviderBean> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
//                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
//        user.setTripDeals(providers);
//        return providers;
//    }
//    public VisitedLocationBean trackUserLocation(User user) {
//        VisitedLocationBean visitedLocation = gpsUtil.getUserLocation(user.getUserId());
//        user.addToVisitedLocations(visitedLocation);
//        rewardsService.calculateRewards(user);
//        return visitedLocation;
//    }
    
    public VisitedLocationBean trackUserLocation(User user) {
        VisitedLocationBean visitedLocationBean = gpsUtilBean.getUserLocation(user.getUserId());
        user.addToVisitedLocations(visitedLocationBean);
        rewardsService.calculateRewards(user);
        return visitedLocationBean;
    }
  
//
//    public List<AttractionBean> getNearByAttractions(VisitedLocationBean visitedLocationBean) {
//        List<AttractionBean> nearbyAttractions = new ArrayList<>();
//        for (AttractionBean attractionBean : gpsUtilBean.getAttractions()) {
//            if (rewardsService.isWithinAttractionProximity(attractionBean, visitedLocationBean.locationBean)) {
//                nearbyAttractions.add(attraction);
//            }
//        }
//
//        return nearbyAttractions;
//    }

//    // get 5 attractions, near the user
//    public List<Attraction> getFiveNearByAttractions(User user) {
//        List<Attraction> fiveNearByAttractions = new ArrayList<>();
//        Map<Double, Attraction> attractionsDistances = new HashMap<>();
//        int countAttraction = 0;
//        VisitedLocation visitedLocation = trackUserLocation(user);
//        List<Double> distances = new ArrayList<>();
//        for (Attraction attraction : gpsUtil.getAttractions()) {
//            Double distance = rewardsService.getDistance(attraction, visitedLocation.location);
//            attractionsDistances.put(distance, attraction);
//            distances.add(distance);
//        }
//        distances.sort(Comparator.naturalOrder());
//
//        for (Double distance : distances) {
//            if (countAttraction <= 4) {
//                fiveNearByAttractions.add(attractionsDistances.get(distance));
//                countAttraction += 1;
//            }
//        }
//        return fiveNearByAttractions;
//    }
    
    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }
    
//
//    /**********************************************************************************
//     *
//     * Methods Below: For Internal Testing
//     *
//     **********************************************************************************/
//    public static final String tripPricerApiKey = "test-server-api-key";
//    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
//    private final Map<String, User> internalUserMap = new HashMap<>();
//
//    private void initializeInternalUsers() {
//        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
//            String userName = "internalUser" + i;
//            String phone = "000";
//            String email = userName + "@tourGuide.com";
//            User user = new User(UUID.randomUUID(), userName, phone, email);
//            generateUserLocationHistory(user);
//
//            internalUserMap.put(userName, user);
//        });
//        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
//    }
//
//    private void generateUserLocationHistory(User user) {
//        IntStream.range(0, 3).forEach(i -> {
//            user.addToVisitedLocations(new VisitedLocationBean(user.getUserId(), new LocationBean(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
//        });
//    }
//
//    private double generateRandomLongitude() {
//        double leftLimit = -180;
//        double rightLimit = 180;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private double generateRandomLatitude() {
//        double leftLimit = -85.05112878;
//        double rightLimit = 85.05112878;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private Date getRandomTime() {
//        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
//        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
//    }
//
}


