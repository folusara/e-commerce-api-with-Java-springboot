CREATE TABLE cart_products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    price DOUBLE,
    stock_quantity BIGINT,
    sub_total DOUBLE,
    product_name VARCHAR(255),
    product_description TEXT,
    image_url VARCHAR(255),
    cart_id BIGINT,
    CONSTRAINT fk_cart
        FOREIGN KEY (cart_id) 
        REFERENCES carts(id)
        ON DELETE CASCADE
);
