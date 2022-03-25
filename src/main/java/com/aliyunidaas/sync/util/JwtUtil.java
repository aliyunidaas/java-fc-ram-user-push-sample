package com.aliyunidaas.sync.util;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JWT工具类
 *
 * @author hatterjiang
 */
public class JwtUtil {
    private final static ConcurrentMap<String, JsonWebKeySet> IDAAS_SIGN_JWK_SET_MAP = new ConcurrentHashMap<>();

    /**
     * 通过JWK URL创建JWT Consumer，来用解析和验证JWT
     */
    public static JwtConsumer createJwtConsumerFromUrl(String jwkUrl, String appId) {
        try {
            final JsonWebKeySet jsonWebKeySet = getJsonWebKeySetByUrl(jwkUrl);
            return createJwtConsumer(jsonWebKeySet, appId);
        } catch (Exception e) {
            throw new RuntimeException("Fetch JWKs from url failed: " + e.getMessage() + ", " + jwkUrl, e);
        }
    }

    /**
     * 通过JWK URL创建JWT Consumer，来用解析和验证JWT
     */
    public static JwtConsumer createJwtConsumerFromJwkJson(String jwkJson, String appId) {
        try {
            final JsonWebKeySet jsonWebKeySet = getJsonWebKeySetByJwkJson(jwkJson);
            return createJwtConsumer(jsonWebKeySet, appId);
        } catch (Exception e) {
            throw new RuntimeException("Fetch JWKs from url failed: " + e.getMessage() + ", " + jwkJson, e);
        }
    }

    public static JwtConsumer createJwtConsumer(JsonWebKeySet jsonWebKeySet, String appId) {
        final JwtConsumerBuilder jwtConsumerBuilder = new JwtConsumerBuilder();
        jwtConsumerBuilder.setExpectedIssuer("urn:alibaba:idaas:app:event");
        jwtConsumerBuilder.setRequireExpirationTime();
        jwtConsumerBuilder.setRequireIssuedAt();
        jwtConsumerBuilder.setRequireExpirationTime();
        jwtConsumerBuilder.setAllowedClockSkewInSeconds(60);
        jwtConsumerBuilder.setExpectedAudience(appId);
        jwtConsumerBuilder.setVerificationKeyResolver((jws, nestingContext) -> {
            final String signKeyId = jws.getKeyIdHeaderValue();
            for (JsonWebKey jsonWebKey : jsonWebKeySet.getJsonWebKeys()) {
                if (StringUtil.equals(jsonWebKey.getKeyId(), signKeyId)) {
                    return jsonWebKey.getKey();
                }
            }
            throw new RuntimeException("Cannot find verification key: " + signKeyId);
        });
        return jwtConsumerBuilder.build();
    }

    synchronized private static JsonWebKeySet getJsonWebKeySetByUrl(String jwkUrlString) throws IOException, JoseException {
        JsonWebKeySet jsonWebKeySet = IDAAS_SIGN_JWK_SET_MAP.get(jwkUrlString);
        if (jsonWebKeySet == null) {
            jsonWebKeySet = innerGetJsonWebKeySetByUrl(jwkUrlString);
            IDAAS_SIGN_JWK_SET_MAP.put(jwkUrlString, jsonWebKeySet);
        }
        return jsonWebKeySet;
    }

    synchronized private static JsonWebKeySet getJsonWebKeySetByJwkJson(String jwkJson) throws IOException, JoseException {
        JsonWebKeySet jsonWebKeySet = IDAAS_SIGN_JWK_SET_MAP.get(jwkJson);
        if (jsonWebKeySet == null) {
            jsonWebKeySet = new JsonWebKeySet(jwkJson);
            IDAAS_SIGN_JWK_SET_MAP.put(jwkJson, jsonWebKeySet);
        }
        return jsonWebKeySet;
    }

    private static JsonWebKeySet innerGetJsonWebKeySetByUrl(String jwkUrlString) throws IOException, JoseException {
        final URL jwkUrl = new URL(jwkUrlString);
        final URLConnection urlConnection = jwkUrl.openConnection();
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(5000);
        final String jwkSetJson = new String(IoUtil.readAll(urlConnection.getInputStream()), StandardCharsets.UTF_8);
        return new JsonWebKeySet(jwkSetJson);
    }
}
