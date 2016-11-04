# try-hibernate
Try features of Hibernate without install database.

# Database
* Use HyerpSql (hsql): http://hsqldb.org/

# Usage:
* Check the Unit Test to see the demo of how to use Hibernate.
* Load the project into IntelliJ and debug the unit test.
* Before running single case, you'd better run cleanMockData to clean database. 

# Some tips when you run into some errors.
* Suggest use gradle task 'gradle cleanMockData' to remove database before you test.

# Issues
* The cases are not independent. All of them are using the same database. So Only run one test case every time and run cleanMockData before.
