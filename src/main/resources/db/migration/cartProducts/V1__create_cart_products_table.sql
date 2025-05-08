CREATE TABLE cart_products (
    id BIGSERIAL PRIMARY KEY,
    price DOUBLE PRECISION,
    stock_quantity BIGINT,
    sub_total DOUBLE PRECISION,
    product_name VARCHAR(255),
    product_description VARCHAR(255),
    image_url VARCHAR(255)
);
