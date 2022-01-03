package com.example.tourguidemodule.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;


public class GpsUtilMocks {
    
    public static void setupMockGpsUtilResponse(WireMockServer mockService, String userId) throws IOException {
     
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/getLocationGpsUtil?userId="+userId))
                .withQueryParam("userId",equalTo(userId))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                ));
    }
    
}