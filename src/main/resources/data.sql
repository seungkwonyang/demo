-- categories 초기 데이터 삽입
INSERT INTO categories (code, name) VALUES
('TOP', '상의'),
('OUTER', '아우터'),
('PANTS', '바지'),
('SNEAKERS', '스니커즈'),
('BAG', '가방'),
('HAT', '모자'),
('SOCKS', '양말'),
('ACCESSORY', '액세서리');

-- brands 초기 데이터 삽입
INSERT INTO brands (name) VALUES ('A');
INSERT INTO brands (name) VALUES ('B');
INSERT INTO brands (name) VALUES ('C');
INSERT INTO brands (name) VALUES ('D');
INSERT INTO brands (name) VALUES ('E');
INSERT INTO brands (name) VALUES ('F');
INSERT INTO brands (name) VALUES ('G');
INSERT INTO brands (name) VALUES ('H');
INSERT INTO brands (name) VALUES ('I');

-- products 초기 데이터 삽입
-- 브랜드 A
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'TOP', 11200);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'OUTER', 5500);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'PANTS', 4200);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'SNEAKERS', 9000);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'BAG', 2000);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'HAT', 1700);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'SOCKS', 1800);
INSERT INTO products (brand_id, category_code, price) VALUES (1, 'ACCESSORY', 2300);

-- 브랜드 B
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'TOP', 10500);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'OUTER', 5900);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'PANTS', 3800);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'SNEAKERS', 9100);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'BAG', 2100);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'HAT', 2000);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'SOCKS', 2000);
INSERT INTO products (brand_id, category_code, price) VALUES (2, 'ACCESSORY', 2200);

-- 브랜드 C
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'TOP', 10000);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'OUTER', 6200);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'PANTS', 3300);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'SNEAKERS', 9200);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'BAG', 2200);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'HAT', 1900);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'SOCKS', 2200);
INSERT INTO products (brand_id, category_code, price) VALUES (3, 'ACCESSORY', 2100);

-- 브랜드 D
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'TOP', 10100);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'OUTER', 5100);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'PANTS', 3000);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'SNEAKERS', 9500);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'BAG', 2500);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'HAT', 1500);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'SOCKS', 2400);
INSERT INTO products (brand_id, category_code, price) VALUES (4, 'ACCESSORY', 2000);

-- 브랜드 E
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'TOP', 10700);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'OUTER', 5000);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'PANTS', 3800);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'SNEAKERS', 9900);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'BAG', 2300);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'HAT', 1800);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'SOCKS', 2100);
INSERT INTO products (brand_id, category_code, price) VALUES (5, 'ACCESSORY', 2100);

-- 브랜드 F
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'TOP', 11200);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'OUTER', 7200);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'PANTS', 4000);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'SNEAKERS', 9300);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'BAG', 2100);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'HAT', 1600);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'SOCKS', 2300);
INSERT INTO products (brand_id, category_code, price) VALUES (6, 'ACCESSORY', 1900);

-- 브랜드 G
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'TOP', 10500);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'OUTER', 5800);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'PANTS', 3900);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'SNEAKERS', 9000);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'BAG', 2200);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'HAT', 1700);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'SOCKS', 2100);
INSERT INTO products (brand_id, category_code, price) VALUES (7, 'ACCESSORY', 2000);

-- 브랜드 H
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'TOP', 10800);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'OUTER', 6300);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'PANTS', 3100);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'SNEAKERS', 9700);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'BAG', 2100);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'HAT', 1600);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'SOCKS', 2000);
INSERT INTO products (brand_id, category_code, price) VALUES (8, 'ACCESSORY', 2000);

-- 브랜드 I
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'TOP', 11400);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'OUTER', 6700);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'PANTS', 3200);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'SNEAKERS', 9500);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'BAG', 2400);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'HAT', 1700);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'SOCKS', 1700);
INSERT INTO products (brand_id, category_code, price) VALUES (9, 'ACCESSORY', 2400);
