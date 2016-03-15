--Run as postgres when creating the schema
--Run and tested against Postgresql-9.4
drop schema if exists StruckTODB; 
create schema StruckTODB;

--Modify password with your current pwd
drop user if exists StruckTODBUser; 
create user StruckTODBUser password '';
grant all on schema StruckTODB to StruckTODBUser;
grant all on all tables in schema StruckTODB to StruckTODBUser;

--Create Schema


