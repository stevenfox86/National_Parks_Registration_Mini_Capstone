package com.techelevator.dao.jdbc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class BaseDAOTests {

    static SingleConnectionDataSource dataSource;
    protected static JdbcTemplate jdbcTemplate;
    
    @BeforeClass
    public static void setupDataSource() throws SQLException, IOException, FileNotFoundException {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        /* The following line disables autocommit for connections
         * returned by this DataSource. This allows us to rollback
         * any changes after each test */
        dataSource.setAutoCommit(false);
    }
    
    @Before
    public void loadTestData() throws IOException {
        // load test data
        File scriptFile = new File(BaseDAOTests.class.getClassLoader().getResource("test-data.sql").getFile());
        Scanner scriptInput = new Scanner(scriptFile);
        
        String testDataSQL = "";
        while (scriptInput.hasNext()) {
        	testDataSQL += scriptInput.nextLine();
        }
        scriptInput.close();
        
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(testDataSQL);
    }
    
    /* After all tests have finished running, this method will close the DataSource */
    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    /* After each test, we rollback any changes that were made to the database so that
     * everything is clean for the next test */
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
}
