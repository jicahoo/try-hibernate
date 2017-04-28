# try-hibernate
Try features of Hibernate without install database.

# TODO
* Add CI. Or the project is rubbish.
* Refactor: for every test (java), one database, data, one hibernate config, one domain model. One session.
* Stick to HSQL and learn HSQL. It is better choice for this project. Command line: http://hsqldb.org/doc/2.0/util-guide/sqltool-chapt.html#N1067A
* Learn HSQL: http://stackoverflow.com/questions/591518/how-to-see-all-the-tables-in-an-hsqldb-database/19646240#19646240
* Easy to prepare table, data, bean.

# Database
* Use HyerpSql (hsql): http://hsqldb.org/  (Reason: Hibernate support it.)

# Usage:
* Check the Unit Test to see the demo of how to use Hibernate.
* Load the project into IntelliJ and debug the unit test.
* Before running single case, you'd better run cleanMockData to clean database. 

# Some tips when you run into some errors.
* Suggest use gradle task 'gradle cleanMockData' to remove database before you test.

# Issues
* The cases are not independent. All of them are using the same database. So Only run one test case every time and run cleanMockData before.
