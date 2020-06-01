package com.system.common.util;

import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static com.system.common.util.RSA.*;

/**
 * @Author tivnan
 * @Date 2020/6/1 16:09
 * @Version 1.0
 */
public class RSATest {

    //使用加密解密模式
    public String algorithm = "RSA";

    //使用的加密解密模式
    public String transformation = "RSA";

    //原文
    public String input = "Hello World!";

    /**
     * 生成密钥，
     *
     * @throws Exception
     */
    @Test
    public void gennerateKeyTest() throws Exception {

        keyGennerateToFile(algorithm, "src/main/resources/forFrontEnd.pub", "src/main/resources/forBackEnd.pri");

    }

    /**
     * 模拟后端加密信息，前端解密信息的过程
     *
     * @throws Exception
     */
    @Test
    public void RSATest() throws Exception {
        //获得forBackEnd的公钥私钥
        PrivateKey privateKey = getPrivateKey(algorithm, "src/main/resources/forBackEnd.pri");
        PublicKey publicKey = getPublicKey(algorithm, "src/main/resources/forFrontEnd.pub");

        //模拟后端，使用私钥对原文input加密
        String encryptRSA = encryptRSA(transformation, privateKey, input);

        //模拟前端，使用公钥对密文encrypt解密
        String decryptRSA = decryptRSA(transformation, publicKey, encryptRSA);

        System.out.println("原文:" + input);
        System.out.println("密文:" + encryptRSA);
        System.out.println("解密:" + decryptRSA);
    }

    @Test
    public void RSATest2() throws Exception {
        //获得forBackEnd的公钥私钥
        PrivateKey privateKey = getPrivateKey(algorithm, "src/main/resources/forBackEnd.pri");
        PublicKey publicKey = getPublicKey(algorithm, "src/main/resources/forFrontEnd.pub");

        //模拟前端，使用公钥对原文进行加密
        String encryptRSA = encryptRSA(transformation, publicKey, input);

        //模拟后端，使用私钥对密文encrypt解密
        String decryptRSA = decryptRSA(transformation, privateKey, encryptRSA);

        System.out.println("原文:" + input);
        System.out.println("密文:" + encryptRSA);
        System.out.println("解密:" + decryptRSA);
    }
}