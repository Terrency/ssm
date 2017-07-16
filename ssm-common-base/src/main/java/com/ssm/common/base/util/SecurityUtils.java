package com.ssm.common.base.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

public abstract class SecurityUtils {

    private static final int DEFAULT_ITERATIONS = 1;

    public static String generateRandomNumber() {
        return SecurityUtils.generateRandomNumber(new byte[0]);
    }

    public static String generateRandomNumber(byte[] seed) {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        if (seed != null && seed.length > 0) {
            randomNumberGenerator.setSeed(seed);
        }
        return randomNumberGenerator.nextBytes().toHex();
    }

    public static String generateMd5Hash(Object source, Object salt) {
        return SecurityUtils.generateMd5Hash(source, salt, DEFAULT_ITERATIONS);
    }

    public static String generateMd5Hash(Object source, Object salt, int hashIterations) {
        return new Md5Hash(source, salt, hashIterations).toString();
    }

}
