CREATE TABLE Buyer (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       phone VARCHAR(50),
                       businessNumber VARCHAR(100)
);

CREATE TABLE Product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         modelCode VARCHAR(100),
                         unitPrice INT,
                         category VARCHAR(100)
);

CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(100),
                      password VARCHAR(255),
                      role VARCHAR(50), -- ADMIN or WORKER
                      email VARCHAR(255)
);

CREATE TABLE `Order` (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         buyer_id INT,
                         orderDate DATETIME,
                         status VARCHAR(50), -- REQUESTED, APPROVED, CANCELLED
                         FOREIGN KEY (buyer_id) REFERENCES Buyer(id)
);

CREATE TABLE OrderItem (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           order_id INT,
                           product_id INT,
                           quantity INT,
                           FOREIGN KEY (order_id) REFERENCES `Order`(id),
                           FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE TABLE Shipment (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          buyer_id INT,
                          shipmentCode VARCHAR(100),
                          shipmentDate DATETIME,
                          FOREIGN KEY (buyer_id) REFERENCES Buyer(id)
);

CREATE TABLE ShipmentItem (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              shipment_id INT,
                              product_id INT,
                              quantity INT,
                              FOREIGN KEY (shipment_id) REFERENCES Shipment(id),
                              FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE TABLE QrScanLog (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           shipment_item_id INT,
                           qrCode VARCHAR(255),
                           scanTime DATETIME,
                           user_id INT,
                           scanStatus VARCHAR(50), -- SUCCESS, DUPLICATE, NOT_REGISTERED, FAILED
                           FOREIGN KEY (shipment_item_id) REFERENCES ShipmentItem(id),
                           FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Payment (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         shipment_id INT,
                         amount INT,
                         method VARCHAR(50), -- CARD, BANK, POINT
                         status VARCHAR(50), -- PENDING, PAID, FAILED
                         paidAt DATETIME,
                         FOREIGN KEY (shipment_id) REFERENCES Shipment(id)
);
