package org.microdiamond.server.auth.services;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.build.JwtSignatureBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.microdiamond.server.auth.beans.UserInfo;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class JWTService {

    @ConfigProperty(name = "jwt.primaryKey.pem")
    String primaryKeyPem;

    @ConfigProperty(name = "jwt.iss")
    String jwtIss;

    @ConfigProperty(name = "jwt.expiration.seconds")
    long expirationSeconds;

    @ConfigProperty(name = "jwt.expiration.appuser.seconds")
    long appUserExpirationSeconds;

    public String generateTokenString(UserInfo userInfo) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey pk = getPrivateKey();
        return generateTokenString(pk, userInfo);
    }

    private String generateTokenString(PrivateKey privateKey, UserInfo userInfo) {
        JwtClaimsBuilder claimsBuilder = getJwtClaimsBuilder(userInfo);
        return getSignedJWT(claimsBuilder, privateKey);
    }

    private JwtClaimsBuilder getJwtClaimsBuilder(UserInfo userInfo) {
        JwtClaimsBuilder claims = Jwt.claims().
            claim(Claims.iss.name(), jwtIss).
            claim(Claims.sub.name(), userInfo.getUsername()).
            claim(Claims.birthdate.name(), userInfo.getBirthdate().getTime() / 1000).
            claim(Claims.groups.name(), userInfo.getRoles());
        long currentTimeInSecs = currentTimeInSecs();
        long expirationSecs = userInfo.isAppUser() ? appUserExpirationSeconds : expirationSeconds;
        claims.issuedAt(currentTimeInSecs);
        claims.expiresAt(currentTimeInSecs + expirationSecs);
        return claims;
    }

    private String getSignedJWT(JwtClaimsBuilder claimsBuilder, PrivateKey privateKey) {
        JwtSignatureBuilder jws = claimsBuilder.jws();
        return jws.sign(privateKey);
    }

    private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = getPrivateKeyBytes();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private byte[] getPrivateKeyBytes() throws IOException {
        String privateKey = getPrivateKeyFromResources();
        String cleanedPrivateKey = cleanPrivateKeyString(privateKey);
        return Base64.getDecoder().decode(cleanedPrivateKey);
    }

    private String getPrivateKeyFromResources() throws IOException {
        InputStream stream = getClass().getResourceAsStream(primaryKeyPem);
        byte[] bytes = stream.readAllBytes();
        int length = bytes.length;
        return new String(bytes, 0, length, StandardCharsets.UTF_8);
    }

    private String cleanPrivateKeyString(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}