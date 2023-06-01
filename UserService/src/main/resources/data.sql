DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO user (name, email)
VALUES
    ('John Doe', 'john@example.com'),
    ('Jane Smith', 'jane@example.com'),
    ('Alice Johnson', 'alice@example.com'),
    ('Bob Anderson', 'bob@example.com');
