-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE  TABLE test_jichao (name varchar(20));

CREATE TABLE host (id varchar(20), name varchar(20));

CREATE TABLE host_initiator (id varchar(20), name varchar(20));

CREATE TABLE host_iscsiHostInitiators (id varchar(20), elementvalue varchar(20));

CREATE TABLE host_fcHostInitiators (id varchar(20), elementvalue varchar(20));

INSERT INTO host(id, name) VALUES ('host_1', 'host_1_name'), ('host_2','host_2_name');

INSERT INTO host_initiator (id, name) VALUES ('initiator_1', 'initiator_1_name'), ('initiator_2', 'initiator_2_name');

INSERT INTO host_iscsiHostInitiators (id, elementvalue) VALUES ('host_1', 'initiator_1'), ('host_2', 'initiator_2');
INSERT INTO host_fcHostInitiators (id, elementvalue) VALUES ('host_2', 'initiator_2');
SELECT * FROM host;
