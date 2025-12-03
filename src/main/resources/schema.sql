USE z;


CREATE TABLE IF NOT EXISTS users (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    password VARCHAR(255),
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(50),
    phone_number VARCHAR(10),
    create_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS posts (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER,
    title VARCHAR(100),
    content TEXT,

    FOREIGN KEY (user_id) REFERENCES users(id)
);