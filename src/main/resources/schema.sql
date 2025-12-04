USE z;

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE ,
    phone_number VARCHAR(10) UNIQUE ,
    create_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP
);

CREATE TABLE posts (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER,
    title VARCHAR(100),
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);