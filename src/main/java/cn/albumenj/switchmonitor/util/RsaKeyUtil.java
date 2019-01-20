package cn.albumenj.switchmonitor.util;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


/**
 * 单例模式生成RSA
 * 该方法耗时较久
 *
 * @author Albumen
 */
public class RsaKeyUtil {
    private final static Logger logger = LoggerFactory.getLogger(RsaKeyUtil.class);
    private static RsaKeyUtil instance = new RsaKeyUtil();

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;


    public RsaKeyUtil() {
        try {
            loadPublicKey();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public static RsaKeyUtil getInstance() {
        return instance;
    }


    private void loadPublicKey() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/pem")));
        Security.addProvider(new BouncyCastleProvider());
        KeyPair kp = (KeyPair) new PEMReader(br).readObject();

        this.privateKey = (RSAPrivateKey) kp.getPrivate();
        this.publicKey = (RSAPublicKey) kp.getPublic();
    }
}
