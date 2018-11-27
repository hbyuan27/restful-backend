package com.hbyuan.demo.config.authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	public static final String SCHEME_BEARER = "Bearer ";

	private static final String KEY_CLAIM_ROLES = "roles";

	private static final String KEY_HEADER_ALG = "alg";

	private static final String KEY_HEADER_TYPE = "typ";

	private final SignatureAlgorithm ALG = SignatureAlgorithm.HS512;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(UserDetails user) {
		Set<String> authorities = AuthorityUtils
				.authorityListToSet(user.getAuthorities());
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(KEY_CLAIM_ROLES, JSON.toJSON(authorities));
		Map<String, Object> header = new HashMap<String, Object>();
		header.put(KEY_HEADER_ALG, ALG.getValue());
		header.put(KEY_HEADER_TYPE, "JWT");
		Date iat = new Date();
		Date exp = new Date(iat.getTime() + expiration * 1000);
		return Jwts.builder().setHeader(header).setClaims(claims)
				.setSubject(user.getUsername())
				.setId(UUID.randomUUID().toString()).setIssuedAt(iat)
				.setExpiration(exp).signWith(ALG, getSecret()).compact();
	}

	public String getUsernameFromToken(String token) {
		Claims claims = parseToken(token);
		return (claims == null) ? null : claims.getSubject();
	}

	public boolean isValid(String token) {
		Claims claims = parseToken(token);
		// claims 有值, 即可证明 token 合法且有效
		if (claims != null) {
			// String id = claims.getId();
			// TODO 查询 id 表, 虽然 token 在有效期内, 但用户点击了 logout, 则 token 视为失效
			// if (!hasLoggedOut) {
			return true;
			// }
		}
		return false;
	}

	private Claims parseToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(getSecret())
					.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private byte[] getSecret() {
		return Base64Utils.encode(secret.getBytes());
	}

}