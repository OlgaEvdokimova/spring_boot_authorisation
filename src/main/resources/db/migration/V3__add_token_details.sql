--create TABLE token_details(
--  token_id bigserial    PRIMARY KEY,
--  user_id BIGINT  not null,
--  access_token    VARCHAR(255) not null,
--  refresh_token   VARCHAR(255) not null,
--  FOREIGN KEY (user_id) REFERENCES user_details(user_id) ON DELETE CASCADE
--);