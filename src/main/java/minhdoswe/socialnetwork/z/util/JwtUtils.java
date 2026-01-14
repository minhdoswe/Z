package minhdoswe.socialnetwork.z.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.access-token-secret}")
    private String ACCESS_TOKEN_SECRET;

    @Value("${jwt.access-token-expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    private final Clock clock;

    public SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateAccessToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return createToken(claims, username);
    }

    String createToken(Map<String, Object> claims, String username) {

        Date expiryDate = Date.from(Instant.now(clock).plusMillis(ACCESS_TOKEN_EXPIRATION));

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now(clock)))
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token, UserDetails userDetails) {

            Claims claims = extractAllClaim(token);

            String username = claims.getSubject();

            return username.equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.getSubject());
    }

    private Date extractExpires(String token) {
        return extractClaim(token, claims -> claims.getExpiration());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claim = extractAllClaim(token);
        return claimResolver.apply(claim);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
