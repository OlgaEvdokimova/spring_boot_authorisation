package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;

public class Utility {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    public static boolean isConfirmed(String password, String confirmPassword) {
        if (!StringUtils.equals(password, confirmPassword)) {
            throw new BadCredentialsException("Passwords don't match");
        }
        return true;
    }
}
