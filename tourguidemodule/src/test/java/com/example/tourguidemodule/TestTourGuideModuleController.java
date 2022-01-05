package com.example.tourguidemodule;

import com.example.tourguidemodule.beans.LocationBean;
import com.example.tourguidemodule.beans.ProviderBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.controller.TourGuideModuleController;
import com.example.tourguidemodule.proxy.GpsUtilProxy;
import com.example.tourguidemodule.proxy.RewardCentralProxy;
import com.example.tourguidemodule.proxy.TripPricerProxy;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import com.jsoniter.output.JsonStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TourGuideModuleController.class)
public class TestTourGuideModuleController {
    
    @Autowired
    MockMvc mvc;
    @MockBean
    GpsUtilProxy gpsUtilProxy;
    
    @MockBean
    RewardCentralProxy rewardCentralProxy;
    
    @MockBean
    TripPricerProxy tripPricerProxy;
    
    @MockBean
    HelperService helperService;
    
    @MockBean
    TourGuideService tourGuideService;
    
    @MockBean
    RewardsService rewardsService;
    
    @Autowired
    @InjectMocks
    TourGuideModuleController tourGuideModuleController;
    
    
    @Test
    public void getLocationTest() {
        UUID userId = UUID.randomUUID();
        LocationBean locationBean = new LocationBean(100.0, 30.2);
        Date date = new Date();
        String userName = "initialUser1";
        VisitedLocationBean visitedLocationBean = new VisitedLocationBean(userId, locationBean, date);
        User user = new User(userId, userName, "111", "initialUser1@email");
        when(gpsUtilProxy.getLocationGpsUtil(userId.toString())).thenReturn(visitedLocationBean);
        when(tourGuideService.getUser(userName)).thenReturn(user);
        
        //assertion with controller method
        assertEquals(JsonStream.serialize(visitedLocationBean), tourGuideModuleController.getLocation(userName));
        
        try {
            MvcResult result = this.mvc.perform(MockMvcRequestBuilders
                    .get("/getLocation")
                    .param("userName", userName)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            
            //assertion with MockMvc
            assertEquals(result.getResponse().getContentAsString(), JsonStream.serialize(visitedLocationBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void getAllCurrentlocationsTest() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        List<UUID> idList = new ArrayList<>();
        idList.add(userId1);
        idList.add(userId2);
        LocationBean location1 = new LocationBean(100.0, 30.2);
        LocationBean location2 = new LocationBean(101.0, 30.5);
        HashMap<String, HashMap<String, Double>> currentLocations = new HashMap<>();
        HashMap<String, Double> location1Map = new HashMap<>();
        location1Map.put("latitude", location1.latitude);
        location1Map.put("longitude", location1.longitude);
        currentLocations.put(String.valueOf(userId1), location1Map);
        
        HashMap<String, Double> location2Map = new HashMap<>();
        location2Map.put("latitude", location2.latitude);
        location2Map.put("longitude", location2.longitude);
        currentLocations.put(String.valueOf(userId2), location2Map);
        
        when(gpsUtilProxy.getAllCurrentLocations(idList)).thenReturn(currentLocations);
        
        List<User> userList = new ArrayList<>();
        User user1 = new User(userId1, "user1", "111", "user1@email");
        User user2 = new User(userId2, "user2", "222", "user2@email");
        userList.add(user1);
        userList.add(user2);
        
        when(tourGuideService.getAllUsers()).thenReturn(userList);
        
        //assertion with controller method
        assertEquals(JsonStream.serialize(currentLocations), tourGuideModuleController.getAllCurrentLocations());
        
        MvcResult result = null;
        try {
            result = mvc.perform(MockMvcRequestBuilders
                    .get("/getAllCurrentLocations")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            
            //assertion with MockMvc
            assertEquals(JsonStream.serialize(currentLocations), result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getNearbyAttractionsTest() {
        
        String userName = "internalUser1";
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "initialUser1", "000222", "initialUser1@tourguide");
        
        try {
            
            this.mvc.perform(MockMvcRequestBuilders
                    .get("/getNearbyAttractions")
                    .param("userName", userName)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void getTripDealsTest() {
        String userName = "internalUser1";
        UUID userId = UUID.randomUUID();
        
        User user = new User(userId, userName, "111", "user1@email");
        List<String> data = new ArrayList<>();
        int cumulativeRewardPoints = 2;
        
        data.add(helperService.tripPricerApiKey);
        data.add(user.getUserId().toString());
        data.add(String.valueOf(user.getUserPreferences().getNumberOfAdults()));
        data.add(String.valueOf(user.getUserPreferences().getNumberOfChildren()));
        data.add(String.valueOf(user.getUserPreferences().getTripDuration()));
        data.add(String.valueOf(cumulativeRewardPoints));
        
        List<ProviderBean> providerList = new ArrayList<>();
        when(tourGuideService.getUser(userName)).thenReturn(user);
        when(tripPricerProxy.getTripDealsTripPricer(data)).thenReturn(providerList);
        
        //assertion with controller method
        assertEquals(JsonStream.serialize(providerList), tourGuideModuleController.getTripDeals(userName));
        try {
            
            MvcResult result=this.mvc.perform(MockMvcRequestBuilders
                    .get("/getTripDeals")
                    .param("userName", userName)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            
            //assertion with MockMvc
           assertEquals(JsonStream.serialize(providerList),result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
