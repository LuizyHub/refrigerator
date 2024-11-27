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

INSERT INTO item_category (name)
SELECT 'POWDER'
    WHERE NOT EXISTS (SELECT 1 FROM item_category WHERE name = 'POWDER');


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


-- CATEGORY

INSERT INTO item_category (name)
SELECT '유제품'
    WHERE NOT EXISTS (SELECT 1 FROM item_category WHERE name = '유제품');


-- ITEM

INSERT INTO item (name, category_id, state_id)
SELECT 'milk', category_id, state_id
    FROM item_category, state
    WHERE item_category.name = '유제품'
    AND state.name = 'LIQUID'
    AND NOT EXISTS (SELECT 1 FROM item WHERE name = 'milk');