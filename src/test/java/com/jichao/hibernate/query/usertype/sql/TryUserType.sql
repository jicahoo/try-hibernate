-- !!MUST set autocommit, or the data won't be persisted.
SET AUTOCOMMIT TRUE;
CREATE TABLE filesystem (id VARCHAR(50), start_time VARCHAR(50));
INSERT INTO filesystem VALUES('fs_1', 100);
