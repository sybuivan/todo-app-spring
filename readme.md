# Todo Management Application API

This project showcases the development of an API for a Todo Management Application using Spring Boot, Spring Data JPA, and Spring Security.
The API enables users to manage tasks, user accounts, and associated details efficiently.

# Todo App API Documentation

This API documentation provides an overview of the Todo App API endpoints using the Postman collection provided. The API facilitates user and admin management, task operations, and more. The Postman collection can be accessed [here](https://www.postman.com/codtalk/workspace/todo-app/collection/14279444-12a7d112-e049-43cb-be11-fe5829b5f348).

## API Endpoints

Please refer to the [Postman collection](https://www.postman.com/codtalk/workspace/todo-app/collection/14279444-12a7d112-e049-43cb-be11-fe5829b5f348) for detailed request and response examples for each endpoint.

## Features

### User Management

- **User Registration:**
  - Implemented user registration with secure data validation.
- **User Login:**
  - Developed a secure user authentication mechanism using Spring Security.
  - Utilized JSON Web Tokens (JWT) for session management.

- **Refresh Token:**
  - Implemented token refresh functionality for extending user sessions.
  - Ensured token security through modern authentication strategies.

- **Forgot Password and Email Notifications:**
  - Enabled users to initiate password reset requests and receive email notifications.
  - Integrated email services for secure password reset instructions.

- **Reset Password:**
  - Developed a secure process for users to reset their passwords.
  - Utilized secure token-based password reset links with expiration.

- **Update User Information:**
  - Created a user profile management feature with secure data validation.
  - Implemented data protection to prevent unauthorized modifications.

- **Change Password:**
  - Implemented secure password change functionality with encryption and hashing.
  - Ensured data integrity and user account security.

### Admin Management

- **Get All Users with Filtering and Pagination:**
  - Developed a feature for administrators to retrieve user data with filtering and pagination.

- **Lock/Unlock User Accounts:**
  - Implemented user account locking and unlocking functionality for administrators.
  - Enhanced security measures by providing administrative control.

- **Password Reset for Users:**
  - Implemented a secure mechanism for administrators to initiate password resets on behalf of users.
  - Improved user support and account security.

- **User Task Statistics:**
  - Created a feature to generate and display task statistics for users.
  - Enhanced user task management and performance evaluation.

### Task Management

- **Create Task:**
  - Implemented task creation with data validation for accurate task details.
  - Enabled users to create new tasks with relevant information.

- **Update Task:**
  - Developed task updating capabilities with data validation for modifications.
  - Ensured accurate and secure updates to task information.

- **Complete Task:**
  - Implemented task completion functionality, allowing users to mark tasks as completed.
  - Designed the system to track task completion status.

- **Delete Task by ID:**
  - Developed secure task deletion by unique identifiers.
  - Prevented unauthorized task deletion through data protection.

- **Filtering, Pagination, and Retrieval of Tasks:**
  - Created advanced task management features including filtering and pagination.
  - Enhanced data retrieval efficiency for tasks.

### Task Detail Management

- **Create, Update, Get, and Delete Task Detail:**
  - Developed a comprehensive system for managing task details.
  - Implemented features to create, update, retrieve, and delete task details.

### Task Type Management

- **Create Task Type:**
  - Designed task type creation with data validation.
  - Enabled categorization of tasks through task types.

- **Retrieve Task Type List:**
  - Developed functionality to retrieve a list of available task types.
  - Aided users in task creation and selection.

## Technologies Used

- Programming Language: Java
- Framework: Spring Boot
- Persistence: Spring Data JPA
- Security: Spring Security, JSON Web Tokens (JWT)
- Database: MySQL
- Version Control: Git

## About

This project demonstrates my ability to design, develop, and implement various functionalities in a Spring Boot API using Spring Data JPA for seamless database interaction and Spring Security for robust authentication and authorization mechanisms. It showcases my proficiency in full-stack development, secure authentication, data protection, and collaboration within a team environment.
