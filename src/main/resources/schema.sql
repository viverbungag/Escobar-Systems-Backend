SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS monthly_report CASCADE;
DROP TABLE IF EXISTS monthly_sales CASCADE;
DROP TABLE IF EXISTS daily_sales CASCADE;
DROP TABLE IF EXISTS monthly_expenses CASCADE;
DROP TABLE IF EXISTS monthly_income_expenses CASCADE;
DROP TABLE iF EXISTS monthly_transactions CASCADE;
DROP TABLE IF EXISTS monthly_utility CASCADE;
DROP TABLE IF EXISTS utility CASCADE;
DROP TABLE IF EXISTS customer_food_order CASCADE;
DROP TABLE IF EXISTS customer_order CASCADE;
DROP TABLE IF EXISTS food_order CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS menu_ingredients CASCADE;
DROP TABLE IF EXISTS menu CASCADE;
DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS supply CASCADE;
DROP TABLE IF EXISTS supplier CASCADE;
DROP TABLE IF EXISTS supply_category CASCADE;
DROP TABLE IF EXISTS unit_of_measurement CASCADE;
DROP TABLE IF EXISTS menu_category CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS employee_income_per_month CASCADE;
DROP TABLE IF EXISTS employee_attendance_join CASCADE;
DROP TABLE IF EXISTS employee_attendance CASCADE;
DROP TABLE IF EXISTS employee_position CASCADE;
DROP TABLE IF EXISTS employee_type CASCADE;
DROP TABLE IF EXISTS expense_category CASCADE;
DROP TABLE IF EXISTS expense CASCADE;
DROP TABLE IF EXISTS transaction_dedicated_to_expired CASCADE;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE IF NOT EXISTS employee_position(
    employee_position_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_position_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (employee_position_id)
);

CREATE TABLE IF NOT EXISTS employee_type(
    employee_type_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_type_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (employee_type_id)
);

CREATE TABLE IF NOT EXISTS employee_attendance(
    employee_attendance_id BIGINT NOT NULL AUTO_INCREMENT,
    attendance_time DATETIME,
    attendance_type VARCHAR(255),
    PRIMARY KEY (employee_attendance_id)
);

CREATE TABLE IF NOT EXISTS employee_income_per_month(
    income_id BIGINT NOT NULL AUTO_INCREMENT,
    payPerHour DECIMAL(10, 2),
    total_hours_worked DECIMAL (4, 2),
    overtimePayPerHour DECIMAL (10,2),
    total_overtime_hours_worked DECIMAL (4,2),
    gross_pay DECIMAL (10,2),
    PRIMARY KEY (income_id)
);

CREATE TABLE IF NOT EXISTS employee(
    employee_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_first_name VARCHAR(255) NOT NULL,
    employee_last_name VARCHAR(255) NOT NULL,
    employee_address VARCHAR(255),
    employee_contact_number VARCHAR(255),
    date_employed DATE,
--    monthly_wage DECIMAL(10, 2),
--    attendance_per_month_id BIGINT,
--    income_id BIGINT,
    employee_position_id BIGINT,
    employee_type_id BIGINT,
    superior_employee_id BIGINT NULL,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (employee_id),
--    FOREIGN KEY (attendance_per_month_id) REFERENCES employee_attendance(attendance_id),
--    FOREIGN KEY (income_id) REFERENCES employee_income_per_month(income_id),
    FOREIGN KEY (employee_position_id) REFERENCES employee_position(employee_position_id),
    FOREIGN KEY (employee_type_id) REFERENCES employee_type(employee_type_id),
    CONSTRAINT emp_superior FOREIGN KEY (superior_employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS employee_attendance_join(
    employee_attendance_join_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_id BIGINT,
    employee_attendance_id BIGINT,
    PRIMARY KEY (employee_attendance_join_id),
    FOREIGN KEY (employee_attendance_id) REFERENCES employee_attendance(employee_attendance_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS account(
    account_id BIGINT NOT NULL AUTO_INCREMENT,
    account_username VARCHAR(255) NOT NULL,
    account_password VARCHAR(255) NOT NULL,
    access_inventory_management_system BOOLEAN DEFAULT false,
    access_employee_management_system BOOLEAN DEFAULT false,
    access_income_and_expense_system BOOLEAN DEFAULT false,
    access_ordering_system BOOLEAN DEFAULT false,
    employee_id BIGINT,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (account_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS menu_category(
    menu_category_id BIGINT NOT NULL AUTO_INCREMENT,
    menu_category_name VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (menu_category_id)
);

CREATE TABLE IF NOT EXISTS unit_of_measurement(
    unit_of_measurement_id BIGINT NOT NULL AUTO_INCREMENT,
    unit_of_measurement_name VARCHAR(255) NOT NULL UNIQUE,
    unit_of_measurement_abbreviation VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (unit_of_measurement_id)
);

CREATE TABLE IF NOT EXISTS supply_category(
    supply_category_id BIGINT NOT NULL AUTO_INCREMENT,
    supply_category_name VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (supply_category_id)
);

CREATE TABLE IF NOT EXISTS supplier(
    supplier_id BIGINT NOT NULL AUTO_INCREMENT,
    supplier_name VARCHAR(255) UNIQUE,
    supplier_address VARCHAR(255),
    supplier_contact_number VARCHAR(255),
    supplier_contact_person VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (supplier_id)
);

CREATE TABLE IF NOT EXISTS supply(
    supply_id BIGINT NOT NULL AUTO_INCREMENT,
    supply_name VARCHAR(255) NOT NULL UNIQUE,
    supply_quantity DECIMAL(10, 2) NOT NULL,
    supplier_id BIGINT,
    unit_of_measurement_id BIGINT,
    supply_category_id BIGINT,
    minimum_quantity DECIMAL(10, 2),
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (supply_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (unit_of_measurement_id) REFERENCES unit_of_measurement(unit_of_measurement_id),
    FOREIGN KEY (supply_category_id) REFERENCES supply_category(supply_category_id)
);

CREATE TABLE IF NOT EXISTS transaction(
    transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    transact_by BIGINT,
    transaction_date DATETIME,
    supplier_id BIGINT,
    transaction_supply_quantity DECIMAL(10, 5),
    supply_id BIGINT,
    price_per_unit DECIMAL(10, 5),
    expiry_date DATETIME,
    transaction_type VARCHAR(255),
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (supply_id) REFERENCES supply(supply_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (transact_by) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS transaction_dedicated_to_expired(
    transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    transact_by BIGINT,
    transaction_date DATETIME,
    supplier_id BIGINT,
    transaction_supply_quantity DECIMAL(10, 5),
    supply_id BIGINT,
    price_per_unit DECIMAL(10, 5),
    expiry_date DATETIME,
    transaction_type VARCHAR(255),
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (supply_id) REFERENCES supply(supply_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (transact_by) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS menu(
    menu_id BIGINT NOT NULL AUTO_INCREMENT,
    menu_name VARCHAR(255) NOT NULL UNIQUE,
    menu_price DECIMAL(10, 2) NOT NULL,
    menu_category_id BIGINT,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (menu_id),
    FOREIGN KEY (menu_category_id) REFERENCES menu_category(menu_category_id)
);

CREATE TABLE IF NOT EXISTS menu_ingredients(
    menu_ingredients_id BIGINT NOT NULL AUTO_INCREMENT,
    menu_id BIGINT,
    supply_id BIGINT,
    quantity DECIMAL(10, 2) NOT NULL DEFAULT 0,
    PRIMARY KEY (menu_ingredients_id),
    FOREIGN KEY (menu_id) REFERENCES menu(menu_id),
    FOREIGN KEY (supply_id) REFERENCES supply(supply_id)
);

CREATE TABLE IF NOT EXISTS food_order(
    food_order_id BIGINT NOT NULL AUTO_INCREMENT,
    menu_id BIGINT,
    menu_quantity INTEGER,
    PRIMARY KEY (food_order_id),
    FOREIGN KEY (menu_id) REFERENCES menu(menu_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS customer_order(
    order_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_id BIGINT,
    order_time DATETIME,
    payment DECIMAL(10, 2),
    discount DECIMAL(10, 2),
    total_cost DECIMAL(10, 2),
    payment_status VARCHAR(255),
    serving_type VARCHAR(255),
    table_number INTEGER,
    additional_payment DECIMAL(10, 2) DEFAULT 0,
    PRIMARY KEY (order_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS customer_food_order(
    customer_food_order_id BIGINT NOT NULL AUTO_INCREMENT,
    food_order_id BIGINT,
    order_id BIGINT,
    PRIMARY KEY (customer_food_order_id),
    FOREIGN KEY (food_order_id) REFERENCES food_order(food_order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (order_id) REFERENCES customer_order(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS expense_category(
    expense_category_id BIGINT NOT NULL AUTO_INCREMENT,
    expense_category_name VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (expense_category_id)
);

CREATE TABLE IF NOT EXISTS expense(
    expense_id BIGINT NOT NULL AUTO_INCREMENT,
    expense_description VARCHAR(255),
    expense_date DATETIME,
    expense_cost DECIMAL(10, 2),
    expense_category_id BIGINT,
    PRIMARY KEY (expense_id),
    FOREIGN KEY (expense_category_id) REFERENCES expense_category(expense_category_id) ON DELETE CASCADE ON UPDATE CASCADE
);
