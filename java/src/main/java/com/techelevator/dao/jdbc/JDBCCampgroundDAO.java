package com.techelevator.dao.jdbc;

import com.techelevator.dao.CampgroundDAO;
import com.techelevator.model.Campground;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCCampgroundDAO implements CampgroundDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCCampgroundDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> getCampgroundsByParkId(int parkId) {
    	
    	List<Campground> campGroundList = new ArrayList<>();
    	
    	String sqlGetCampgroundsByParkId = "SELECT campground_id, park_id, name, open_from_mm, "
    								 	 + "open_to_mm, daily_fee "
    								 	 + "FROM campground "
    								 	 + "WHERE park_id = ?";
    	
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundsByParkId, parkId);
    	
    	while(results.next()) {
    		Campground theCampground = mapRowToCampground(results);
    		campGroundList.add(theCampground);
    	}
        return campGroundList;
    }

    private Campground mapRowToCampground(SqlRowSet results) {
        Campground camp = new Campground();
        camp.setCampgroundId(results.getInt("campground_id"));
        camp.setParkId(results.getInt("park_id"));
        camp.setName(results.getString("name"));
        camp.setOpenFromMonth(results.getInt("open_from_mm"));
        camp.setOpenToMonth(results.getInt("open_to_mm"));
        camp.setDailyFee(results.getDouble("daily_fee"));
        return camp;
    }
}
