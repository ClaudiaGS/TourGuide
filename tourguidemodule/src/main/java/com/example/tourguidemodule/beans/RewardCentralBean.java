package com.example.tourguidemodule.beans;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RewardCentralBean {
    public RewardCentralBean() {
    }
    
    public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) ThreadLocalRandom.current().nextInt(1, 1000));
        } catch (InterruptedException var4) {
        }
        
        int randomInt = ThreadLocalRandom.current().nextInt(1, 1000);
        return randomInt;
    }
}
