package com.example.tourguidemodule.service;

import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserReward;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
//    @Autowired
//    HelperService helperService;
    boolean testMode = true;
    
//    public List<User> getAllUsers() {
//        return helperService.internalUserMap.values().stream().collect(Collectors.toList());
//    }
    
//    public User addUser(User user) {
//        boolean done=false;
//        if (!helperService.internalUserMap.containsKey(user.getUserName())) {
//            helperService.internalUserMap.put(user.getUserName(), user);
//            done=true;
//        }
//        if(done){
//            return user;
//        }else return null;
//    }
    
//    public User getUser(String userName) {
//        try {
//            helperService.initializeInternalUsers();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return helperService.internalUserMap.get(userName);
//    }
    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }
//    public VisitedLocationBean addToVisitedLocations(VisitedLocationBean visitedLocationBean){
//        user.addToVisitedLocations(visitedLocationBean);
//        return visitedLocationBean;
//
//    }
//    public VisitedLocationBean trackUserLocation(User user) {
//        VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
//        user.addToVisitedLocations(visitedLocation);
//        rewardsService.calculateRewards(user);
//        return visitedLocation;
//    }
}
