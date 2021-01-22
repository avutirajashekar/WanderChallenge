package com.wander.utility;

import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public boolean checkCookie(String cookieName, Cookie[] cookies) {
        boolean cookieFlag = false;
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equals(cookie.getName())) {
                        cookieFlag = true;
                        break;
                    }
                }
            } else {
                cookieFlag = false;
            }
        } catch (Exception e) {
            System.out.println("Exception in checkCookie method of CookieManager :: " + e.getMessage());
        }
        return cookieFlag;
    }

    public boolean checkCookie(String cookieName, HttpServletRequest request) {
        boolean cookieFlag = false;
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equalsIgnoreCase(cookie.getName())) {
                        cookieFlag = true;
                        break;
                    }
                }
            } else {
                cookieFlag = false;
            }
        } catch (Exception e) {
            System.out.println("Exception in checkCookie method of CookieManager :: " + e.getMessage());
        }

        return cookieFlag;
    }

    public String getCookie(String cookieName, HttpServletRequest request) {
        String ret = "";
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equals(cookie.getName())) {
                        ret = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        //ret=cookie.getValue();
                        break;
                    } else {
                        ret = "nocookie";
                    }
                }
            } else {
                ret = "nocookie";
            }
        } catch (Exception e) {
            System.out.println("Exception in getCookie method of CookieManager :: " + e.getMessage());
        }
        return ret;
    }

    public void deleteCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookieName.equals(cookie.getName())) {
                        cookie.setValue(null);
                        cookie.setMaxAge(0);
//                        cookie.setDomain(domainName);
                        cookie.setPath(request.getContextPath() + "/");
                        response.addCookie(cookie);
                        break;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Exception in deleteCookie method of CookieManager :: " + e.getMessage());
        }
    }

    public void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath() + "/");
                    response.addCookie(cookie);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in deleteAllCookies method of CookieManager :: " + e.getMessage());
        }
    }

    public boolean setCookie(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response, int maxAge) {
        boolean cookieFlag = false;

        //System.out.println("Cookie Name ;: " + cookieName + " :: " + cookieValue);
        try {
            Cookie cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue, "UTF-8"));
            //cookie1.setSecure(true);
            //cookie1.setHttpOnly(true);
            //System.out.println("Cookie Name after creation;: " + cookieName + " :: " + cookieValue);
            if (maxAge > 0) {
                cookie.setMaxAge(maxAge);
            } else {
                cookie.setMaxAge(-1);
            }
            cookie.setPath(request.getContextPath() + "/");
            response.addCookie(cookie);
        } catch (Exception e) {
            System.out.println("Exception in setCookie method of CookieManager :: " + e.getMessage());
        }
        return cookieFlag;
    }
}
