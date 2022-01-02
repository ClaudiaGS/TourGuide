package com.example.tourguidemodule.controller;//package tourGuide.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.jsoniter.output.JsonStream;
//import gpsUtil.location.Attraction;
//import gpsUtil.location.VisitedLocation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import tourGuide.service.RewardsService;
//import tourGuide.service.TourGuideService;
//import tourGuide.user.User;
//import tripPricer.Provider;
//
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//public class TourGuideController {
//
//    @Autowired
//    TourGuideService tourGuideService;
//    @Autowired
//    RewardsService rewardsService;
//
//    @RequestMapping("/")
//    public String index() {
//        return "Greetings from TourGuide!";
//    }
//
//    @RequestMapping("/getLocation")
//    public String getLocation(@RequestParam String userName) {
//        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
//        return JsonStream.serialize(visitedLocation.location);
//    }
//
//    //  TODO: Change this method to no longer return a List of Attractions.
//    //  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
//    //  Return a new JSON object that contains:
//    // Name of Tourist attraction,
//    // Tourist attractions lat/long,
//    // The user's location lat/long,
//    // The distance in miles between the user's location and each of the attractions.
//    // The reward points for visiting each Attraction.
//    //    Note: Attraction reward points can be gathered from RewardsCentral
//    @RequestMapping("/getNearbyAttractions")
//    public String getNearbyAttractions(@RequestParam String userName) {
//        User user = tourGuideService.getUser(userName);
//        VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
//        List<Attraction> fiveNearestAttractions = tourGuideService.getFiveNearByAttractions(user);
//        String jsonString = "";
//
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//
//            ArrayNode attractionNode = mapper.createArrayNode();
//            for (Attraction attraction : fiveNearestAttractions) {
//                ObjectNode attractionDataNode = mapper.createObjectNode();
//                attractionDataNode.put("attractionName", attraction.attractionName);
//                attractionDataNode.put("attractionLatitude", attraction.latitude);
//                attractionDataNode.put("attractionLongitude", attraction.longitude);
//                attractionDataNode.put("distanceUserAttraction", rewardsService.getDistance(visitedLocation.location, attraction));
//                attractionDataNode.put("rewardPoints", rewardsService.getRewardPoints(attraction, user));
//                attractionNode.add(attractionDataNode);
//            }
//
//
//            ObjectNode usersDataNode = mapper.createObjectNode();
//            usersDataNode.put("usersLatitude", visitedLocation.location.latitude);
//            usersDataNode.put("usersLongitude", visitedLocation.location.longitude);
//
//            ObjectNode completeNode = mapper.createObjectNode();
//            completeNode.set("user", usersDataNode);
//            completeNode.set("attractions", attractionNode);
//
//            jsonString = mapper.writeValueAsString(completeNode);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return jsonString;
//    }
//
//    @RequestMapping("/getRewards")
//    public String getRewards(@RequestParam String userName) {
//        return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
//    }
//
//    @RequestMapping("/getAllCurrentLocations")
//    public String getAllCurrentLocations() {
//        // TODO: Get a list of every user's most recent location as JSON
//        //- Note: does not use gpsUtil to query for their current location,
//        //        but rather gathers the user's current location from their stored location history.
//        //
//        // Return object should be the just a JSON mapping of userId to Locations similar to:
//        //     {
//        //        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371}
//        //        ...
//        //     }
//        HashMap<String, HashMap<String, Double>> currentLocations = new HashMap<>();
//        List<User> userList = tourGuideService.getAllUsers();
//        HashMap<String ,Double>location=new HashMap<>();
//        for (User user : userList){
//            location.put("latitude", user.getLastVisitedLocation().location.latitude);
//            location.put("longitude", user.getLastVisitedLocation().location.longitude);
//            currentLocations.put(String.valueOf(user.getUserId()),location);
//        }
//        return JsonStream.serialize(currentLocations);
//    }
//
//    @RequestMapping("/getTripDeals")
//    public String getTripDeals(@RequestParam String userName) {
//        List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
//        return JsonStream.serialize(providers);
//    }
//
//    private User getUser(String userName) {
//        return tourGuideService.getUser(userName);
//    }
//
//
//}