# User Manager

### Spring Boot Kotlin Backend

Install Maven or use IntelliJ
https://maven.apache.org/download.cgi

    mvn install
    
    mvn spring-boot:run
    
    running at http://localhost:8080

### Angular Frontend

    npm i @angular/cli
    
    inside /UserManagerFrontend
    
    npm i 
    
    npm run start
    
    running at http://localhost:4200

### Endpoints

    POST    /account            create  Account		
    	Request: Account-Object (username, password, firstname, lastname)
    	Response: OK(200), Bad Request(400) - if username already exists, Internal Server error(500)
    POST  /account/delete            delete  Account
    	Request: User-Object (token, username, firstname, lastname)
    	Response: OK(200), Forbidden(403) - on wrong token, Internal Server error(500)
    POST    /account/login      login   Account 
    	Request: Login-Object (username, password)
    	Response: OK(200), Forbidden(403) - on wrong credentials or Ban, Internal Server error(500)
    POST    /account/password   change  Password
    	Request: PasswordChange-Object (username, newPassword)
    	Response: OK(200), Internal Server error(500)

