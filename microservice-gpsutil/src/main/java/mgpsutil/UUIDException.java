package mgpsutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UUIDException extends Exception {
    
    private static final Logger logger = LogManager.getLogger("UUIDExceptionGpsUtil");
    public UUIDException(String userId) {
        super("UUID userId invalid  : " + userId);
        logger.error("UUID userId invalid  : " + userId);
    }
}


