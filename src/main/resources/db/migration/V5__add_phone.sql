CREATE TABLE phone(
  phone_id bigserial PRIMARY KEY,
  phone_number VARCHAR(13) UNIQUE not null,
  user_id BIGINT not null,
  FOREIGN KEY (user_id) references user_details(user_id)
);