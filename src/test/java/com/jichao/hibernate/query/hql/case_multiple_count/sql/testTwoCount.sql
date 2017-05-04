-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE TABLE host (id VARCHAR(50), name VARCHAR(50));

CREATE TABLE host_initiator (id VARCHAR(50), name VARCHAR(50));

CREATE TABLE host_iscsiHostInitiators (id VARCHAR(50), elementvalue VARCHAR(50));

CREATE TABLE host_fcHostInitiators (id VARCHAR(50), elementvalue VARCHAR(50));

INSERT INTO host(id, name) VALUES
    ('host_1', 'host_1_name'),
    ('host_2','host_2_name');

INSERT INTO host_initiator (id, name) VALUES
    ('initiator_1', 'iscsi_initiator_1_name'),
    ('initiator_2', 'iscsi_initiator_2_name'),
    ('initiator_3', 'fc_initiator_3_name'),
    ('initiator_4', 'fc_initiator_4_name'),
    ('initiator_5', 'fc_initiator_5_name'),
    ('initiator_6', 'iscsi_initiator_6_name');


INSERT INTO host_iscsiHostInitiators (id, elementvalue) VALUES
    ('host_1', 'initiator_1'),
    ('host_2', 'initiator_2');

INSERT INTO host_fcHostInitiators (id, elementvalue) VALUES
    ('host_1', 'initiator_6'),
    ('host_2', 'initiator_3'),
    ('host_2', 'initiator_4'),
    ('host_2', 'initiator_5');

SELECT * FROM host;
