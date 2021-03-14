
-- GET ALL PARKS
SELECT park_id, name, location, establish_date, area, visitors, description
FROM park
ORDER BY location ASC;

--GET CAMPGROUNDS BY PARK ID
SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee
FROM campground
WHERE park_id = 3;

--GET SITES THAT ALLOW RV's
SELECT S.site_id, S.site_number, S.max_occupancy, S.accessible, S.max_rv_length, S.utilities
FROM site S
INNER JOIN campground C ON C.campground_id = S.campground_id
WHERE S.max_rv_length > 0 AND C.park_id = 3;


--CREATE RESERVATION
BEGIN TRANSACTION;

SELECT * FROM reservation;

INSERT INTO reservation (site_id, name, from_date, to_date)
VALUES(1, 'Kent Family', '2021-12-12', '2021-12-26');

SELECT * FROM reservation;

ROLLBACK;

SELECT reservation_id 
FROM reservation
WHERE site_id = 1 AND name = 'Smith Family Reservation' AND from_date = '2021-02-17' AND to_date = '2021-02-21';

--UPCOMING RESERVATION
SELECT R.reservation_id, R.site_id, R.name, R.from_date, R.to_date, R.create_date
FROM reservation R
INNER JOIN site S ON S.site_id = R.site_id
INNER JOIN campground C ON C.campground_id = S.campground_id
WHERE C.park_id = 3 AND R.from_date = CURRENT_DATE +29;


--CURRENTLY AVAILABLE SITES
SELECT S.site_id, S.campground_id, C.name, S.site_number, S.max_occupancy, S.accessible, S.max_rv_length, S.utilities
FROM site S
INNER JOIN campground C ON C.campground_id = S.campground_id
INNER JOIN park P ON P.park_id = C.park_id
INNER JOIN reservation R ON R.site_id = S.site_id
WHERE P.park_id = 3 AND S.site_id IS NOT null;

SELECT S.site_id, S.campground_id, C.name, S.site_number, S.max_occupancy, S.accessible, S.max_rv_length, S.utilities
FROM site S
INNER JOIN campground C ON C.campground_id = S.campground_id
INNER JOIN park P ON P.park_id = C.park_id
LEFT OUTER JOIN reservation R ON R.site_id = S.site_id
WHERE P.park_id = 1 AND S.site_id IS null;

--BONUS AVAILABLE FOR RSERVATION

--SELECT R.site_id, C.name
--FROM reservation R
--INNER JOIN campground C ON C.campground_id = S.campground_id
--INNER JOIN reservation R ON S.site_id = R.site_id
--INNER JOIN park P ON P.park_id = C.park_id
--WHERE P.park_id = 1 AND BETWEEN from_date = '2020-01-01' AND to_date = '2021-12-12';


SELECT S.site_id, S.campground_id, S.site_number, S.max_occupancy, S.accessible, S.max_rv_length, S.utilities
FROM site S
INNER JOIN campground C ON C.campground_id = S.campground_id
INNER JOIN park P ON P.park_id = C.park_id
LEFT OUTER JOIN reservation R ON S.site_id = R.site_id
WHERE P.park_id = 1 AND (R.site_id IS null OR
((from_date NOT BETWEEN ('2020-01-01') AND ('2020-01-01' )) OR
(to_date NOT BETWEEN ('2020-01-01') AND ('2020-01-01' )) OR
(from_date > '2020-01-01') AND (to_date < '2020-01-01' )) OR
(from_date < '2020-01-01') AND (to_date > '2020-01-01' ));