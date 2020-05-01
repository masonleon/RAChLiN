-- # Login to your MySQL client and execute the following to setup a schema
DROP SCHEMA IF EXISTS rachlin;
CREATE DATABASE rachlin;
DROP USER IF EXISTS 'rachlin_usr'@'localhost';
CREATE USER 'rachlin_usr'@'localhost' IDENTIFIED BY '<your password>';
GRANT ALL PRIVILEGES ON rachlin.* TO 'rachlin_usr'@'localhost';
FLUSH PRIVILEGES;