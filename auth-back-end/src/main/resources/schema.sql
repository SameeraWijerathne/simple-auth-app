CREATE TABLE user (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(50) NOT NULL,
  full_name VARCHAR(50) NOT NULL
);

CREATE TABLE customer (
  id INT AUTO_INCREMENT PRIMARY KEY,
  address VARCHAR(150) NOT NULL,
  name VARCHAR(75) NOT NULL
);