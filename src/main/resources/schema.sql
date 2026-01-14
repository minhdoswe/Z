USE z;

DROP TABLE IF EXISTS posts;

DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS refresh_tokens;DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE ,
    phone_number VARCHAR(10) UNIQUE ,
    role ENUM('USER') NOT NULL,
    create_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT 0,
    deleted_at TIMESTAMP,
    visibility ENUM('PRIVATE', 'PUBLIC') NOT NULL DEFAULT 'PUBLIC'
);

CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(100),
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT 0,
    deleted_at TIMESTAMP,
    visibility ENUM('PRIVATE', 'PUBLIC') NOT NULL,


    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (target_id) REFERENCES users(id)
);

CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(256) NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    is_revoked BOOLEAN NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id)
);