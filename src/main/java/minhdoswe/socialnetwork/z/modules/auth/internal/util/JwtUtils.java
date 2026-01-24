package minhdoswe.socialnetwork.z.modules.auth.internal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    public String generateAccessToken(Long userId, List<String> roles, String email, String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        claims.put("roles", roles);
        claims.put("username", username);
        claims.put("email", email);
//        claims.put("issueAt", Instant.now(clock));
        return createToken(claims, userId);
    }

    String createToken(Map<String, Object> claims, Long userId) {

        Date expiryDate = Date.from(Instant.now(clock).plusMillis(ACCESS_TOKEN_EXPIRATION));

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(Instant.now(clock)))
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {

            try {
                Claims claims = extractAllClaim(token);
            } catch (JwtException e) {
                return false;
            }

            return true;
    }

    public List<String> extractUserRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, claims -> claims.getSubject()));
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    public Instant extractIssueAt(String token) {
        return extractClaim(token, claims -> claims.get("issueAt", Instant.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
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
