package com.techelevator.dao.jdbc;

import com.techelevator.dao.ParkDAO;
import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCParkDAO implements ParkDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCParkDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Park> getAllParks() {
    	
    	List<Park> parksList = new ArrayList<>();
    	
    	String sqlGetAllParks = "SELECT park_id, name, location, establish_date, area, " +
    							"visitors, description " +
    							"FROM park " +
    							"ORDER BY location ASC; ";
    	
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
    	
    	while(results.next()) {
    		Park thePark = mapRowToPark(results);
    		parksList.add(thePark);
    	}
        return parksList;
    }

    private Park mapRowToPark(SqlRowSet results) {
        Park park = new Park();
        park.setParkId(results.getInt("park_id"));
        park.setName(results.getString("name"));
        park.setLocation(results.getString("location"));
        park.setEstablishDate(results.getDate("establish_date").toLocalDate());
        park.setArea(results.getInt("area"));
        park.setVisitors(results.getInt("visitors"));
        park.setDescription(results.getString("description"));
        return park;
    }

}
