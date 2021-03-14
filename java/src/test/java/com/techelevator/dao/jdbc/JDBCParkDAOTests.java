package com.techelevator.dao.jdbc;

import com.techelevator.dao.ParkDAO;
import com.techelevator.model.Park;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JDBCParkDAOTests extends BaseDAOTests {

    private ParkDAO parkDAO;

    @Before
    public void setup() {
    	parkDAO = new JDBCParkDAO(dataSource);
    }

    @Test
    public void getParksTest_Should_ReturnAllParksInLocationAlphaOrder() {
        List<Park> parks = parkDAO.getAllParks();

        assertEquals(2, parks.size());
        assertEquals("Ohio", parks.get(0).getLocation());
        assertEquals("Pennsylvania", parks.get(1).getLocation());
    }

}
