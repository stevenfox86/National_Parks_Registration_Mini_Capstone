package com.techelevator.dao.jdbc;

import com.techelevator.dao.SiteDAO;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCSiteDAO implements SiteDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCSiteDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
    	
    	List<Site> siteList = new ArrayList<>();
    	
    	String sqlGetSitesThatAllowRVs = "SELECT S.site_id, S.campground_id, S.site_number, S.max_occupancy,"
    								   + " S.accessible, S.max_rv_length, S.utilities"
    								   + " FROM site S"
    								   + " INNER JOIN campground C ON C.campground_id = S.campground_id"
    								   + " WHERE S.max_rv_length > 0 AND C.park_id = ?;";
    	
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSitesThatAllowRVs, parkId);
    	
    	while(results.next()) {
    		Site theSite = mapRowToSite(results);
    		siteList.add(theSite);
    	}
    	
        return siteList;
    }

    @Override
	public List<Site> getAvailableSites(int parkId) {
		
    	List<Site> availableSitesList = new ArrayList<>();
    	
    	String sqlcurrentlyAvailableSites = "SELECT S.site_id, S.campground_id, C.name, S.site_number,"
    									  + " S.max_occupancy, S.accessible, S.max_rv_length, S.utilities"
    									  + " FROM site S"
    									  + " INNER JOIN campground C ON C.campground_id = S.campground_id"
    									  + " INNER JOIN park P ON P.park_id = C.park_id"
    									  + " LEFT OUTER JOIN reservation R ON S.site_id = R.site_id"
    									  + " WHERE P.park_id = ? AND R.site_id IS null;";
    	
    	
    	
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlcurrentlyAvailableSites, parkId);
    	
    	while(results.next()) {
    		Site theSite = mapRowToSite(results);
    		availableSitesList.add(theSite);
    	}
		return availableSitesList;
	}
    
	@Override
	public List<Site> getAvailableSitesDateRange(int parkId, LocalDate fromDate, LocalDate toDate) {
		
		List<Site> availableSitesList = new ArrayList<>();
		
		String sqlAvailableSitesDateRange = "SELECT DISTINCT S.site_id, S.campground_id, S.site_number,"
				  						  + " S.max_occupancy, S.accessible, S.max_rv_length, S.utilities"
				  						  + " FROM site S"
								  		  + " INNER JOIN campground C ON C.campground_id = S.campground_id"
								  		  + " INNER JOIN park P ON P.park_id = C.park_id"
								  		  + " LEFT OUTER JOIN reservation R ON S.site_id = R.site_id"
								  		  
								  		  + " WHERE P.park_id = ? AND "
								  		  + " R.site_id IS null OR"
								  		  + " (NOT (from_date < ?) AND NOT (to_date > ?)) OR"
								  		  + " (from_date NOT BETWEEN ? AND ?) OR"
								  		  + " (to_date NOT BETWEEN ? AND ?) OR"
								  		  + " (NOT (from_date > ?) AND NOT (to_date < ?))";
								  		  
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSitesDateRange, parkId, fromDate, toDate, fromDate, toDate, fromDate, toDate, fromDate, toDate);
		
		while (results.next()) {
			
			Site theSite = mapRowToSite(results);
			availableSitesList.add(theSite);
		}
		return availableSitesList;
	}
 
    
    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }

	
}
