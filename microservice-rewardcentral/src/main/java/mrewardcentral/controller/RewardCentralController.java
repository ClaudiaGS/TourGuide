package mrewardcentral.controller;

import mrewardcentral.exception.UUIDException;
import mrewardcentral.service.RewardCentralService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RewardCentralController {
    
    private static final Logger logger = LogManager.getLogger("RewardCentralController");
    
    @Autowired
    RewardCentralService rewardCentralService;
    
    /**
     *
     * Get the reward points endpoint from the microservice-rewardcentral, to supply to the tourguidemodule
     * @param attractionId
     * @param userId
     * @return int
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
        int rewardPoints=rewardCentralService.getRewardPoints(attractionIdUuid, userIdUuid);
        
        logger.info("Reward points for user with id "+userId+" and attraction with id "+attractionId+" are "+rewardPoints);
        
        return rewardPoints;
    }
    
}
