package com.yaro.jwtconsumer.service;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.stream.Collectors;


public class RSAKeyReader {
    public static RSAPublicKey readPublicKey() throws Exception {
        String key = getResourceFileAsString("/public.pem");

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }


    private static String getResourceFileAsString(String fileName) throws IOException {
        try (InputStream inputStream = RSAKeyReader.class.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
