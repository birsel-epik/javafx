-- Database Create
CREATE DATABASE user_management;

-- Oluşturulmuş database seçmek
USE user_management;

-- user adında bir tablo oluştur
CREATE TABLE  IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- ###################################################################################################################

-- Insert
INSERT INTO users(username,password,email) VALUES ("birsel","123456","birsel.epik@gmail.com");

-- Select
select * FROM users;

-- Find User
SELECT  *  FROM users WHERE username="birsel" AND "123456";

-- Update
UPDATE users SET username="birsel4423", password="1234564423", email="birsel.epik12@gmail.com" WHERE id=1;

-- delete
DELETE FROM users  WHERE id=1;
