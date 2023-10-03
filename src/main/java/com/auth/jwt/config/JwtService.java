package com.auth.jwt.config;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth.jwt.auth.forms.TokenPairs;
import com.auth.jwt.auth.models.ResetToken;
import com.auth.jwt.auth.repositories.BlackListTokenRepository;
import com.auth.jwt.auth.repositories.ResetTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	@Value("${app.security.jwt.secret-key}")
	private String secretKey;

	@Value("${app.security.jwt.access.exp}")
	private Long accsessExp;
	@Value("${app.security.jwt.refresh.exp}")
	private Long refreshExp;
	@Value("${app.security.jwt.reset.exp}")
	private Long resetExp;

	private final BlackListTokenRepository blackListTokenRepository;

	private final ResetTokenRepository resetTokenRepository;

	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigninKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public TokenPairs generateTokenPair(UserDetails userDetails) {
		String uuid = UUID.randomUUID().toString();
		TokenPairs tokenPairs = new TokenPairs();
		tokenPairs.setAccess(generateAccessToken(userDetails, uuid));
		tokenPairs.setRefresh(generateRefreshToken(userDetails, uuid));
		return tokenPairs;
	}

	public String generateAccessToken(
			UserDetails userDetails,
			String uuid) {
		return generateToken(userDetails, uuid, accsessExp);
	}

	public String generateAccessTokenFromRefreshClaims(Claims claims) {
		claims.setIssuedAt(new Date(System.currentTimeMillis()));
		claims.setExpiration(new Date(System.currentTimeMillis() + (accsessExp * 1000)));
		return Jwts
				.builder()
				.setClaims(claims)
				.signWith(getSigninKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String generateRefreshToken(
			UserDetails userDetails,
			String uuid) {
		return generateToken(userDetails, uuid, refreshExp);
	}

	public String generateResetToken(
			UserDetails userDetails) {
		String id = UUID.randomUUID().toString();
		ResetToken resetToken = ResetToken.builder()
				.email(userDetails.getUsername())
				.id(id)
				.build();
		resetTokenRepository.save(resetToken);
		return generateToken(userDetails, id, resetExp);
	}

	private String generateToken(
			UserDetails userDetails,
			String uuid,
			Long exp) {
		return Jwts
				.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (exp * 1000)))
				.setId(uuid)
				.signWith(getSigninKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token) {
		return !isTokenExpired(token) && !isTokenBlackListed(token);
	}

	public boolean isResetTokenValid(String token) {
		if (isTokenExpired(token))
			return false;
		Claims claims = extractAllClaims(token);
		return resetTokenRepository.existsByIdAndEmail(claims.getId(), claims.getSubject());
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean isTokenBlackListed(String token) {
		String id = extractId(token);
		return blackListTokenRepository.existsById(id);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractId(String token) {
		return extractClaim(token, Claims::getId);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Key getSigninKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
