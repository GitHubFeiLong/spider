package com.cfl.myproject.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @ClassName PasswordEncryptionTest
 * @Author msi
 * @Date 2020/5/30 15:28
 * @Version 1.0
 */
public class PasswordEncryptionTest {
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String str1 = PasswordEncryption.generateSalt();
        str1 = "3d06085176e1fad41ea8046dea17586b";
        System.out.println("str1 = " + str1);
        System.out.println(PasswordEncryption.getEncryptedPassword("123456", str1));
        System.out.println("9da2350c3934fb9a7bc78d42f68edead32df21cad475f2f95d38b5a4340ea151210aada2c66f4e049a4c5d36784d5ca1a5f30f0ca16ffbc3ad8a8d6b784851e4"
        .equals(PasswordEncryption.getEncryptedPassword("123456", str1)));
    }
}
