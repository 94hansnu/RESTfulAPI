# Description
##### This project is a demonstration of a RESTful API based on Spring Boot, featuring user authentication, user roles, and basic CRUD operations for managing books. It utilizes technologies such as Spring Security, OAuth2, and Spring Data JPA.

### Prerequisites
##### Before you begin, ensure you have the following installed:
* Java 17
* Maven
* MySQL (Make sure to configure the database details in 'application.properties')

### Installation
1. Clone the repository https://github.com/94hansnu/RESTfulAPI
2. Build the project
3. The project is built automatically and includes all dependencies.
4. In case of failure, build the project manually by right-clicking on the project's root folder in the project tree and selecting "Rebuild"

### Database Configuration
1. Ensure you have MySQL installed and running.
2. Open 'application.properties' and configure the connection details for the database. Adjust the URL, username, and password as needed. 
3. The database schema will be created or updated automatically based on the JPA entity classes.

### Run the Application
1. Run the application with maven
2. The application will be accessible at http://localhost:8080.

### API Endpoints
##### To test the API with Postman, use the following link to download postman https://www.postman.com/downloads/

##### API Endpoints
##### Admin Controller:
* Endpoint : '/admin'
  * Method: 'GET'
  * Description: Returns "Admin level access."
* Endpoint : '/admin/{id}'
  * Method: 'PUT'
  * Description: Updates a user with the specified ID
##### Authentication Controller:
* Endpoint : '/auth/register'
  * Method: 'POST'
  * Description: Registers a new user
* Endpoint : '/auth/login'
  * Method: 'POST'
  * Description: Logs in a user
##### Book Controller:
* Endpoint : '/book'
  * Method: 'POST'
  * Description: Adds a new book
* Endpoint : '/book/{id}'
  * Method: 'DELETE'
  * Description: Deletes a book with the specified ID
* Endpoint : '/book
  * Method: 'GET'
  * Description: Returns a list of all books
* Endpoint : '/book/{id}
  * Method: 'GET'
  * Description: Returns information about a specific book
* Endpoint : '/book/{id}
  * Method: 'PUT'
  * Description: Updates the author of a book with the specified ID

##### User Controller
* Endpoint : '/user'
  * Method: 'GET'
  * Description: Returns "User access level."
* Endpoint : '/user'
  * Method: 'GET'
  * Description: Returns a list of all users
* Endpoint : '/user/{id}'
  * Method: 'GET'
  * Description: Returns information about a user with the specified ID
* Endpoint : '/user'
  * Method: 'POST'
  * Description: Creates a new user

### Security Configuration
##### The project includes security features such as role-based access control, configuration of OAuth2 resource server, and JWT token management. The 'SecurityConfiguration' class contains the necessary configurations.
### Additional Information
##### The project initializes an admin user on startup with the credentials (username: admin, password: password). You can change this behavior in the 'ResTfulApiApplication' class.


    
