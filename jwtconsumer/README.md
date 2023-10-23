# JwtConsumer Application for Cryptography Class

The JwtConsumer is a Spring Boot Java application that uses the [jjwt library](https://github.com/jwtk/jjwt) to validate JWT tokens.

## Getting Started with Docker

To run the application using Docker, follow these steps:

1. Build the Docker image:

   ```bash
   docker build -t jwtconsumer:latest .
   ```

2. Run the Docker container:

   ```bash
   docker run -p 8081:8081 --name jwtconsumer jwtconsumer:latest
   ```

## Database

The application uses an embedded database with a `USER_DATA` table containing user login and data. For database schema details and sample data, see [data.sql](jwtproducer/src/main/resources/data.sql) and [schema.sql](jwtproducer/src/main/resources/schema.sql).

You can access the database via the following URL: [http://localhost:8081/h2-console](http://localhost:8081/h2-console). Leave the password field empty and change the JDBC URL field from `jdbc:h2:~/test` to `jdbc:h2:mem:testdb` in the login form.

## API Endpoints

The application provides the following API endpoint:

1. **Get User Info**

   Use this endpoint to retrieve user data. 

   - URL: `localhost:8081/user_info`
   - Method: GET
   - Sample Request:

     ```bash
     curl --location 'localhost:8081/user_info?login=login2' \
     --header 'Authorization: Bearer ewogI.....'
     ```

   - Sample Response:

     ```json
     {
       "id": 1,
       "login": "login2",
       "mobilePhone": "0220805667",
       "balance": 100.0,
       "name": "Name Surname"
     }
     ```

   If the token is valid, the response will contain user data from the `USER_DATA` table. Admin users can access all user data, while regular users can access only their own.