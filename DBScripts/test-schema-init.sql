--Create Schema
--Create Location
create schema strucktodb authorization dba;

CREATE TABLE strucktodb."LOCATION"
(
   "LOCATION_ID" INTEGER NOT NULL PRIMARY KEY, 
   "LOCATION_LAT" real NOT NULL, 
   "LOCATION_LONG" real NOT NULL, 
   "LOCATION_CITY" character varying, 
   "LOCATION_PROV" character varying, 
   "LOCATION_COUNTRY" character varying, 
   "LOCATION_CREATE_DATE" date NOT NULL, 
   "LOCATION_USER" character varying
);

--added to ensure we keep locations unique
alter table strucktodb."LOCATION" ADD CONSTRAINT pk_incident UNIQUE ("LOCATION_LAT","LOCATION_LONG");

--create tweet capture
CREATE TABLE strucktodb."TWEET"
(
  "TWEET_ID" bigint NOT NULL PRIMARY KEY,
  "TWEET_URL" character varying,
  "TWEET_ACCOUNT" character varying,
  "TWEET_TIMESTAMP" date);

--Create Incident
CREATE TABLE strucktodb."INCIDENT"
(
  "INCIDENT_ID" INTEGER NOT NULL,
  "INCIDENT_TWEET_ID" bigint,
  "INCIDENT_SEVERITY" character varying,
  "INCIDENT_NEWS_URL" character varying,
  "INCIDENT_CREATE_DATE" date NOT NULL,
  "INCIDENT_ACTIVITY_DATE" date NOT NULL,
  "INCIDENT_LOCATION_ID" integer,
  "INCIDENT_VERIFIED" character varying,
  CONSTRAINT "INCIDENT_pkey" PRIMARY KEY ("INCIDENT_ID"),
  CONSTRAINT "FK_LOCATION" FOREIGN KEY ("INCIDENT_LOCATION_ID")
      REFERENCES strucktodb."LOCATION" ("LOCATION_ID"),
  CONSTRAINT "FK_TWEET" FOREIGN KEY ("INCIDENT_TWEET_ID")
      REFERENCES strucktodb."TWEET" ("TWEET_ID") 
);

--create interaction table
CREATE TABLE strucktodb."INTERACTION"
(
  "INTERACTION_TWEET_ID" bigint NOT NULL PRIMARY KEY,
  "INTERACTION_USER" character varying,
  "INTERACTION_PROCESSED" character varying,
  "INTERACTION_RESULT" character varying,
  "INTERACTION_CREATE_DATE" date NOT NULL,
  "INTERACTION_ACTIVITY_DATE" date NOT NULL
);