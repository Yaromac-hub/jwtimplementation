# Assignment 3 Project for Cryptography Class

The goal of this project is to implement authentication with JWT tokens and demonstrate their security features.

## Getting Started

To start the applications using Docker Compose, run:

```bash
docker-compose up --build
```

This will launch two services: JwtProducer and JwtConsumer, which will run on ports 8080 and 8081, respectively.

- [JwtProducer](./jwtproducer/) service provides an API to generate JWT tokens to authorized users.
- [JwtConsumer](./jwtconsumer/) service provides an API to access user data and uses a JWT token to authorize the requests.

Both services utilize predefined key pairs to sign and verify JWT tokens: `key.pem` and `public.pem`.

Please refer to the helper [script](./script.sh) for the signing and verification process.

To stop the applications, run:

```bash
docker-compose down
```