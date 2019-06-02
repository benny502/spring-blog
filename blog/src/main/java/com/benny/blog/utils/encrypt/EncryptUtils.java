package com.benny.blog.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


  
public class EncryptUtils{


    private static final Logger log = LoggerFactory.getLogger(EncryptUtils.class);

    public static enum ENCRYPTTYPE {
        MD5("MD5");
        private String name;

        private ENCRYPTTYPE(String name) {
            this.name = name;
        }

        public static String getName(String name) {
            for (ENCRYPTTYPE e : ENCRYPTTYPE.values()) {
                if(e.getName() == name) {
                    return e.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
        
        }catch(NoSuchAlgorithmException e){
            log.error(e.getMessage());
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i=0; i<bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if(tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
