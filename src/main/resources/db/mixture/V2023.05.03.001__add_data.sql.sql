-- insert statements for Table 1: customers
INSERT INTO customers (customer_id, customer_name, contact_name, country) VALUES
(1, 'Customer 1', 'John Smith', 'USA'),
(2, 'Customer 2', 'Jane Doe', 'Canada'),
(3, 'Customer 3', 'Miguel Rodriguez', 'Mexico');

-- insert statements for Table 2: suppliers
INSERT INTO suppliers (supplier_id, supplier_name, contact_name, address, city, region, postal_code, country, phone, email) VALUES
(1, 'Supplier 1', 'Sam Johnson', '123 Main St', 'Chicago', 'IL', '60601', 'USA', '555-1234', 'sam@supplier1.com'),
(2, 'Supplier 2', 'Maria Gomez', '456 Oak Ave', 'Toronto', 'Ontario', 'M6G 1H3', 'Canada', '555-5678', 'maria@supplier2.com'),
(3, 'Supplier 3', 'Juan Perez', '789 Elm St', 'Mexico City', 'Mexico', '01010', 'Mexico', '555-9012', 'juan@supplier3.com');

-- insert statements for Table 3: categories
INSERT INTO categories (category_id, category_name, description) VALUES
(1, 'Category 1', 'Description for Category 1'),
(2, 'Category 2', 'Description for Category 2'),
(3, 'Category 3', 'Description for Category 3');