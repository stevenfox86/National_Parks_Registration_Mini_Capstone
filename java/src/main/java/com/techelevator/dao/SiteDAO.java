package com.techelevator.dao;

import com.techelevator.model.Site;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

    public List<Site> getSitesThatAllowRVs(int parkId);
    
    public List<Site> getAvailableSites(int parkId);
    
    public List<Site> getAvailableSitesDateRange(int parkId, LocalDate fromDate, LocalDate toDate);
}
