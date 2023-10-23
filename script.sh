#!/bin/bash

# Define a base64url transformation command
base64url='base64 | tr "/+" "_-" | tr -d "="'

# Key pair is created using thise commands
## openssl genrsa -out key.pem 2048
## openssl rsa -in key.pem -outform PEM -pubout -out public.pem

# Convert the header and payload to base64url representation
(cat header.json | eval $base64url) > header.b64
(cat payload.json | eval $base64url) > payload.b64

# Concatenate the header and payload into "header.b64"."payload.b64"
cat header.b64 | tr -d '\n' > unsignedjwt.b64
echo -n "." >> unsignedjwt.b64
cat payload.b64 | tr -d '\n' >> unsignedjwt.b64

# Sign the concatenated data with the private key
openssl dgst -sha256 -sign key.pem  -out signature.bin  unsignedjwt.b64

# Concatenate everything into "header.b64"."payload.b64"."signature"
cat unsignedjwt.b64 | tr -d '\n' > jwt
echo -n "." >> jwt
(cat signature.bin | eval $base64url)  | tr -d '\n' >> jwt

# Verify the unsigned part with the public key
openssl dgst -verify public.pem -sha256 -signature signature.bin unsignedjwt.b64

# Print the resulting JWT token
cat jwt

# Remove redundant files
rm header.b64
rm payload.b64
rm signature.bin