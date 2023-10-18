create TABLE password_reset_token(
  id bigserial    PRIMARY KEY,
  token    VARCHAR(255) not null,
  user_id BIGINT  not null,
  expiry_date TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user_details(user_id)
);