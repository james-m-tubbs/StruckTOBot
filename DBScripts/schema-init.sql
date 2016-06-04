--Run as postgres when creating the schema
--Run and tested against Postgresql-9.4
drop schema if exists StruckTODB; 
create schema StruckTODB;

--Modify password with your current pwd
drop user if exists StruckTODBUser; 
create user StruckTODBUser password '';
grant all on schema StruckTODB to StruckTODBUser;
grant all on all tables in schema StruckTODB to StruckTODBUser;

--Clean old schema
DROP TABLE strucktodb."INCIDENT";
DROP TABLE strucktodb."LOCATION";

--Create Schema
--Create Location
CREATE TABLE strucktodb."LOCATION"
(
   "LOCATION_ID" SERIAL NOT NULL PRIMARY KEY, 
   "LOCATION_LAT" real NOT NULL, 
   "LOCATION_LONG" real NOT NULL, 
   "LOCATION_CITY" character varying, 
   "LOCATION_PROV" character varying, 
   "LOCATION_COUNTRY" character varying, 
   "LOCATION_CREATE_DATE" date NOT NULL, 
   "LOCATION_USER" character varying
) 
WITH (
  OIDS = FALSE
)

TABLESPACE pg_default;

--Create Incident

CREATE TABLE strucktodb."INCIDENT"
(
  "INCIDENT_ID" serial NOT NULL,
  "INCIDENT_TWEET" character varying,
  "INCIDENT_TWEET_TEXT" character varying,
  "INCIDENT_SEVERITY" character varying,
  "INCIDENT_NEWS_URL" character varying,
  "INCIDENT_CREATE_DATE" date NOT NULL,
  "INCIDENT_ACTIVITY_DATE" date NOT NULL,
  "INDICDENT_LOCATION_ID" integer,
  CONSTRAINT "INCIDENT_pkey" PRIMARY KEY ("INCIDENT_ID"),
  CONSTRAINT "FK_LOCATION" FOREIGN KEY ("INDICDENT_LOCATION_ID")
      REFERENCES strucktodb."LOCATION" ("LOCATION_ID") MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
)
TABLESPACE pg_default;

