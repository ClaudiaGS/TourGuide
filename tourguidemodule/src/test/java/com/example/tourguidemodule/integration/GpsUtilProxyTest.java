package com.example.tourguidemodule.integration;

import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.LocationBean;
import com.example.tourguidemodule.beans.RewardCentralBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;
import com.example.tourguidemodule.configuration.GpsUtilMocks;
import com.example.tourguidemodule.configuration.WireMockConfig;
import com.example.tourguidemodule.helper.InternalTestHelper;
import com.example.tourguidemodule.proxy.GpsUtilProxy;
import com.example.tourguidemodule.service.HelperService;
import com.example.tourguidemodule.service.RewardsService;
import com.example.tourguidemodule.service.TourGuideService;
import com.example.tourguidemodule.user.User;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
public class GpsUtilProxyTest {
    private static final Logger logger = LogManager.getLogger("GpsUtilProxyTest");
    
    
    @Autowired
    private WireMockServer mockGpsUtilService;
    @Autowired
    GpsUtilProxy gpsUtilProxy;
    
    
    @BeforeEach
    void setUp() throws IOException {
        GpsUtilMocks.setupMockGpsUtilResponse(mockGpsUtilService);
    }

//
//        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/getLocationGpsUtil"))
//                .withQueryParam("userId", equalTo(user.getUserId().toString()))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                        .withBody(String.valueOf(visitedLocation))));
//        return user.getUserId().toString();
//    }
//
    
    
    @Test
    public void getLocationGpsUtilProxy() {
        String userId = null;
        
        InternalTestHelper internalTestHelper = new InternalTestHelper();
        internalTestHelper.setInternalUserNumber(1);
        HelperService helperService = new HelperService();
        
        GpsUtilBean gpsUtilBean = new GpsUtilBean();
        RewardsService rewardsService = new RewardsService(gpsUtilBean, new RewardCentralBean());
        TourGuideService tourGuideService = new TourGuideService(gpsUtilBean, helperService, rewardsService);
        User user = tourGuideService.getAllUsers().get(0);
        userId = user.getUserId().toString();
        LocationBean location = new LocationBean(80.2, 35.0);
        VisitedLocationBean visitedLocation = new VisitedLocationBean(user.getUserId(), location, new Date());
        
        
        assertNotNull(gpsUtilProxy.getLocation(userId));
    }
//    @Test
//    public void getLocationGpsUtilProxy() {
//        try {
//            userId=setupMockBooksResponse(mockGpsUtilService);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logger.info("userIdhere"+userId);
//        assertNotNull(gpsUtilProxy.getLocation(userId));
//    }

}

//        @Test
//    public void getLocation(){
//        InternalTestHelper internalTestHelper = new InternalTestHelper();
//        internalTestHelper.setInternalUserNumber(1);
//        HelperService helperService=new HelperService();
//        GpsUtilBean gpsUtilBean=new GpsUtilBean();
//        RewardsService rewardsService=new RewardsService(gpsUtilBean,new RewardCentralBean());
//        TourGuideService tourGuideService=new TourGuideService(gpsUtilBean,helperService,rewardsService);
//        HttpResponse httpResponse = response()
//                    .withStatusCode(200);
//        User user= tourGuideService.getAllUsers().get(0);
//        try {
//            mockServer
//                    .when(
//                            request().withPath("/getLocationGpsUtil")
//                                    .withPathParameter("userId", user.getUserId().toString()))
//                    .respond(httpResponse);
//        }catch (Exception e){
//            e.getMessage();
//        }
//        assertNotNull(gpsUtilProxy.getLocation(user.getUserId().toString()));
//    }

