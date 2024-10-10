package br.com.demo.person.service;

import br.com.demo.person.model.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Users users){


        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("api-person").withSubject(users.getLogin()).withExpiresAt( generateExpirateData()).sign(algorithm);
            return token;
        }catch(JWTCreationException ex){
            throw new RuntimeException("Error while generating token", ex);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-person")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException ex){
            return "";
        }
    }

    private Instant generateExpirateData(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
