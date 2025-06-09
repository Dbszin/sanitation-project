package sanea.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Base64;

public class JwtUtil {
    // Chave secreta em base64 (32 bytes)
    private static final String SECRET_KEY = "SECRET_KEY";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public static String generateToken(String userId) {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
} 
