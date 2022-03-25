package com.aliyunidaas.sync.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * JWE工具类
 *
 * @author hatterjiang
 */
public class JweUtil {
    private static final String AES = "AES";

    public static String decrypt(String cipherData, String key) throws JoseException, DecoderException {
        final SecretKeySpec secretKeySpec = new SecretKeySpec(Hex.decodeHex(key), AES);
        final JsonWebKey jsonWebKey = JsonWebKey.Factory.newJwk(secretKeySpec);
        final JsonWebEncryption receiverJwe = new JsonWebEncryption();
        final AlgorithmConstraints algConstraints = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, "dir");
        receiverJwe.setAlgorithmConstraints(algConstraints);
        final AlgorithmConstraints encConstraints = new AlgorithmConstraints(
                AlgorithmConstraints.ConstraintType.PERMIT, "A256GCM", "A192GCM", "A128GCM");
        receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);
        receiverJwe.setKey(jsonWebKey.getKey());
        receiverJwe.setCompactSerialization(cipherData);
        return new String(receiverJwe.getPlaintextBytes(), StandardCharsets.UTF_8);
    }
}
