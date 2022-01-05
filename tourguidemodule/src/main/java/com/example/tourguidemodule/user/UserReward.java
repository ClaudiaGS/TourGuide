package com.example.tourguidemodule.user;

import com.example.tourguidemodule.beans.AttractionBean;
import com.example.tourguidemodule.beans.VisitedLocationBean;

public class UserReward {

	public final VisitedLocationBean visitedLocationBean;
	public final AttractionBean attractionBean;
	private int rewardPoints;
	public UserReward(VisitedLocationBean visitedLocationBean, AttractionBean attractionBean, int rewardPoints) {
		this.visitedLocationBean = visitedLocationBean;
		this.attractionBean = attractionBean;
		this.rewardPoints = rewardPoints;
	}
	
	public UserReward(VisitedLocationBean visitedLocationBean, AttractionBean attractionBean) {
		this.visitedLocationBean = visitedLocationBean;
		this.attractionBean = attractionBean;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	public int getRewardPoints() {
		return rewardPoints;
	}
	
}
