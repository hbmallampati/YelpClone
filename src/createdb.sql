CREATE TABLE BUSINESS(
                         BUSINESS_ID VARCHAR(22) PRIMARY KEY,
                         BUSINESS_NAME  VARCHAR(100) NOT NULL,
                         OPEN VARCHAR(5),
                         CITY VARCHAR(25)NOT NULL,
                         STATE VARCHAR(50) NOT NULL,
                         REVIEW_COUNT INTEGER,
                         STARS DOUBLE
);

CREATE TABLE BUSINESS_HOURS(
                               BUSINESS_ID VARCHAR(22) NOT NULL,
                               BUSINESS_DAY VARCHAR(25),
                               BUSINESS_OPEN_TIME TIME,
                               BUSINESS_CLOSE_TIME TIME,
                               PRIMARY KEY (BUSINESS_ID , BUSINESS_DAY),
                               FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS(BUSINESS_ID)
);

CREATE TABLE BUSINESS_CATEGORY(
                                  BUSINESS_ID VARCHAR(22) NOT NULL,
                                  BUSINESS_CATEGORY_NAME VARCHAR(100) NOT NULL,
                                  PRIMARY KEY(BUSINESS_ID, BUSINESS_CATEGORY_NAME),
                                  FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS(BUSINESS_ID)
);

CREATE TABLE BUSINESS_ATTRIBUTES(
                                    BUSINESS_ID VARCHAR(22) NOT NULL,
                                    B_ATTRIBUTE VARCHAR(150) NOT NULL,
                                    PRIMARY KEY (BUSINESS_ID, B_ATTRIBUTE),
                                    FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS(BUSINESS_ID)
);

CREATE TABLE BUSINESS_MAIN_CATEGORIES (
                                    CATEGORY_NAME VARCHAR(100) PRIMARY KEY
);

CREATE TABLE YELP_USERS(
                           USER_ID VARCHAR(22) PRIMARY KEY,
                           USER_NAME VARCHAR(50) NOT NULL,
                           YELPING_SINCE VARCHAR(7) NOT NULL,
                           REVIEW_COUNT INTEGER NOT NULL,
                           USEFUL_VOTES INTEGER,
                           FUNNY_VOTES INTEGER,
                           COOL_VOTES INTEGER,
                           NO_OF_VOTES INTEGER NOT NULL,
                           NO_OF_FRIENDS INTEGER NOT NULL,
                           AVERAGE_STARS DOUBLE NOT NULL
);

CREATE TABLE REVIEWS(
                        USER_ID VARCHAR(22) NOT NULL,
                        REVIEW_ID VARCHAR(22) PRIMARY KEY,
                        STARS INTEGER NOT NULL,
                        DATEE DATE NOT NULL,
                        REVIEW_TEXT VARCHAR(5000) NOT NULL,
                        BUSINESS_ID VARCHAR (22) NOT NULL,
                        USEFUL_VOTES INTEGER NOT NULL,
                        FUNNY_VOTES INTEGER NOT NULL,
                        COOL_VOTES INTEGER NOT NULL,
                        TOTAL_VOTES INTEGER NOT NULL,
                        FOREIGN KEY (USER_ID) REFERENCES YELP_USERS(USER_ID),
                        FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS(BUSINESS_ID)
);

ALTER TABLE BUSINESS_ATTRIBUTES ADD INDEX (B_ATTRIBUTE);
ALTER TABLE BUSINESS_CATEGORY ADD INDEX (BUSINESS_CATEGORY_NAME);
ALTER TABLE REVIEWS ADD INDEX (STARS);
ALTER TABLE REVIEWS ADD INDEX (TOTAL_VOTES);
ALTER TABLE REVIEWS ADD INDEX (DATEE);
ALTER TABLE YELP_USERS ADD INDEX (YELPING_SINCE);
ALTER TABLE YELP_USERS ADD INDEX (REVIEW_COUNT);
ALTER TABLE YELP_USERS ADD INDEX (NO_OF_FRIENDS);
ALTER TABLE YELP_USERS ADD INDEX (AVERAGE_STARS);
ALTER TABLE YELP_USERS ADD INDEX (NO_OF_VOTES);