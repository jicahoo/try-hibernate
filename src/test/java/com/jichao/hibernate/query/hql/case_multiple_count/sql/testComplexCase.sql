-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE TABLE host (id VARCHAR(50), name VARCHAR(50), type INTEGER);

CREATE TABLE host_initiator (id VARCHAR(50), name VARCHAR(50));

CREATE TABLE host_iscsiHostInitiators (id VARCHAR(50), elementvalue VARCHAR(50));

CREATE TABLE host_fcHostInitiators (id VARCHAR(50), elementvalue VARCHAR(50));

INSERT INTO host(id, name, type) VALUES
    ('host_1', 'host_1_name', 1),
    ('host_2','host_2_name', 2),
    ('host_2','host_2_name', 2),
    ('host_3','host_3_name', 2),
    ('host_4','host_4_name', 2),
    ('host_5','host_5_name', 2),
    ('host_6','host_6_name', 2),
    ('host_7','host_7_name', 2)
    ;

INSERT INTO host_initiator (id, name) VALUES
    ('initiator_1', 'iscsi_initiator_1_name'),
    ('initiator_2', 'iscsi_initiator_2_name'),
    ('initiator_3', 'fc_initiator_3_name'),
    ('initiator_4', 'fc_initiator_4_name'),
    ('initiator_5', 'fc_initiator_5_name'),
    ('initiator_6', 'iscsi_initiator_6_name'),
    ('initiator_7', 'iscsi_initiator_7_name'),
    ('initiator_8', 'iscsi_initiator_8_name'),
    ('initiator_9', 'iscsi_initiator_9_name'),
    ('initiator_10', 'iscsi_initiator_10_name'),
    ('initiator_11', 'iscsi_initiator_11_name')
    ;


INSERT INTO host_iscsiHostInitiators (id, elementvalue) VALUES
    ('host_1', 'initiator_1'),
    ('host_2', 'initiator_2'),
    ('host_3', 'initiator_7'),
    ('host_3', 'initiator_8'),
    ('host_4', 'initiator_9'),
    ('host_4', 'initiator_10'),
    ('host_4', 'initiator_11')
    ;

INSERT INTO host_fcHostInitiators (id, elementvalue) VALUES
    ('host_1', 'initiator_6'),
    ('host_2', 'initiator_3'),
    ('host_2', 'initiator_4'),
    ('host_2', 'initiator_5');

SELECT * FROM host;
