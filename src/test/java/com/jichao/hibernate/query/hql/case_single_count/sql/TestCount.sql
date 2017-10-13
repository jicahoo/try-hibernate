-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE TABLE filesystem (id VARCHAR(50), name VARCHAR(50));
CREATE TABLE pool (id VARCHAR(50), name VARCHAR(50));
CREATE TABLE storageResource (id VARCHAR(50), name VARCHAR(50), filesystem VARCHAR(50));
CREATE TABLE storageResource_pools (id VARCHAR(50), elementvalue VARCHAR(50));
--
--INSERT INTO filesystem(id,name) VALUES ('fs_1', 'MyFs'), ('fs_2', 'YourFs');
--INSERT INTO storageResource(id, name, filesystem) VALUES ('sr_1', 'OneStorageResource', 'fs_1');
--INSERT INTO storageResource(id, name, filesystem) VALUES ('sr_2', 'TwoStorageResource', 'fs_2');
--
--INSERT INTO pool(id,name) VALUES ('pool_1', 'BobPool'), ('pool_2', 'AlicePool');
--INSERT INTO storageResource_pools(id, elementvalue) VALUES ('sr_1', 'pool_1'), ('sr_1', 'pool_2');





