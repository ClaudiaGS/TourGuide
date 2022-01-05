package com.example.tourguidemodule.beans;

import com.revinate.guava.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GpsUtilBean {
    
    private static final RateLimiter rateLimiter = RateLimiter.create(1000.0D);
    
    public GpsUtilBean() {
    }
    
    public VisitedLocationBean getUserLocation(UUID userId) {
        rateLimiter.acquire();
        this.sleep();
        double longitude = ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D);
        longitude = Double.parseDouble(String.format("%.6f", longitude));
        double latitude = ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D);
        latitude = Double.parseDouble(String.format("%.6f", latitude));
        VisitedLocationBean visitedLocationBean = new VisitedLocationBean(userId, new LocationBean(latitude, longitude), new Date());
        return visitedLocationBean;
    }
    
    public List<AttractionBean> getAttractions() {
        rateLimiter.acquire();
        this.sleepLighter();
        List<AttractionBean> attractions = new ArrayList();
        attractions.add(new AttractionBean("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
        attractions.add(new AttractionBean("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
        attractions.add(new AttractionBean("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
        attractions.add(new AttractionBean("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
        attractions.add(new AttractionBean("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
        attractions.add(new AttractionBean("Hot Springs National Park", "Hot Springs", "AR", 34.52153D, -93.042267D));
        attractions.add(new AttractionBean("Kartchner Caverns State Park", "Benson", "AZ", 31.837551D, -110.347382D));
        attractions.add(new AttractionBean("Legend Valley", "Thornville", "OH", 39.937778D, -82.40667D));
        attractions.add(new AttractionBean("Flowers Bakery of London", "Flowers Bakery of London", "KY", 37.131527D, -84.07486D));
        attractions.add(new AttractionBean("McKinley Tower", "Anchorage", "AK", 61.218887D, -149.877502D));
        attractions.add(new AttractionBean("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
        attractions.add(new AttractionBean("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
        attractions.add(new AttractionBean("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
        attractions.add(new AttractionBean("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
        attractions.add(new AttractionBean("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
        attractions.add(new AttractionBean("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
        attractions.add(new AttractionBean("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
        attractions.add(new AttractionBean("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
        attractions.add(new AttractionBean("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
        attractions.add(new AttractionBean("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
        attractions.add(new AttractionBean("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
        attractions.add(new AttractionBean("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
        attractions.add(new AttractionBean("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
        attractions.add(new AttractionBean("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
        attractions.add(new AttractionBean("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
        attractions.add(new AttractionBean("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));
        return attractions;
    }
    
    private void sleep() {
        int random = ThreadLocalRandom.current().nextInt(30, 100);
        
        try {
            TimeUnit.MILLISECONDS.sleep((long) random);
        } catch (InterruptedException var3) {
        }
        
    }
    
    private void sleepLighter() {
        try {
            TimeUnit.MILLISECONDS.sleep(10L);
        } catch (InterruptedException var2) {
        }
        
    }
}

