# School App Pro
![index page](https://github.com/ConstantineVac/school-app-pro/assets/108877593/ac08f125-3a0a-48ad-a9dd-36577e998949)


Welcome to School App Pro! This application is designed within the boundaries of a university project. The application enables the staff managers of a school organization, to perform basic CRUD functions on members and events of a school campus.

## Technologies and IDE Used:
- IntelliJ Ultimate
- Java
- Spring Boot 2.7.15
- Hibernate
- Java Persistence API (JPA)
- ThymeLeaf
- Spring Security
- MySQL
- JavaScript

## Security

- The School App Pro prioritizes security to ensure the safety of your data and user information. It implements the following security features:

- spring-boot-starter-security: This dependency provides comprehensive security features for Spring Boot applications. It includes authentication, authorization, and protection against common security vulnerabilities.
The security in School App Pro is implemented as a web form-based authentication system. Users are required to log in using their credentials to access the application's features securely. Spring Boot Security handles user authentication and session management, ensuring that only authorized users can access protected resources within the application. Security configurations can be customized to meet specific requirements, such as role-based access control and password policies.

Please refer to the Spring Boot documentation for more details on configuring and customizing security features: Spring Boot Security

This web form-based authentication system provides a user-friendly and secure way to interact with the School App Pro. Users can log in, access various sections of the application, and log out when their tasks are completed, ensuring a seamless and secure user experience.

## Sections

### Login and Registration

- Let's administrators, create a new moderator account with a valid email and a password (1 uppper one lower case and at least 8-chars with a special character and numbers)
- Users are stored in our MySQL Database and passwords are beinged stored as hashed passwords using BCrypt Dependency.

![image](https://github.com/ConstantineVac/school-app-pro/assets/108877593/aa5c6a7b-443a-42a6-a44b-a767b0e3f00e)

![image](https://github.com/ConstantineVac/school-app-pro/assets/108877593/38a934c4-880e-4e6b-b985-08139931fb5d)

## School App Pro - CRUD Operations

This document outlines the basic CRUD (Create, Read, Update, Delete) operations available for various entities in the School App Pro.

## Table of Contents
![image](https://github.com/ConstantineVac/school-app-pro/assets/108877593/ae50d505-94e9-4902-973e-a873c9826923)

1. [Teachers](#teachers)
2. [Students](#students)
3. [Cities](#cities)
4. [Meetings](#meetings)
5. [Specialties](#specialties)


### Teacher Management

### Create (Insert)

- **Endpoint**: `/teacher/insert`
- **Description**: Add a new teacher to the system. With an SSN, firstname, lastname and specialty
- **HTTP Method**: POST
- **Request Body**: Teacher information (e.g., name, subject).
- **Usage**: Send a POST request with teacher details to create a new teacher.

### Read (Search)

- **Endpoint**: `/teacher/search`
- **Description**: Search for teachers based on specific criteria.
- **HTTP Method**: POST
- **Request Body**: Search parameters (e.g., teacher name, subject).
- **Usage**: Send a POST request with search criteria to retrieve matching teachers.

### Update

- **Endpoint**: `/teacher/update/{id}`
- **Description**: Update an existing teacher's information.
- **HTTP Method**: PUT
- **Request Body**: Updated teacher details.
- **Usage**: Send a PUT request with updated teacher information to modify an existing teacher.

### Delete

- **Endpoint**: `/teacher/delete/{id}`
- **Description**: Remove a teacher from the system.
- **HTTP Method**: DELETE
- **Usage**: Send a DELETE request with the teacher's ID to delete the teacher.

## Students

### Create (Insert)

- **Endpoint**: `/student/insert`
- **Description**: Add a new student to the system with details such as name, ID, etc.
- **HTTP Method**: POST
- **Request Body**: Student information (e.g., name, ID).
- **Usage**: Send a POST request with student details to create a new student.

### Read (Search)

- **Endpoint**: `/student/search`
- **Description**: Search for students based on specific criteria.
- **HTTP Method**: POST
- **Request Body**: Search parameters (e.g., student name, ID).
- **Usage**: Send a POST request with search criteria to retrieve matching students.

### Update

- **Endpoint**: `/student/update/{id}`
- **Description**: Update an existing student's information.
- **HTTP Method**: PUT
- **Request Body**: Updated student details.
- **Usage**: Send a PUT request with updated student information to modify an existing student.

### Delete

- **Endpoint**: `/student/delete/{id}`
- **Description**: Remove a student from the system.
- **HTTP Method**: DELETE
- **Usage**: Send a DELETE request with the student's ID to delete the student.


## Cities

### Create (Insert)

- **Endpoint**: `/city/insert`
- **Description**: Add a new city to the system with details such as name, ID, etc.
- **HTTP Method**: POST
- **Request Body**: City information (e.g., name).
- **Usage**: Send a POST request with student details to create a new city.

### Read (Search)

- **Endpoint**: `/city/search`
- **Description**: Search for cities based on specific criteria.
- **HTTP Method**: POST
- **Request Body**: Search parameters (e.g., city name).
- **Usage**: Send a POST request with search criteria to retrieve matching cities.

### Update

- **Endpoint**: `/city/update/{id}`
- **Description**: Update an existing city's information.
- **HTTP Method**: PUT
- **Request Body**: Updated city details.
- **Usage**: Send a PUT request with updated city information to modify an existing city.

### Delete

- **Endpoint**: `/city/delete/{id}`
- **Description**: Remove a city from the system.
- **HTTP Method**: DELETE
- **Usage**: Send a DELETE request with the city's ID to delete the city.


## Specialties

### Create (Insert)

- **Endpoint**: `/specialty/insert`
- **Description**: Add a new specialty to the system with details such as name, ID, etc.
- **HTTP Method**: POST
- **Request Body**: Specialty information (e.g., name).
- **Usage**: Send a POST request with specialty details to create a new specialty.

### Read (Search)

- **Endpoint**: `/specialty/search`
- **Description**: Search for specialties based on specific criteria.
- **HTTP Method**: POST
- **Request Body**: Search parameters (e.g., specialty name).
- **Usage**: Send a POST request with search criteria to retrieve matching specialties.

### Update

- **Endpoint**: `/specialty/update/{id}`
- **Description**: Update an existing specialty's information.
- **HTTP Method**: PUT
- **Request Body**: Updated specialty details.
- **Usage**: Send a PUT request with updated specialty information to modify an existing specialty.

### Delete

- **Endpoint**: `/specialty/delete/{id}`
- **Description**: Remove a specialty from the system.
- **HTTP Method**: DELETE
- **Usage**: Send a DELETE request with the specialty's ID to delete the specialty.


## Meetings

### Create (Insert)

- **Endpoint**: `/meeting/insert`
- **Description**: Add a new meeting to the system with details such as date, location, etc.
- **HTTP Method**: POST
- **Request Body**: Meeting information (e.g., date, location).
- **Usage**: Send a POST request with meeting details to create a new meeting.

### Read (Search)

- **Endpoint**: `/meeting/search`
- **Description**: Search for meetings based on specific criteria.
- **HTTP Method**: POST
- **Request Body**: Search parameters (e.g., date, location).
- **Usage**: Send a POST request with search criteria to retrieve matching meetings.

### Update

- **Endpoint**: `/meeting/update/{id}`
- **Description**: Update an existing meeting's information.
- **HTTP Method**: PUT
- **Request Body**: Updated meeting details.
- **Usage**: Send a PUT request with updated meeting information to modify an existing meeting.

### Delete

- **Endpoint**: `/meeting/delete/{id}`
- **Description**: Remove a meeting from the system.
- **HTTP Method**: DELETE
- **Usage**: Send a DELETE request with the meeting's ID to delete the meeting.


## Conclusion

This README provides an overview of the basic CRUD operations available for Teachers, Students, Cities, Meetings, and Specialties in the School App Pro. For detailed endpoint URLs, request/response formats, and usage examples, refer to the API documentation or relevant code files in the application.


## Getting Started
Clone the repository, load the project in IntelliJ IDEA, and configure it for development.

### Prerequisites
Make sure you have the following software installed on your system:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) - Integrated Development Environment (IDE)
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) - Java Development Kit
- [MySQL](https://dev.mysql.com/downloads/mysql/) - Database Server

### Clone the Repository
1. Open your terminal/command prompt.
2. Navigate to the directory where you want to store the project.
3. Run the following command to clone the repository:
   ``` shell
      git clone https://github.com/ConstantineVac/school-app-pro.git
   ```
### Import the Project in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Click on `File` > `Open...` and select the cloned project directory (`school-app-pro`).
3. IntelliJ IDEA will automatically recognize it as a Spring Boot project and configure it accordingly.

### Configure the Database
1. Open the `application.properties` file located in `src/main/resources`.
2. Modify the database configuration properties according to your MySQL setup (e.g., `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`).

### Build and Run
1. Build the project by clicking `Build` > `Build Project` in IntelliJ IDEA.
2. Run the project by right-clicking on the `SchoolApplication` class located in `src/main/java/com/school/app/SchoolApplication.java` and selecting `Run 'SchoolApplication'`.

### Access the Application
Once the application is running, you can access it through a web browser using the following URL:
- [http://localhost:8080](http://localhost:8080)

You should see the School App Pro the home page. Then click again on login and you should see the dashboard menu !


## Dependencies

- **Spring Boot**: v2.7.15
- **Java Version**: 11

### Spring Boot Starter Dependencies

- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-security`
- `org.springframework.boot:spring-boot-starter-thymeleaf`
- `org.springframework.boot:spring-boot-starter-web`
- `com.mysql:mysql-connector-j` (Runtime Scope)
- `org.springframework.boot:spring-boot-starter-test` (Test Scope)

### Validation Dependencies

- `javax.validation:validation-api:2.0.1.Final`
- `org.hibernate.validator:hibernate-validator:6.2.0.Final`

### Other Dependencies

- `org.projectlombok:lombok`


## Contributors

- [AsmrProg](https://github.com/AsmrProg-YT/)

