USE z;

DROP TABLE IF EXISTS comment_votes;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(21) NOT NULL UNIQUE ,
    password VARCHAR(256) NOT NULL,
    first_name VARCHAR(21) NOT NULL,
    last_name VARCHAR(21) NOT NULL,
    email VARCHAR(51) UNIQUE ,
    phone_number VARCHAR(11) UNIQUE ,
    role ENUM('USER') NOT NULL,
    create_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT 1,
    deleted_at TIMESTAMP,
    visibility ENUM('PRIVATE', 'PUBLIC') NOT NULL DEFAULT 'PUBLIC'
);

CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(101),
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT 0,
    deleted_at TIMESTAMP,
    visibility ENUM('PRIVATE', 'PUBLIC') NOT NULL,
    upvote_count BIGINT NOT NULL,
    downvote_count BIGINT NOT NULL,
    vote_score BIGINT NOT NULL
);

CREATE TABLE follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (target_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT uk_follower_target UNIQUE (follower_id, target_id)
);

CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(257) NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    is_revoked BOOLEAN NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    vote_status enum('UPVOTE', 'DOWNVOTE') NOT NULL,
    created_at TIMESTAMP NOT NULL,

    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT uk_post_user UNIQUE (post_id, user_id)
);

CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    deleted BOOLEAN NOT NULL,
    deleted_at TIMESTAMP,
    upvote_count BIGINT,
    downvote_count BIGINT,
    vote_score BIGINT,

    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE
);

CREATE TABLE comment_votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    comment_vote_status enum('UPVOTE', 'DOWNVOTE') NOT NULL,

    FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT uk_comment_user UNIQUE (comment_id, user_id)
)

