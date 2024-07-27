# JwtProducer Application for Cryptography Class

The JwtProducer is a Spring Boot Java application that utilizes the [jjwt library](https://github.com/jwtk/jjwt) to generate JWT tokens.

## Getting Started

To run the application using Docker, follow these steps:

1. Build the Docker image:
   ```bash
   docker build -t jwtproducer:latest .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 --name jwtproducer jwtproducer:latest
   ```

## Database

The application uses an embedded database with a `USER_CREDENTIALS` table containing login credentials and roles. For more details on the database schema and sample data, refer to the [data.sql](jwtproducer/src/main/resources/data.sql) and [schema.sql](jwtproducer/src/main/resources/schema.sql) files. 

You can access the database via the following URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console). Leave the password field empty and change the JDBC URL field from `jdbc:h2:~/test` to `jdbc:h2:mem:testdb` in the login form.

## API Endpoints

The application provides the following API endpoint:

1. **Issue JWT Token**

   Use this endpoint to request a JWT token.

   - URL `localhost:8081/issuejwt`
   - **Method:** POST
   - **Sample Request:**

     ```bash
     curl --location 'localhost:8080/issuejwt' \
     --header 'Content-Type: application/json' \
     --data '{
         "login": "login2",
         "password": "Password2"
     }'
     ```
    - **Sample Response:**
        ```json
        {
            "access_token": "eyJhbGciOiJSUzI1...",
            "issued_at": 1698100648007,
            "expires_at": 1698100708007
        }
        ```

If the login and password are valid (found in the `USER_CREDENTIALS` table), the response will be a JWT token with a one-minute lifespan.
