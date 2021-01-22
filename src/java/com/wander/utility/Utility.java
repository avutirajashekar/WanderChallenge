package com.wander.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rajashekar avuti
 */
@Component
public class Utility {

    @Autowired
    public StringEncrypter stringEncrypter;

    public String regex = "\\s+$";

    public String getEncodedId(int userId) {
        String encodeValue = null;
        try {
            encodeValue = stringEncrypter.encrypt(String.valueOf(userId));
        } catch (Exception e) {
            System.err.println("Exception at getEncodedId() in Utility.java ::: " + e.getMessage());
        }
        return encodeValue;
    }

    public String getEncodedId(String inputStr) {
        String encodeValue = null;
        try {
            encodeValue = stringEncrypter.encrypt(inputStr);
        } catch (Exception e) {
            System.err.println("Exception at getEncodedId(String) in Utility.java ::: " + e.getMessage());
        }
        return encodeValue;
    }

    public String getDecodeId(String encryptId) {
        String decodeValue = null;
        try {
            decodeValue = stringEncrypter.decrypt(encryptId);
        } catch (Exception e) {
            System.err.println("Exception at getDecodeId() in Utility.java ::: " + e.getMessage());
        }
        return decodeValue;
    }

    public String getStringEncode(String value) {
        String encodeValue = null;
        try {
            encodeValue = stringEncrypter.encrypt(value);
        } catch (Exception e) {
            System.err.println("Exception at getStringEncode() in Utility.java ::: " + e.getMessage());
        }
        return encodeValue;
    }

    public boolean validateMobileNumber(String mobileNumber) {
        Pattern p = Pattern.compile("^[6-9]\\d{9}$");//. represents single character  
        Matcher m = p.matcher(mobileNumber);
        boolean isValid = m.matches();
        return isValid;
    }

    public boolean validateEmail(String email) {
        Pattern p = Pattern.compile("^[\\w\\-\\.\\+]+\\@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z0-9]{2,4}$");//. represents single character  
        Matcher m = p.matcher(email);
        boolean isValid = m.matches();
        return isValid;
    }

    public String dateLongToString(int intDate) {
        String dateStr = "";
        Date date = null;
        DateFormat dateFormat = null;
        long longDate = Long.valueOf(intDate) * 1000;
        try {
            date = new Date(longDate);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            dateStr = dateFormat.format(date);
        } catch (Exception e) {
        }
        return dateStr;
    }
}
