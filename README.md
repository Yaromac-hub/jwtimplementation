# Assignment 3 Project for Cryptography Class

The goal of this project is to implement authentication with JWT tokens and demonstrate their security features.

## Getting Started

To start the applications using Docker Compose, follow these steps:

```bash
docker-compose up --build
```

This will launch two services:

1. **JwtProducer:** This service provides an API to generate JWT tokens for authorized users.

2. **JwtConsumer:** This service offers an API to access user data and uses a JWT token to authorize requests.

Both services utilize predefined key pairs for signing and verifying JWT tokens: `key.pem` and `public.pem`.

## Tasks

To explore the project and understand JWT token usage, perform the following tasks:

1. Acquire a JWT token from the JwtProducer app using valid user credentials.
2. Use the obtained JWT token to request data from the JwtConsumer app.
3. Perform requests using both Admin and ordinary user roles and describe your observations.
4. As the token's lifespan is set to only 1 minute, attempt to change the payload of the token, modify the expiration date, and use it again to request data.
5. Utilize the provided helper [script](./script.sh) to create a valid JWT token and try it.


## Stopping the Applications

To stop the applications, run:

```bash
docker-compose down
```