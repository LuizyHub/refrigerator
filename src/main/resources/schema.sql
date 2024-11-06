-- member 테이블 -- 정제
CREATE TABLE IF NOT EXISTS member (
                                      user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
    );

-- refrigerator 테이블 -- 정제
CREATE TABLE IF NOT EXISTS refrigerator (
                                            refrig_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            name VARCHAR(100) NOT NULL
    );

-- permission 테이블 -- 정제
CREATE TABLE IF NOT EXISTS permission (
                                          permission_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(50) NOT NULL UNIQUE,
    readable BOOLEAN NOT NULL,
    writable BOOLEAN NOT NULL,
    deletable BOOLEAN NOT NULL
    );

-- member_refrig 테이블 (member와 refrigerator의 N:N 관계 및 permission 정보) -- 정제
CREATE TABLE IF NOT EXISTS member_refrig (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             user_id BIGINT NOT NULL,
                                             refrig_id BIGINT NOT NULL,
                                             permission_id BIGINT NOT NULL,
                                             FOREIGN KEY (user_id) REFERENCES member(user_id),
    FOREIGN KEY (refrig_id) REFERENCES refrigerator(refrig_id),
    FOREIGN KEY (permission_id) REFERENCES permission(permission_id)
    );
CREATE INDEX IF NOT EXISTS idx_refrig_user ON member_refrig (refrig_id, user_id);
CREATE INDEX IF NOT EXISTS idx_user_refrig ON member_refrig (user_id, refrig_id);


--------------------------------------------------
-- item_category 테이블
CREATE TABLE IF NOT EXISTS item_category (
                                             category_id INT PRIMARY KEY AUTO_INCREMENT,
                                             name VARCHAR(50) NOT NULL UNIQUE
    );

-- state 테이블
CREATE TABLE IF NOT EXISTS state (
                                     state_id INT PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(50) NOT NULL UNIQUE
    );

-- item 테이블
CREATE TABLE IF NOT EXISTS item (
                                    item_id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(100) NOT NULL,
    category_id INT,
    state_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES item_category(category_id),
    FOREIGN KEY (state_id) REFERENCES state(state_id)
    );

--------------------------------------------------


--------------------------------------------------

-- unit 테이블
CREATE TABLE IF NOT EXISTS unit (
                                    unit_id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(50) NOT NULL,
    state_id INT NOT NULL,
    FOREIGN KEY (state_id) REFERENCES state(state_id)
    );

-- unit_transform 테이블 (단위 변환 정보)
CREATE TABLE IF NOT EXISTS unit_transform (
                                              from_unit_id INT,
                                              to_unit_id INT,
                                              ratio DOUBLE NOT NULL,
                                              PRIMARY KEY (from_unit_id, to_unit_id),
    FOREIGN KEY (from_unit_id) REFERENCES unit(unit_id),
    FOREIGN KEY (to_unit_id) REFERENCES unit(unit_id)
    );

-- inventory 테이블
CREATE TABLE IF NOT EXISTS inventory (
                                         inventory_id INT PRIMARY KEY AUTO_INCREMENT,
                                         refrig_id BIGINT NOT NULL,
                                         item_id INT NOT NULL,
                                         unit_id INT NOT NULL,
                                         amount DOUBLE NOT NULL,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         end_at TIMESTAMP,
                                         FOREIGN KEY (refrig_id) REFERENCES refrigerator(refrig_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id),
    FOREIGN KEY (unit_id) REFERENCES unit(unit_id)
    );
CREATE INDEX IF NOT EXISTS idx_refrig ON inventory (refrig_id);
CREATE INDEX IF NOT EXISTS idx_inventory_end_at ON inventory (end_at ASC);
CREATE INDEX IF NOT EXISTS idx_inventory_item_id ON inventory (item_id);

--------------------------------------------------


--------------------------------------------------

-- recipe_category 테이블
CREATE TABLE IF NOT EXISTS recipe_category (
                                               category_id INT PRIMARY KEY AUTO_INCREMENT,
                                               name VARCHAR(50) NOT NULL UNIQUE
    );

-- recipe 테이블
CREATE TABLE IF NOT EXISTS recipe (
                                      recipe_id INT PRIMARY KEY AUTO_INCREMENT,
                                      name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES member(user_id)
    );

-- r_rcategory 테이블 (recipe와 recipe_category의 N:N 관계)
CREATE TABLE IF NOT EXISTS r_rcategory (
                                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                           recipe_id INT NOT NULL,
                                           category_id INT NOT NULL,
                                           FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
    FOREIGN KEY (category_id) REFERENCES recipe_category(category_id)
    );
CREATE INDEX IF NOT EXISTS idx_recipe_category ON r_rcategory (recipe_id, category_id);
CREATE INDEX IF NOT EXISTS idx_category_recipe ON r_rcategory (category_id, recipe_id);

-- recipe_ingredient 테이블
CREATE TABLE IF NOT EXISTS recipe_ingredient (
                                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                                 recipe_id INT NOT NULL,
                                                 item_id INT NOT NULL,
                                                 unit_id INT NOT NULL,
                                                 amount DOUBLE NOT NULL,
                                                 FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id),
    FOREIGN KEY (unit_id) REFERENCES unit(unit_id)
    );
CREATE INDEX IF NOT EXISTS idx_recipe_id ON recipe_ingredient (recipe_id);

--------------------------------------------------

-- log 테이블
CREATE TABLE IF NOT EXISTS log (
                                   log_id INT PRIMARY KEY AUTO_INCREMENT,
                                   user_id BIGINT,
                                   inventory_id INT,
                                   unit_id INT NOT NULL,
                                   amount DOUBLE NOT NULL,
                                   timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (user_id) REFERENCES member(user_id),
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id),
    FOREIGN KEY (unit_id) REFERENCES unit(unit_id)
    );