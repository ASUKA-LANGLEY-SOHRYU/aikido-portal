package com.prosvirnin.trainersportal.service.auth;

import com.prosvirnin.trainersportal.exception.api.InvalidJWTTokenException;
import com.prosvirnin.trainersportal.model.domain.jwt.Token;
import com.prosvirnin.trainersportal.model.domain.jwt.TokenPair;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultJWTService implements JWTService {

    @Value("${service.jwt.secret}")
    private String SECRET_KEY;

    @Value("${service.jwt.refresh.expirationDays}")
    private long refreshExpirationDays;

    @Value("${service.jwt.access.expirationMinutes}")
    private long accessExpirationMinutes;

    private final UserRepository userRepository;


    @Override
    public TokenPair generateTokenPair(User user) {
        return TokenPair.of(generateRefreshToken(user), generateAccessToken(user));
    }

    private String generateAccessToken(User user) {
        long ACCESS_TOKEN_EXPIRATION_MULTIPLIER = 60 * 1000; //Minute in milliseconds
        long accessExpirationMillis = accessExpirationMinutes * ACCESS_TOKEN_EXPIRATION_MULTIPLIER;
        return Jwts.builder()
                .subject(user.getLogin())
                .claim("roles", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationMillis))
                .signWith(secretKey())
                .compact();
    }

    private String generateRefreshToken(User user) {
        long REFRESH_TOKEN_EXPIRATION_MULTIPLIER = 24 * 60 * 60 * 1000; //Day in milliseconds
        long refreshExpirationMillis = refreshExpirationDays * REFRESH_TOKEN_EXPIRATION_MULTIPLIER;
        return Jwts.builder()
                .subject(user.getLogin())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationMillis))
                .signWith(secretKey())
                .compact();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    @Override
    public boolean isValid(Token token) {
        Claims payload;
        try {
            payload = getClaims(token);
        } catch (InvalidJWTTokenException e) {
            return false;
        }
        String username = payload.getSubject();
        if (userRepository.findByLogin(username).isEmpty())
            return false;
        return !payload.getExpiration().before(new Date());
    }

    @Override
    public String getSubject(Token token) {
        Claims payload = getClaims(token);
        return payload.getSubject();
    }

    private Claims getClaims(Token token) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey())
                    .build()
                    .parseSignedClaims(token.getToken());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Failed to parse jwt token: ", e);
            throw new InvalidJWTTokenException();
        }
        return claims.getPayload();
    }
}