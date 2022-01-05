package com.example.tourguidemodule.controller;//package tourGuide.controller;


import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import com.example.tourguidemodule.user.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    
    @Autowired
    TourGuideService tourGuideService;
    
    /**
     * Get a list with all users
     *
     * @return List<User>
     */
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return tourGuideService.getAllUsers();
    }
    
    /**
     * Get user information
     *
     * @param userName
     * @return User
     */
    @GetMapping("/getUser")
    public User getUser(String userName) {
        return tourGuideService.getUser(userName);
    }
    
    /**
     * Create user
     *
     * @param user
     * @return User
     */
    @PostMapping("/addUser")
    public User addUser(@RequestParam User user) {
        return tourGuideService.addUser(user);
    }
    
    /**
     * Update user preferences
     *
     * @param userName
     * @param newPreferences
     * @return UserPreferences
     */
    @PutMapping("/updateUserPreferences")
    public UserPreferences modifyUserPreferences(@RequestParam String  userName, @RequestBody HashMap<String,String> newPreferences){
        return tourGuideService.modifyUserPreferences(userName,newPreferences);
    }
}
