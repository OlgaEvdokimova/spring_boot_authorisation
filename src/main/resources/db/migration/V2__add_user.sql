CREATE SEQUENCE user_details_seq START WITH 2 INCREMENT BY 1;
--DROP TYPE public."u_role" CASCADE;
CREATE TYPE u_role AS ENUM ('USER', 'ADMIN');
CREATE CAST (varchar AS u_role) WITH INOUT AS IMPLICIT;
CREATE TABLE user_details(
  user_id bigserial  PRIMARY KEY,
  firstname VARCHAR(100) not null,
  lastname VARCHAR(100) not null,
  email VARCHAR(100) UNIQUE not null,
  password VARCHAR(100) not null,
  phones text[],
  user_role u_role not null
);