package mrewardcentral.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardCentralService {
    
    private static final Logger logger = LogManager.getLogger("RewardCentralService");
    
    RewardCentral rewardCentral = new RewardCentral();
    
    /**
     * Get the reward points
     *
     * @param attractionId
     * @param userId
     * @return int
     */
    public int getRewardPoints(UUID attractionId, UUID userId) {
        
        logger.info("Getting reward points for attraction with id " + attractionId + " and user with id " + userId);
        
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }
    
}
