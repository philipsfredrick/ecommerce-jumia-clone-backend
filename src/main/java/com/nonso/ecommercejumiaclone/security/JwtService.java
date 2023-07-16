package com.nonso.ecommercejumiaclone.config.security;

import com.nonso.ecommercejumiaclone.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nonso.ecommercejumiaclone.utils.GeneralConstants.*;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.token.secret_key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
       return parseLong(extractClaim(token, Claims::getId));
    }

    public String getUserAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("scope").toString();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateJwtToken(User user) {
     return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, User user) {
        List<String> scope = user.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).collect(toList());
        extraClaims.put("scope", scope) ;
        extraClaims.put("userId", user.getId());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            log.error("Invalid JWT token. Possibly expired");
            throw ex;
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Optional<String> retrieveJWT(HttpServletRequest request) {
        if (hasBearerToken(request)) {
            return Optional.of(retrieveToken(request).substring(7));
        }
        return Optional.empty();
    }

    public boolean hasBearerToken(HttpServletRequest request) {
        String token = retrieveToken(request);
        return StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX);
    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader(HEADER_STRING);
    }
}
