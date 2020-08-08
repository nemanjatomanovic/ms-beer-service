DROP DATABASE IF EXISTS beer_service;

CREATE DATABASE beer_service;

CREATE ROLE beer_user WITH ENCRYPTED PASSWORD 'beerusesrpassword';

GRANT ALL PRIVILEGES ON DATABASE beer_service TO beer_user;