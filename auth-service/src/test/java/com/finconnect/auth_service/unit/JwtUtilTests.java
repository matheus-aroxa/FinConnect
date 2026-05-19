package com.finconnect.auth_service.unit;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import com.finconnect.auth_service.exception.exceptions.ExpiredTokenException;
import com.finconnect.auth_service.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilTests {
    
    private JwtUtil jwtUtil;
    private final String SECRET_TOKEN = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private final int EXPIRATION_TOKEN = 3600000;
    private final String SECRET_REFRESH_TOKEN = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private final int EXPIRATION_REFRESH_TOKEN = 360000000;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", SECRET_TOKEN);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", EXPIRATION_TOKEN);
        ReflectionTestUtils.setField(jwtUtil, "jwtRefreshSecret", SECRET_REFRESH_TOKEN);
        ReflectionTestUtils.setField(jwtUtil, "jwtRefreshExpirationMs", EXPIRATION_REFRESH_TOKEN);
        jwtUtil.init();
    }

    @Test
    void shouldGenerateValidToken() {
        String username = "test";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(jwtUtil.validateJwtToken(token));
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void shouldGenerateValidRefreshToken() {
        String username = "test";
        String token = jwtUtil.generateRefreshToken(username);

        assertNotNull(token);
        assertTrue(jwtUtil.validateRefreshToken(token));
        assertEquals(username, jwtUtil.getUsernameFromRefreshToken(token));
    }

    @Test
    void shouldExtractCorrectDates() {
        String token = jwtUtil.generateToken("test");
        Date issuedAt = jwtUtil.getIssueDateFromToken(token);
        Date expiresAt = jwtUtil.getExpirationDateFromToken(token);

        assertNotNull(issuedAt);
        assertNotNull(expiresAt);
        assertTrue(expiresAt.after(issuedAt));
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        assertFalse(jwtUtil.validateJwtToken("not a valid token"));
    }

    @Test
    void shouldReturnFalseForInvalidRefreshToken() {
        assertFalse(jwtUtil.validateRefreshToken("not a valid token"));
    }

    @Test
    void shouldThrowExpiredTokenExceptionWhenTokenIsExpired() {
        String expiredToken = Jwts.builder()
            .setSubject("test")
            .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
            .setExpiration(new Date(System.currentTimeMillis() - 5000))
            .signWith((javax.crypto.SecretKey)ReflectionTestUtils.getField(jwtUtil, "key"), SignatureAlgorithm.HS256)
            .compact();

        assertThrows(ExpiredTokenException.class, () -> jwtUtil.validateJwtToken(expiredToken));
    }

    @Test
    void shouldReturnFalseWhenSignatureIsInvalid() {
        String token = jwtUtil.generateToken("test");

        String invalidSignatureToken = token + "modified";

        assertFalse(jwtUtil.validateJwtToken(invalidSignatureToken));
    }

    @Test
    void shouldReturnFalseWhenTokenIsMalformed() {
        assertFalse(jwtUtil.validateJwtToken("nota.jwt.token"));
    }

    @Test
    void shouldReturnFalseWhenTokenIsEmpty() {
        assertFalse(jwtUtil.validateJwtToken(""));
    }
}
