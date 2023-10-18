CREATE SEQUENCE user_details_seq START WITH 2 INCREMENT BY 1;
CREATE TABLE user_details(
  user_id bigserial  PRIMARY KEY, --GENERATED ALWAYS AS IDENTITY,
  firstname VARCHAR(100) not null,
  lastname VARCHAR(100) not null,
  email VARCHAR(100) UNIQUE not null,
  password VARCHAR(100) not null,
  role_id BIGINT not null,
  FOREIGN KEY (role_id) REFERENCES user_role(role_id)
);