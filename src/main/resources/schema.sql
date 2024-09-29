CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movies
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    liked_count    BIGINT       NOT NULL DEFAULT 0,
    disliked_count BIGINT       NOT NULL DEFAULT 0,
    added_by       BIGINT       NOT NULL,
    creation_date  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (added_by)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reactions
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id       BIGINT NOT NULL,
    movie_id      BIGINT NOT NULL,
    reaction_type VARCHAR(10) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,
    FOREIGN KEY (movie_id)
        REFERENCES movies (id)
        ON DELETE CASCADE
);


