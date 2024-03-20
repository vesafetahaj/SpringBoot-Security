package com.vesa.securityTeddy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/*This is likely a utility class used to generate JWT tokens after a user successfully authenticates. It might include
methods to specify token claims (such as user details), expiration times, and signing the token to ensure its integrity.
Generated tokens are sent back to users, which they must include in the headers of their subsequent requests to access
 protected resources.*/

@Component
public class JWTGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public TokenPair generateTokens(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();

        // Generate access token
        Date accessTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION * 1000);
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(accessTokenExpiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Generate refresh token
        Date refreshTokenExpiry = new Date(currentDate.getTime() + SecurityConstants.REFRESH_TOKEN_EXPIRATION * 1000);
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(refreshTokenExpiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return new TokenPair(accessToken, refreshToken);
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }
}
