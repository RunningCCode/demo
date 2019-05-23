package util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;

/**
 * @author chenzhicong
 * @time 2019/5/21 10:55
 * @description 提供了一些公共的编解码实现，比如Base64, Hex, MD5,Phonetic and URLs等等。
 */
public class CodecStudy {
    /**
     * Base64加解密
     */
    public void testBase64(){
        System.out.println("==========Base64==========");
        //假设密码为123456abc+
        byte[] passwd = "123456abc+".getBytes();
        Base64 base64 = new Base64();
        //加密
        String enPasswd = base64.encodeAsString(passwd);
        System.out.println("加密后：" + enPasswd);
        //解密
        String dePasswd = new String(base64.decode(enPasswd));
        System.out.println("解密后：" + dePasswd);
    }
    /**
     * MD5的加密
     */
    public void testMD5(){
        System.out.println("==========MD5==========");
        String enPasswd = DigestUtils.md5Hex("123456abc+");
        System.out.println("加密后：" + enPasswd);
    }
    /**
     * 对url进行编码解码
     */

    public void testURLCodec(){
        System.out.println("==========URLCodec==========");
        URLCodec urlCodec = new URLCodec();
        String data = "陈某某abc+";
        try {
            String encode = urlCodec.encode(data, "UTF-8");
            System.out.println("加密后：" + encode);
            String decode = urlCodec.decode(encode, "UTF-8");
            System.out.println("解密后：" + decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
