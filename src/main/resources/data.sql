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