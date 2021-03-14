package com.techelevator.dao.jdbc;

import com.techelevator.dao.CampgroundDAO;
import com.techelevator.model.Campground;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCCampgroundDAOTests extends BaseDAOTests {

    private CampgroundDAO campgroundDAO;

    @Before
    public void setup() {
    	campgroundDAO = new JDBCCampgroundDAO(dataSource);
    }

    @Test
    public void getCampgrounds_Should_ReturnAllCampgrounds() {
        List<Campground> campgrounds = campgroundDAO.getCampgroundsByParkId(99);

        assertEquals(2,campgrounds.size());
    }

}
