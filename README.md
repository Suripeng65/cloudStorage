# File and Information Management System

This is an application to manage personal and file information, including the following three user-facing features:

1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  

## Technologies stack
1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End
The back-end is all about security and connecting the front-end to database data and actions. 

1. Managing user access with Spring Security
 - Created a security configuration class that extends the `WebSecurityConfigurerAdapter` class from Spring.
 - Used the security configuration to override the default `/login` and `/logout` endpoints of Spring Boot.
 - Implemented a custom `AuthenticationProvider` which authorizes user logins by matching their credentials against those stored in the database.  

2. Handling front-end calls with controllers that handles communcations between server-side and client-side

3. Making calls to the database with MyBatis mappers
 - Designed Java classes to match the data in the database. 
 - Implemeneted MyBatis mapper interfaces for each of the model types to support the basic CRUD (Create, Read, Update, Delete) operations.


### The Front-End
1. Login page
 - Everyone have access to this page, and users can use this page to login to the application. 
 - Show login errors, e.g. invalid username/password, on this page. 

2. Sign Up page
 - Everyone have access to this page, and potential users can use this page to sign up for a new account. 
 - Validate that the username supplied does not already exist in the application, and show such signup errors on the page when they arise.

3. Home page
The home page hosts the three required pieces of functionality:

 i. Files
  - The user can upload files and see any files they previously uploaded. 
  - The user can view/download or delete previously-uploaded files.
  - Any errors related to file actions should be displayed.

 ii. Notes
  - The user can create notes and see a list of the notes they have previously created.
  - The user can edit or delete previously-created notes.

 iii. Credentials
 - The user can store credentials for specific websites and see a list of the credentials they've previously stored. 
 - The user scan view/edit or delete individual credentials. When the user views the credential, they see the unencrypted password.

The home page has a logout button that allows the user to logout of the application and keep their data private.

### Testing
1. Tested user signup, login, and unauthorized access restrictions.
2. Tested note creation, viewing, editing, and deletion.
3. Tested for credential creation, viewing, editing, and deletion.

