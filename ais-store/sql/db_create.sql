-- Database: ais_decode_message_store

DROP DATABASE IF EXISTS "rachlin";

CREATE DATABASE "rachlin"
    WITH
    OWNER = rachlin_usr
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- GRANT ALL PRIVILEGES ON rachlin.* TO 'rachlin_usr'@'localhost';


-- FLUSH PRIVILEGES;