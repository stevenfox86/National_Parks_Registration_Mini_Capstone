/* Clean out the data first */
DELETE FROM reservation;
DELETE FROM site;
DELETE FROM campground;
DELETE FROM park;


/* test parks */
INSERT INTO park (park_id, name, location, establish_date, area, visitors, description)
VALUES (98, 'Park 1', 'Pennsylvania', '1/1/1970', 1024, 512, 'Test description 1');

INSERT INTO park (park_id, name, location, establish_date, area, visitors, description)
VALUES (99, 'Park 2', 'Ohio', '1/1/1970', 2048, 1024, 'Test description 2');


/* test campgrounds */
INSERT INTO campground(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee)
VALUES (998, 99, 'Test Campground 1', '1', '12', 35);

INSERT INTO campground(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee)
VALUES (999, 99, 'Test Campground 2', '1', '12', 35);


/* test sites */
/**** accepts RVs */
INSERT INTO site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)
VALUES (9997, 999, 1, 10, true, 33, true);

INSERT INTO site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)
VALUES (9998, 999, 2, 10, true, 30, true);

/**** doesn't accept RVs */
INSERT INTO site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)
VALUES (9999, 999, 3, 10, true, 0, true);


/* test reservations */
/**** future */
INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date)
VALUES (1237, 9999, 'Test Testerson', CURRENT_DATE + 1, CURRENT_DATE + 5, CURRENT_DATE - 23);

INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date)
VALUES (1236, 9999, 'Bob Robertson', CURRENT_DATE + 11, CURRENT_DATE + 18, CURRENT_DATE - 23);

/**** present */
INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date)
VALUES (1235, 9999, 'Manager Managerson', CURRENT_DATE - 5, CURRENT_DATE + 2, CURRENT_DATE - 23);

/**** past */
INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date)
VALUES (1234, 9999, 'Leonard Leonardson', CURRENT_DATE - 11, CURRENT_DATE - 18, CURRENT_DATE - 23);

