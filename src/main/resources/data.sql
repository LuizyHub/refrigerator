-- RWD: Read, Write, Delete 모두 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'RWD', true, true, true
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'RWD');

-- RW: Read와 Write만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'RW', true, true, false
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'RW');

-- WR: Write와 Read만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'WR', true, true, false
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'WR');

-- RD: Read와 Delete만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'RD', true, false, true
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'RD');

-- R: Read만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'R', true, false, false
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'R');

-- W: Write만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'W', false, true, false
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'W');

-- D: Delete만 허용
INSERT INTO permission (name, readable, writable, deletable)
SELECT 'D', false, false, true
    WHERE NOT EXISTS (SELECT 1 FROM permission WHERE name = 'D');

-- STATE

INSERT INTO state (name)
SELECT 'LIQUID'
    WHERE NOT EXISTS (SELECT 1 FROM state WHERE name = 'LIQUID');

INSERT INTO state (name)
SELECT 'SOLID'
    WHERE NOT EXISTS (SELECT 1 FROM state WHERE name = 'SOLID');

INSERT INTO state (name)
SELECT 'GAS'
    WHERE NOT EXISTS (SELECT 1 FROM state WHERE name = 'GAS');

INSERT INTO state (name)
SELECT 'POWDER'
    WHERE NOT EXISTS (SELECT 1 FROM state WHERE name = 'POWDER');


-- UNIT

INSERT INTO unit (name, state_id)
SELECT 'ml', state_id
    FROM state
    WHERE name = 'LIQUID'
    AND NOT EXISTS (SELECT 1 FROM unit WHERE name = 'ml');

INSERT INTO unit (name, state_id)
SELECT 'l', state_id
    FROM state
    WHERE name = 'LIQUID'
    AND NOT EXISTS (SELECT 1 FROM unit WHERE name = 'l');

-- UNIT TRANSFER
-- ml -> l 변환 (1 ml = 0.001 l)
INSERT INTO unit_transform (from_unit_id, to_unit_id, ratio)
SELECT u1.unit_id, u2.unit_id, 0.001
FROM unit u1
         JOIN unit u2 ON u1.state_id = u2.state_id
WHERE u1.name = 'ml' AND u2.name = 'l'
  AND NOT EXISTS (
    SELECT 1
    FROM unit_transform
    WHERE from_unit_id = u1.unit_id AND to_unit_id = u2.unit_id
);

-- l -> ml 변환 (1 l = 1000 ml)
INSERT INTO unit_transform (from_unit_id, to_unit_id, ratio)
SELECT u1.unit_id, u2.unit_id, 1000
FROM unit u1
         JOIN unit u2 ON u1.state_id = u2.state_id
WHERE u1.name = 'l' AND u2.name = 'ml'
  AND NOT EXISTS (
    SELECT 1
    FROM unit_transform
    WHERE from_unit_id = u1.unit_id AND to_unit_id = u2.unit_id
);


-- CATEGORY

INSERT INTO item_category (name)
SELECT '유제품'
    WHERE NOT EXISTS (SELECT 1 FROM item_category WHERE name = '유제품');

INSERT INTO item_category (name)
SELECT '육류'
    WHERE NOT EXISTS (SELECT 1 FROM item_category WHERE name = '육류');


-- ITEM

INSERT INTO item (name, category_id, state_id)
SELECT 'milk', category_id, state_id
    FROM item_category, state
    WHERE item_category.name = '유제품'
    AND state.name = 'LIQUID'
    AND NOT EXISTS (SELECT 1 FROM item WHERE name = 'milk');

INSERT INTO item (name, category_id, state_id)
SELECT '삼겹살', category_id, state_id
    FROM item_category, state
    WHERE item_category.name = '육류'
    AND state.name = 'SOLID'
    AND NOT EXISTS (SELECT 1 FROM item WHERE name = '삼겹살');