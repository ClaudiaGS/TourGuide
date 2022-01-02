package com.example.tourguidemodule.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Declare the microservice endpoint
 *
 */
@FeignClient(name = "microservice-rewardcentral", url = "localhost:9002")
public interface RewardCentralProxy {
    
    @GetMapping(value = "/getRewardPoints")
    int getRewardPoints(@RequestParam("attractionId") String attractionId, @RequestParam("userId") String userId);

}
