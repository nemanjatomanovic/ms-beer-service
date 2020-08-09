DROP DATABASE IF EXISTS beer_service;

CREATE DATABASE beer_service;

CREATE ROLE beer_user WITH ENCRYPTED PASSWORD 'beerusesrpassword';

ALTER ROLE beer_user LOGIN;