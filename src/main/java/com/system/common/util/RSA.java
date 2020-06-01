package com.system.common.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static javax.crypto.Cipher.*;

/**
 * @Author tivnan
 * @Date 2020/6/1 15:39
 * @Version 1.0
 */
public class RSA {


    /**
     * 对原文进行加密，获得密文
     *
     * @param transformation 加密模式
     * @param privateKey     私钥
     * @param input          原文
     * @return 返回加密后得到的密文
     * @throws Exception
     */
    public static String encryptRSA(String transformation, Key privateKey, String input) throws Exception {

        //创建加密对象
        Cipher cipher = getInstance(transformation);

        //加密初始化
        cipher.init(ENCRYPT_MODE, privateKey);

        //密文
        byte[] encryptBytes = cipher.doFinal(input.getBytes());

        return Base64.encode(encryptBytes);
    }

    /**
     * 对密文进行解密
     *
     * @param transformation 加密模式
     * @param publicKey      公钥
     * @param encrypted      密文
     * @return 返回解密后得到的明文
     * @throws Exception
     */
    public static String decryptRSA(String transformation, Key publicKey, String encrypted) throws Exception {

        //创建加密对象
        Cipher cipher = getInstance(transformation);

        //加密初始化
        cipher.init(DECRYPT_MODE, publicKey);

        //密文
        byte[] decryptBytes = cipher.doFinal(Base64.decode(encrypted));

        return new String(decryptBytes);
    }

    /**
     * 通过公钥文件，生成publicKey
     *
     * @param algorithm 使用的加密解密算法
     * @param pubPath   公钥文件所在路径
     * @return 返回一个publicKey的对象
     * @throws Exception
     */
    public static PublicKey getPublicKey(String algorithm, String pubPath) throws Exception {

        String publicKeyString = FileUtils.readFileToString(new File(pubPath), Charset.defaultCharset());

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));

        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 通过私钥文件，生成PrivateKey
     *
     * @param algorithm 使用的加密解密算法
     * @param priPath   公钥文件所在路径
     * @return 返回一个privateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String algorithm, String priPath) throws Exception {
        //读取私钥文件，获得密钥字符串
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());

        //获得密钥工厂对象
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        //通过密钥字符串，获得加密规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));

        //通过加密规则，生成对应的privateKey
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 用于生成RSA密钥
     *
     * @param algorithm 使用的加密算法
     * @param pubPath   生成的公钥的路径
     * @param priPath   生成的私钥的路径
     * @throws Exception
     */
    public static void keyGennerateToFile(String algorithm, String pubPath, String priPath) throws Exception {
        //根据算法获取密钥对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

        //生成密钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //获得密钥中的私钥
        PrivateKey privateKey = keyPair.getPrivate();

        //获得密钥中的公钥
        PublicKey publicKey = keyPair.getPublic();

        //获得私钥字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();

        //获得公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();

        //赋值各一个String数组，[0]为私钥，[1]为公钥
        String[] keyPairStrings = new String[2];
        keyPairStrings[0] = Base64.encode(privateKeyEncoded);
        keyPairStrings[1] = Base64.encode(publicKeyEncoded);

        //保存到文件
        FileUtils.writeStringToFile(new File(priPath), keyPairStrings[0], Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(pubPath), keyPairStrings[1], Charset.forName("UTF-8"));
    }
}
