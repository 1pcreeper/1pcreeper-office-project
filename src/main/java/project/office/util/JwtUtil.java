package project.office.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.office.model.entity.AppUser;
import project.office.model.entity.enums.AppUserRole;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.millis}")
    private Long jwtExpirationMillis;

    /**
     * Generate a JWT token for given user details.
     *
     * @param appUser the appUser details to embed in the token
     * @return the generated JWT string
     */
    public String generateToken(AppUser appUser) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMillis);

        return Jwts.builder()
                .subject(appUser.getId().toString())
                .claim("authorities", AppUserRole.ADMIN)
                //.claim("authorities", user.getRoles())
                //.claim("displayName", user.getDisplayName())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Extract the username from a JWT token.
     *
     * @param token the JWT string
     * @return the username contained in the token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validate a given JWT token.
     *
     * @param token the JWT string
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            // JwtException covers expiration, signature invalidity, and other parsing issues.
            return false;
        }
    }
}
