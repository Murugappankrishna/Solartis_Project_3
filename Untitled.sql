create database ShopBillingManagement;
show databases;
use ShopBillingManagement;
show tables;
CREATE TABLE user_Credentials (
     id INT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(50) NOT NULL,
     password VARCHAR(50) NOT NULL,
     role VARCHAR(50)	
);
INSERT INTO user_Credentials (username, password, role) VALUES
('Ramesh', '-938314596', 'SalesPerson'),
('Ragunath', '65654852', 'Admin'),
('Arun', '3003018', 'SalesPerson'),
('Siva', '3530625', 'Admin'),
('Harish', '-1224453307', 'SalesPerson'),
('Vijay', '112208527', 'SalesPerson');

-- Product Table
CREATE TABLE product_details (
     product_id INT AUTO_INCREMENT PRIMARY KEY,
     product_name VARCHAR(50) NOT NULL,
     product_des VARCHAR(50),
     cost_price INT,
     selling_price INT,
     Stock INT
);
ALTER TABLE product_details
ADD COLUMN profit INT AS (selling_price - cost_price);
ALTER TABLE product_details
DROP COLUMN profit;
Alter Table product_details ADD COLUMN Tax_Percent int;
INSERT INTO product_details (product_name, product_des, cost_price, selling_price, Stock) VALUES
('Soap', 'Personal Care', 20, 30, 100),
('Rice', 'Household Essentials', 50, 70, 200),
('Shampoo', 'Personal Care', 40, 60, 150),
('Biscuit', 'Snacks', 10, 15, 80),
('Cereal', 'Household Essentials', 30, 45, 120);
CREATE INDEX idx_product_id ON product_details(product_id);
-- User Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);
INSERT INTO users (username) VALUES
('Arjun'),
('Karthik'),
('Manoj'),
('Rajesh'),
('Suresh'),
('Ganesh'),
('Venkat'),
('Saravanan'),
('Prakash');
-- table cart
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES product_details(product_id)
);
ALTER TABLE cart
ADD COLUMN total_amount INT;



-- Trial quries
show tables;
describe user_credentials;
describe product_details;
describe cart;
select*from user_Credentials;
select * from product_details;
select*from users;
select*from cart;
select role from user_Credentials where username= 'Siva' AND password = '3530625';
INSERT INTO cart (user_id, product_id, quantity)
VALUES
(1,2 ,10);
INSERT INTO cart (user_id, product_id, quantity, total_amount)
VALUES
(1, 2, 10, (SELECT selling_price * 10 FROM product_details WHERE product_id = 2));

SELECT u.username, pd.product_name, c.quantity,c.total_amount
FROM cart c
JOIN users u ON c.user_id = u.user_id
JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 1;
DELETE FROM cart where cart_id=1;
UPDATE product_details SET TAX_Percent=10 WHERE product_id IN (1,3);
DELETE FROM cart where cart_id IN(5,6,7,8,9,13,35,36,37,38);

SELECT cart_id FROM cart WHERE user_id = 1;
DELETE cart FROM cart INNER JOIN (SELECT cart_id FROM cart WHERE user_id = 1) AS subquery ON cart.cart_id = subquery.cart_id;
SELECT cart_id FROM cart WHERE user_id = 2;
SELECT u.username, pd.product_name, c.quantity,c.total_amount FROM cart c JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 3;

SELECT 
    u.username, 
    pd.product_name, 
    c.quantity,
    c.total_amount,
    (c.total_amount * pd.Tax_Percent / 100) AS tax
FROM 
    cart c 
JOIN 
    users u ON c.user_id = u.user_id 
JOIN 
    product_details pd ON c.product_id = pd.product_id 
WHERE 
    u.user_id = 2;
SELECT u.username, pd.product_name, c.quantity, pd.selling_price AS price_before_tax, pd.tax_rate AS tax_percent, (pd.selling_price * c.quantity) AS total_price_before_tax, (pd.selling_price * pd.tax_rate / 100) AS tax_amount, (pd.selling_price + (pd.selling_price * pd.tax_rate / 100)) AS price_inclusive_of_tax FROM cart c JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 2;
    
-- 21:48:15	SELECT u.username, pd.product_name, c.quantity, pd.selling_price AS price_before_tax, pd.tax_rate AS tax_percent, (pd.selling_price * c.quantity) AS total_price_before_tax, (pd.selling_price * pd.tax_rate / 100) AS tax_amount, (pd.selling_price + (pd.selling_price * pd.tax_rate / 100)) AS price_inclusive_of_tax FROM cart c JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 2 LIMIT 0, 1000	Error Code: 1054. Unknown column 'pd.tax_rate' in 'field list'	0.051 sec

SELECT u.username, pd.product_name, c.quantity, pd.selling_price AS unit_price, pd.Tax_Percent AS tax_percent, (pd.selling_price * c.quantity) AS total_price_before_tax, (pd.selling_price * pd.Tax_Percent / 100) AS tax_amount, ((pd.selling_price * c.quantity) + (pd.selling_price * c.quantity * pd.Tax_Percent / 100)) AS price_inclusive_of_tax FROM cart c JOIN users u ON c.user_id = u.user_id JOIN product_details pd ON c.product_id = pd.product_id WHERE u.user_id = 1;
SELECT 
  u.username, 
  pd.product_name, 
  c.quantity, 
  pd.selling_price AS unit_price, 
  pd.Tax_Percent AS tax_percent, 
  (pd.selling_price * c.quantity) AS total_price_before_tax, 
  (
    pd.selling_price * pd.Tax_Percent / 100
  ) AS tax_amount, 
  (
     (
      pd.selling_price * c.quantity * pd.Tax_Percent / 100
    )
  ) AS price_inclusive_of_tax 
FROM 
  cart c 
  JOIN users u ON c.user_id = u.user_id 
  JOIN product_details pd ON c.product_id = pd.product_id 
WHERE 
  u.user_id = 2;

SELECT 
  u.username, 
  pd.product_name, 
  c.quantity, 
  pd.selling_price AS unit_price, 
  pd.Tax_Percent AS tax_percent, 
  (pd.selling_price * c.quantity) AS total_price_before_tax, 
  (
    (pd.selling_price * c.quantity)* pd.Tax_Percent / 100
  ) AS tax_amount, 
  (
    (pd.selling_price * c.quantity) + (
      pd.selling_price * c.quantity * pd.Tax_Percent / 100
    )
  ) AS price_inclusive_of_tax 
FROM 
  cart c 
  JOIN users u ON c.user_id = u.user_id 
  JOIN product_details pd ON c.product_id = pd.product_id 
WHERE 
  u.user_id = 2;
--  