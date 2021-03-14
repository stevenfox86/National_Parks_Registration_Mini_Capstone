package com.techelevator.dao.jdbc;

import com.techelevator.dao.ReservationDAO;
import com.techelevator.model.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCReservationDAOTests extends BaseDAOTests {

    private ReservationDAO reservationDAO;

    @Before
    public void setup() {
    	reservationDAO = new JDBCReservationDAO(dataSource);
    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int reservationCreated = reservationDAO.createReservation(9999,
                "TEST NAME",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));
        
        String sqlString = "SELECT reservation_id FROM reservation WHERE name = 'TEST NAME' AND site_id = 9999";
        
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlString);
        
        int container = 0;
        
        if(results.next())
        	container = results.getInt("reservation_id");

        assertEquals(reservationCreated, container);
    }

}
