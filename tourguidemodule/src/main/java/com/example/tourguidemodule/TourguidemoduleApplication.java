package com.example.tourguidemodule;

import com.example.tourguidemodule.beans.GpsUtilBean;
import com.example.tourguidemodule.beans.RewardCentralBean;
import com.example.tourguidemodule.beans.TripPricerBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("com.example.tourguidemodule")
public class TourguidemoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourguidemoduleApplication.class, args);
	}

    @Bean
	public GpsUtilBean getGpsUtil() {
		return new GpsUtilBean();
	}

	@Bean
	public RewardCentralBean getRewardCentral() {
		return new RewardCentralBean();
	}

	@Bean
	public TripPricerBean getTripPricer() {
		return new TripPricerBean();
	}
}
