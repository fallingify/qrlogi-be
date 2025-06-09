CREATE TABLE buyers (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       phone VARCHAR(50),
                       business_num VARCHAR(100)
);

CREATE TABLE products (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         model_code VARCHAR(100),
                         unit_price INT,
                         category VARCHAR(100)
);

CREATE TABLE users (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(100),
                      password VARCHAR(255),
                      role VARCHAR(50),
                      email VARCHAR(255)
);

CREATE TABLE orders (
                        id CHAR(36) PRIMARY KEY,
                        order_number INT NOT NULL AUTO_INCREMENT UNIQUE,
                        buyer_id INT,
                        order_date DATETIME,
                        order_status VARCHAR(50),
                        FOREIGN KEY (buyer_id) REFERENCES buyers(id)
);

CREATE TABLE order_items (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           order_id CHAR(36),
                           product_id INT,
                           qty INT,
                           FOREIGN KEY (order_id) REFERENCES orders(id),
                           FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE shipments (
                          id CHAR(36) PRIMARY KEY,
                          shipment_num INT NOT NULL AUTO_INCREMENT UNIQUE,
                          buyer_id INT,
                          shipment_code VARCHAR(100),
                          shipment_date DATETIME,
                          FOREIGN KEY (buyer_id) REFERENCES buyers(id)
);

CREATE TABLE shipment_items (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              shipment_id CHAR(36),
                              product_id INT,
                                qty INT,
                              FOREIGN KEY (shipment_id) REFERENCES shipments(id),
                              FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE qr_scan_log (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           shipment_item_id INT,
                           qr_code VARCHAR(255),
                           scan_time DATETIME,
                           user_id INT,
                           scan_status VARCHAR(50),
                           FOREIGN KEY (shipment_item_id) REFERENCES shipment_items(id),
                           FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE payments (
                         id CHAR(36) PRIMARY KEY,
                         payment_num INT NOT NULL AUTO_INCREMENT UNIQUE,
                         shipment_id CHAR(36),
                         amount INT,
                         payments_method VARCHAR(50),
                         payments_status VARCHAR(50),
                         paid_date DATETIME,
                         FOREIGN KEY (shipment_id) REFERENCES shipments(id)
);
