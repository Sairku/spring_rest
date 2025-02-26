
CREATE TABLE IF NOT EXISTS employer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255)
);




INSERT INTO employer (name, address) VALUES ('Company A', '1234 Address St');
INSERT INTO employer (name, address) VALUES ('Company B', '5678 Another Rd');

INSERT INTO customer (name) VALUES ('John Doe');
INSERT INTO customer (name) VALUES ('Jane Smith');

-- Зв'язок між клієнтами та компаніями (багато до багатьох)
INSERT INTO customer_employers (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employers (customer_id, employer_id) VALUES (2, 2);
