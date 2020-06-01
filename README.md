## RSA加密解密算法及后端使用指南
### 前提

- 在做一个微信小程序项目时，想要实现密文传输，于是写了个基于java的RSA加密解密工具类
- 这是个Maven工程
- 这是后端部分的加密解密，前端还没写

### 使用方法

tips：可以在RSATest里看见用法

- 在你的maven工程的pom.xml文件里添加`common-io`的依赖

  ```java
  <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.6</version>
  </dependency>
  ```

- 在你想要进行加密的地方，导入RSA工具类

  ```java
  import static com.system.common.util.RSA.*;
  ```

- 生成密钥

  ```java
  //第一个参数表示所使用加密解密算法
  //第二个参数表示生成的公钥文件的路径，这里生成在src/main/resources下，命名为a.pub
  //第三个参数表示生成的私钥文件的路径.这里生成在src/main/resources下，命名为a.pri
  keyGennerateToFile("RSA","src/main/resources/a.pub","src/main/resources/forBackEnd.pri");
  ```
  
- 然后通过密钥文件获取密钥

  ```java
  //获得forBackEnd的公钥私钥
  PrivateKey privateKey=getPrivateKey(algorithm,"src/main/resources/a.pri");
  
  PublicKey publicKey = getPublicKey(algorithm,"src/main/resources/a.pub");
  ```

- 使用私钥加密

  input是未加密的要发送给前端的信息，String类型，

  ```java
  String encryptRSA = encryptRSA(transformation, privateKey, input);
  ```

- 使用私钥进行解密

  encryptRSA是前端发送来的，已加密的信息

  ```java
  String decryptRSA = decryptRSA(transformation, privateKey, encryptRSA);
  ```