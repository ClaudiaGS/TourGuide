package mrewardcentral.controller;

import mrewardcentral.UUIDException;
import mrewardcentral.service.RewardCentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RewardCentralController {
    
    @Autowired
    RewardCentralService rewardCentralService;
    
    /**
     *
     * Get the reward points endpoint from the microservice-rewardcentral, to supply to the tourguidemodule
     * @param userId
     * @return VisitedLocation
     *
     */
    @GetMapping("/getRewardPoints")
    public int getRewardPoints(@RequestParam String attractionId, @RequestParam String userId) throws UUIDException {
       UUID attractionIdUuid=null;
       UUID userIdUuid=null;
        try {
            attractionIdUuid=UUID.fromString(attractionId);
            userIdUuid=UUID.fromString(userId);
        }catch (IllegalArgumentException e){
            throw new UUIDException(userId,attractionId);
            
        }
        return rewardCentralService.getRewardPoints(attractionIdUuid, userIdUuid);
    }
    
}
