-- this script will reset the database back to an empty state
USE malbec;

DROP TABLE IF EXISTS repositories;
DROP TABLE IF EXISTS keystore;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS installations;

DROP TABLE IF EXISTS DATABASECHANGELOGLOCK;
DROP TABLE IF EXISTS DATABASECHANGELOG;