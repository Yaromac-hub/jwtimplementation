package com.yaro.jwtconsumer.service;

import com.yaro.jwtconsumer.model.JwtToken;
import com.yaro.jwtconsumer.model.JwtTokenWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class JwtUtil {

    public static JwtTokenWrapper parseToken(String jwt){
        try {
            JwtToken jwtToken = parseClaims(jwt);
            String decodedJwt = decodeJwt(jwt);
            return new JwtTokenWrapper(0l, "OK", jwtToken, decodedJwt);
        } catch(Exception e){
            return new JwtTokenWrapper(1l, e.getMessage().split(":")[0], null, null);
        }
    }

    public static JwtToken parseClaims(String jwt) throws Exception {
        Claims claims = verifyJwt(jwt);
        JwtToken jwtToken = new JwtToken( claims.getSubject(),
                claims.get("name", String.class),
                claims.get("role", String.class),
                claims.getIssuedAt(),
                claims.getExpiration()
        );
        return jwtToken;
    }

    private static Claims verifyJwt(String jwt) throws Exception {
        Claims claims = Jwts.parser()
                .verifyWith(RSAKeyReader.readPublicKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims;
    }

    private static String decodeJwt(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        return header + payload;
    }

}
