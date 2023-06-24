package fii.workflow.manager.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    private final String secret = "secret";
    private final Long oneHourInMillis = Long.valueOf(60 * 60 * 1000);

    public String generateToken(UserDetails userDetails) throws IllegalArgumentException {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().stream().toList().get(0).getAuthority())
                .withIssuedAt(new Date())
                .withIssuer("Workflow Manager")
                .withExpiresAt(new Date(System.currentTimeMillis() + oneHourInMillis))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Workflow Manager")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        if(jwt.getExpiresAt().before(new Date())){
            throw new RuntimeException("token expired");
        }
        return jwt.getSubject();
    }
}
