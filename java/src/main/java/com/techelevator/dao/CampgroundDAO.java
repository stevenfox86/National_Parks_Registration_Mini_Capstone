package com.techelevator.dao;

import com.techelevator.model.Campground;

import java.util.List;

public interface CampgroundDAO {

    List<Campground> getCampgroundsByParkId(int parkId);
}
