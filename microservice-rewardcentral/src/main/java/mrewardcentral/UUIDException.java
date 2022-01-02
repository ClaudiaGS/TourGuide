package mrewardcentral;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UUIDException extends Exception {
    
    private static final Logger logger = LogManager.getLogger("UUIDExceptionRewardCentral");
    public UUIDException(String userId, String attractionId) {
        super("UUID userId invalid  : " + userId + "UUID attractionId invalid  : " + attractionId);
        logger.error("UUID userId invalid  : " + userId + "UUID attractionId invalid  : " + attractionId);
    }
}
