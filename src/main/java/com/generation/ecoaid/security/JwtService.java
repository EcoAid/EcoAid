package com.generation.ecoaid.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	// Código de criptográfia, todos são diferentes.
	public static final String SECRET = "c3349c8e01f866be73c2fceed12bcfc1cfa9ddd4ce5967cc4f63baac549a3aeb";

	// Códifica o SECRET na base 64 para criar a assinatura.
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Extrai todas as claims da token, mas nesse caso apenas a assinatura
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	// Extrai a claim e deixa ela entendivel pro sistema.
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Extrai o email do token.
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extrai a data de expiração do token.
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Validação se o token estiver expirado, retorna boolean.
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Valida o match do user com o token.
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Forma o token com todos os dados necessários.
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// Gera o token com o E-mail colocado no parametro.
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
}
