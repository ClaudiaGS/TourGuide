package com.example.tourguidemodule.controller;//package tourGuide.controller;


import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.service.UserService;
import com.example.tourguidemodule.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
@Autowired
    TourGuideService tourGuideService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return tourGuideService.getAllUsers();
    }

    @GetMapping("/getUser")
    public User getUser(String userName){
        return tourGuideService.getUser(userName);
    }
    @PostMapping("/user")
    public User addUser(@RequestParam User user){
        return tourGuideService.addUser(user);
    }
}
