package com.cfl.jd.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 类描述：
 * 生成6位数字的随机验证码
 * @ClassName VerCodeGenerateUtil
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 19:39
 * @Version 1.0
 */
public class VerCodeGenerateUtil {
    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    /**
     * 生成6位随机数字
     * @return 返回6位数字验证码
     */
    public static String generateVerCode() {
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}

