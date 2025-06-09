package sanea.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
	private static final String SECRET_KEY = "supersecretkey"; // Troque por uma chave segura

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // Usa o userId diretamente como String
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expira em 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static int validateToken(String token) {
        return Integer.parseInt(Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
}
