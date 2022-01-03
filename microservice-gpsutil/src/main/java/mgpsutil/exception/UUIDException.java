package mgpsutil.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UUIDException extends Exception {
    
    private static final Logger logger = LogManager.getLogger("UUIDExceptionGpsUtil");
    
    /**
     * Get invalid userId message
     *
     * @param userId
     *
     */
    public UUIDException(String userId) {
        super("UUID userId invalid for " + userId);
        logger.error("UUID userId invalid for " + userId);
    }
}


