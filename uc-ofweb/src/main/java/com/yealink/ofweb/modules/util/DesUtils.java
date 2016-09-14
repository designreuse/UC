package com.yealink.ofweb.modules.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * Created with IntelliJ IDEA.
 * User: huangyx
 * Date: 14-9-24
 * Time: 上午9:39
 * To change this template use File | Settings | File Templates.
 */
public class DesUtils {
    private final static String iv = "01234567";
    private final static String encoding = "utf-8";
    public static final String key = "56212235226886758978225025933009171679";

    public static String encryptBase64(String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("utf-8"));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey= keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes("utf-8"));
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encodeBase64String(encryptData);
    }
    public static String decryptBase64(String encryptText) throws Exception {

        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("utf-8"));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes("utf-8"));
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decodeBase64(encryptText));
        return new String(decryptData, encoding);
    }
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("start:"+start);
        String strMing = "当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.当然是AES了，3DES的安全期限早就过了，你是要用硬件还是软件实现？硬件比如DSP,FPGA实现，速度非常快，软件实现就是速度慢。当然国内也有很多学术机构对AES实现做优化的，你可以参考下它们的paper.";
        String encodeStr = 	encryptBase64(strMing);
        System.out.println(encodeStr);
        System.out.println(decryptBase64(encodeStr));
        long end = System.currentTimeMillis();
        System.out.println("end:"+ end +",total:"+ (end - start));
    }
}
