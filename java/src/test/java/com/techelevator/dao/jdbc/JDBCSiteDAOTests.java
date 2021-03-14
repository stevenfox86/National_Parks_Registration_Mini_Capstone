package com.techelevator.dao.jdbc;

import com.techelevator.dao.SiteDAO;
import com.techelevator.model.Site;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCSiteDAOTests extends BaseDAOTests {

    private SiteDAO siteDAO;

    @Before
    public void setup() {
    	siteDAO = new JDBCSiteDAO(dataSource);
    }

    @Test
    public void getSitesThatAllowRVs_Should_ReturnSites() {
        List<Site> sites = siteDAO.getSitesThatAllowRVs(99);

        assertEquals(2,sites.size());
    }

    @Test
    public void getAvailableSites_Should_ReturnSites() {

    	List<Site> sites = siteDAO.getAvailableSites(99);

        assertEquals(2,sites.size());
    	
    }

    @Test
    public void getAvailableSitesDateRange_Should_ReturnSites() {


    	List<Site> sites = siteDAO.getAvailableSitesDateRange(99, 
    			LocalDate.now().plusDays((long) 3), LocalDate.now().plusDays((long) 5));

        assertEquals(2,sites.size());
    	
    	
    }
}
