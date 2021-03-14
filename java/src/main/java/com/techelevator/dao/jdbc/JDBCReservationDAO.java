package com.techelevator.dao.jdbc;

import com.techelevator.dao.ReservationDAO;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCReservationDAO implements ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCReservationDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {
    	
    	String sqlCreateReservation = "INSERT INTO reservation (site_id, name, from_date, to_date)"
    							 	+ " VALUES(?, ?, ?, ?) RETURNING reservation_id ;";
    	
//    	jdbcTemplate.update(sqlCreateReservation, );
    	int reservationID = jdbcTemplate.queryForObject(sqlCreateReservation, int.class, siteId, name, fromDate, toDate);
    	
    	
        return reservationID;
    }
    
    
    @Override
    public List<Reservation> upcomingReservations(int parkId) {
    	
    	List<Reservation> reservationList = new ArrayList<>();
    	
    	String sqlUpcomingReservations = "SELECT R.reservation_id, R.site_id, R.name, R.from_date,"
    								   + " R.to_date, R.create_date"
    								   + " FROM reservation R"
    								   + " INNER JOIN site S ON S.site_id = R.site_id"
    								   + " INNER JOIN campground C ON C.campground_id = S.campground_id"
    								   + " WHERE C.park_id = ? AND R.from_date = CURRENT_DATE +29;";
		
    	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlUpcomingReservations, parkId);
    	
    	while (results.next()) {
    		
    		Reservation theReservation = mapRowToReservation(results);
    		reservationList.add(theReservation);
    	}
    	return reservationList;
    }
     

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation r = new Reservation();
        r.setReservationId(results.getInt("reservation_id"));
        r.setSiteId(results.getInt("site_id"));
        r.setName(results.getString("name"));
        r.setFromDate(results.getDate("from_date").toLocalDate());
        r.setToDate(results.getDate("to_date").toLocalDate());
        r.setCreateDate(results.getDate("create_date").toLocalDate());
        return r;
    }






}
