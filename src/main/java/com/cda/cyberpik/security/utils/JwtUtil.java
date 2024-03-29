package com.cda.cyberpik.security.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cda.cyberpik.exception.InvalidTokenException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Service
public class JwtUtil {

	@Value("${com.cda.cyberpik.jwt.key}")
	private String jwtKey;

	public String extractUsername(String token) throws InvalidTokenException {
		try {
			return extractClaim(token, Claims::getSubject);
		} catch (JwtException e){
			return null;
		}
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		Key vKey = new SecretKeySpec(jwtKey.getBytes(),
				SignatureAlgorithm.HS512.getJcaName());
		return Jwts.parser().setSigningKey(vKey).parseClaimsJws(token).getBody();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
				.signWith(SignatureAlgorithm.HS512, jwtKey).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws InvalidTokenException {
		final String username = extractUsername(token);
		Boolean isUsernameValid = false;
		Boolean isTokenValid = false;

		try{
			isUsernameValid = username.equals(userDetails.getUsername());
			isTokenValid = !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}

		return (isUsernameValid && isTokenValid);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
