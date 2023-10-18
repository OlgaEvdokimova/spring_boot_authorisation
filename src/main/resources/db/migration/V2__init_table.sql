--create sequence hibernate_sequence start 1 increment 1;
CREATE TABLE user_role
 (
    role_id          bigserial PRIMARY KEY,
    role_name        VARCHAR(25) not null
);