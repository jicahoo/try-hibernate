-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE TABLE t_user(id VARCHAR(50), address_id VARCHAR(50));

CREATE TABLE t_address(id VARCHAR(50), country VARCHAR(50));


INSERT INTO t_user(id, address_id) VALUES
    ('user_1', 'addr_1'),
    ('user_2','addr_1'),
    ('user_3', 'addr_1'),
    ('user_4', 'addr_2'),
    ('user_5', 'addr_2');
INSERT INTO t_address(id, country) VALUES
    ('addr_1', 'China'),
    ('addr_2', 'USA');
