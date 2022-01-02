package com.example.tourguidemodule.errorhandling;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {

        if(reponse.status() == 400 ) {
            return new ProductBadRequestException(
                    "Bad request "
            );
        }

        return defaultErrorDecoder.decode(invoqueur, reponse);
    }

}