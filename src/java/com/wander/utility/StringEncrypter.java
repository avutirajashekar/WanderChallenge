package com.wander.utility;

import java.nio.charset.Charset;
import org.springframework.stereotype.Repository;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//import org.apache.commons.codec.binary.Base64;

@Repository
public class StringEncrypter {

    private static final String UNICODE_FORMAT = "UTF8";

    public StringEncrypter() {
        super();
    }

    public String encrypt(String unencryptedString) throws EncryptionException {

        if (unencryptedString == null || unencryptedString.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "unencrypted string was null or empty");
        }
        try {
            byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
            BASE64Encoder base64encoder = new BASE64Encoder();

            return base64encoder.encode(cleartext);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    public String decrypt(String encryptedString) throws EncryptionException {

        if (encryptedString == null || encryptedString.trim().length() <= 0) {
            throw new IllegalArgumentException("encrypted string was null or empty");
        }
        try {
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] cleartext = base64decoder.decodeBuffer(encryptedString);

            return bytes2String(cleartext);

        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    public static class EncryptionException extends Exception {

        public EncryptionException(Throwable t) {
            super(t);
        }
    }

    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    public String encodeMessage(String s) {
        String out = null;
        try {
            byte[] data = s.getBytes("UTF-8");
//            out = new String(Base64.encodeBase64(data));
//            out = out.trim();
        } catch (Exception e) {
            return null;
        }
        return out;
    }

    public String decodeMessage(String s) {
        String out = null;
        try {
//            byte[] bytesEncoded = Base64.decodeBase64(s.getBytes());
//            out = new String(bytesEncoded, Charset.forName("UTF8"));
        } catch (Exception e) {
            return null;
        }
        return out;
    }
}
