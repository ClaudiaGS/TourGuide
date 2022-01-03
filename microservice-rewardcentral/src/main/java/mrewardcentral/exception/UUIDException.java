package mrewardcentral.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UUIDException extends Exception {
    
    private static final Logger logger = LogManager.getLogger("UUIDExceptionRewardCentral");
    
    /**
     * Get invalid id message
     *
     * @param userId
     * @param attractionId
     *
     */
    public UUIDException(String userId, String attractionId) {
        super("UUID userId invalid for " + userId + "UUID attractionId invalid for " + attractionId);
        
        logger.error("UUID userId invalid for " + userId + "UUID attractionId invalid for " + attractionId);
        
    }
}
