package com.example.tourguidemodule.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Declare the microservice-rewardcentral endpoint
 *
 */
@FeignClient(name = "microservice-rewardcentral", url = "mrewardcentral:9002")
public interface RewardCentralProxy {
    
    /**
     * @see mrewardcentral.controller
     */
    @GetMapping(value = "/getRewardPoints")
    int getRewardPoints(@RequestParam("attractionId") String attractionId, @RequestParam("userId") String userId);

}
