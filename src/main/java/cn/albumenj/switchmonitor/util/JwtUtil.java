package cn.albumenj.switchmonitor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * JWT工具
 *
 * @author Albumen
 */
@Component
public class JwtUtil {
    @Value("${security.jwtexp}")
    Integer expTime;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private Algorithm algorithmRS;

    public JwtUtil() {
        publicKey = RsaKeyUtil.getInstance().getPublicKey();
        privateKey = RsaKeyUtil.getInstance().getPrivateKey();
        algorithmRS = Algorithm.RSA256(publicKey, privateKey);
    }

    public String create(String userName) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                    .sign(algorithmRS);
        } catch (JWTCreationException exception) {
            return null;
        }
        return token;
    }

    public String create(String userName, String[] claim) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                    .withArrayClaim("Claim", claim)
                    .sign(algorithmRS);
        } catch (JWTCreationException exception) {
            return null;
        }
        return token;
    }

    public boolean verify(String token, String userName) {
        try {
            JWTVerifier verifier = JWT.require(algorithmRS)
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String[] verifyWithPermission(String token, String userName) {
        try {
            JWTVerifier verifier = JWT.require(algorithmRS)
                    .withIssuer("Albumen")
                    .withSubject(userName)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("Permission").asArray(String.class);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String[] verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithmRS)
                    .withIssuer("Albumen")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("Claim").asArray(String.class);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
