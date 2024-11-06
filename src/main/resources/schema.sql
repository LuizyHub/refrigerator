-- User 테이블
CREATE TABLE User IF NOT EXISTS User (
                      user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      name VARCHAR(100) NOT NULL
);

-- Refrigerator 테이블
CREATE TABLE Refrigerator IF NOT EXISTS User (
                              refrig_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL
);


-- Role 테이블 RWD, RW, R, D
CREATE TABLE Role IF NOT EXISTS User (
                      role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(50) NOT NULL UNIQUE,
                      readable BOOLEAN NOT NULL,
                      writable BOOLEAN NOT NULL,
                      deletable BOOLEAN NOT NULL
);


-- User_Refrig 테이블 (User와 Refrigerator의 N:N 관계 및 Role 정보)
CREATE TABLE User_Refrig IF NOT EXISTS User (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             user_id BIGINT NOT NULL,
                             refrig_id BIGINT NOT NULL,
                             role_id BIGINT NOT NULL , -- 권한 정보 (예: Admin, Viewer)
                             FOREIGN KEY (user_id) REFERENCES User(user_id),
                             FOREIGN KEY (refrig_id) REFERENCES Refrigerator(refrig_id),
                             FOREIGN KEY (role_id) REFERENCES Role(role_id)
);
CREATE INDEX IF NOT EXISTS idx_refrig_user ON User_Refrig (refrig_id, user_id);
CREATE INDEX IF NOT EXISTS idx_user_refrig ON User_Refrig (user_id, refrig_id);


-- Item_Category 테이블
CREATE TABLE Item_Category IF NOT EXISTS User (
                               category_id INT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(50) NOT NULL UNIQUE -- 카테고리 이름 육류, 해산물
);


-- State 테이블 Solid, Gas, Liquid, Powder
CREATE TABLE State IF NOT EXISTS User (
                       state_id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Item 테이블 재료
CREATE TABLE Item IF NOT EXISTS User (
                      item_id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      category_id INT,
                      state_id INT NOT NULL ,
                      FOREIGN KEY (category_id) REFERENCES Item_Category(category_id),
                      FOREIGN KEY (state_id) REFERENCES State(state_id)
);


-- Unit 테이블
CREATE TABLE Unit IF NOT EXISTS User (
                      unit_id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(50) NOT NULL,
                      state_id INT NOT NULL,
                      FOREIGN KEY (state_id) REFERENCES State(state_id)
);


-- Unit_Transform 테이블 (단위 변환 정보)
CREATE TABLE Unit_Transform IF NOT EXISTS User (
                                from_unit_id INT,
                                to_unit_id INT,
                                ratio DOUBLE NOT NULL,
                                PRIMARY KEY (from_unit_id, to_unit_id),
                                FOREIGN KEY (from_unit_id) REFERENCES Unit(unit_id),
                                FOREIGN KEY (to_unit_id) REFERENCES Unit(unit_id)
);


-- Inventory 테이블 재고
CREATE TABLE Inventory IF NOT EXISTS User (
                           inventory_id INT PRIMARY KEY AUTO_INCREMENT,
                           refrig_id BIGINT NOT NULL ,
                           item_id INT NOT NULL ,
                           unit_id INT NOT NULL ,
                           amount DOUBLE NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           end_at TIMESTAMP, -- 유통기한
                           FOREIGN KEY (refrig_id) REFERENCES Refrigerator(refrig_id),
                           FOREIGN KEY (item_id) REFERENCES Item(item_id),
                           FOREIGN KEY (unit_id) REFERENCES Unit(unit_id)
);

CREATE INDEX IF NOT EXISTS idx_refrig ON Inventory (refrig_id);
-- 유통기한 임박 순 조회
CREATE INDEX IF NOT EXISTS idx_inventory_end_at ON Inventory (end_at ASC);
-- 아이템 종류별 조회
CREATE INDEX IF NOT EXISTS idx_inventory_item_id ON Inventory (item_id);

-- Recipe_Category 테이블
CREATE TABLE Recipe_Category IF NOT EXISTS User (
                                 category_id INT PRIMARY KEY AUTO_INCREMENT,
                                 name VARCHAR(50) NOT NULL UNIQUE -- 카테고리 이름 한식, 중식, 일식
);

-- Recipe 테이블
CREATE TABLE Recipe IF NOT EXISTS User (
                        recipe_id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        user_id BIGINT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES User(user_id)
);


-- R_RCategory 테이블 (Recipe와 Recipe_Category의 N:N 관계)
CREATE TABLE R_RCategory IF NOT EXISTS User (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             recipe_id INT NOT NULL ,
                             category_id INT NOT NULL ,
                             FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
                             FOREIGN KEY (category_id) REFERENCES Recipe_Category(category_id)
);

CREATE INDEX IF NOT EXISTS idx_recipe_category ON R_RCategory (recipe_id, category_id);
CREATE INDEX IF NOT EXISTS idx_category_recipe ON R_RCategory (category_id, recipe_id);

-- Recipe_Ingredient 테이블 (Recipe와 Ingredient의 관계)
CREATE TABLE Recipe_Ingredient IF NOT EXISTS User (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   recipe_id INT NOT NULL ,
                                   item_id INT NOT NULL ,
                                   unit_id INT NOT NULL ,
                                   amount DOUBLE NOT NULL,
                                   FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
                                   FOREIGN KEY (item_id) REFERENCES Item(item_id),
                                   FOREIGN KEY (unit_id) REFERENCES Unit(unit_id)
);

CREATE INDEX recipe_id ON Recipe_Ingredient (recipe_id);


-- Log 테이블
CREATE TABLE Log IF NOT EXISTS User (
                     log_id INT PRIMARY KEY AUTO_INCREMENT,
                     user_id BIGINT,
                     inventory_id INT,
                     unit_id INT NOT NULL ,
                     amount DOUBLE NOT NULL, -- 변화량 (양수: 추가, 음수: 소비)
                     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     FOREIGN KEY (user_id) REFERENCES User(user_id),
                     FOREIGN KEY (inventory_id) REFERENCES Inventory(inventory_id),
                     FOREIGN KEY (unit_id) REFERENCES Unit(unit_id)
);
