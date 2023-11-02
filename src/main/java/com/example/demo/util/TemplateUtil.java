package com.example.demo.util;


import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.Map;

public class TemplateUtil {

    public static Map<String, String> prepareTemplateInfo(User user) {
        return Map.of(
                "name", user.getFirstName(),
                "currentTime", DateUtil.formatter(LocalDateTime.now()));
    }

    public static Map<String, String> prepareTemplateInfoResetPassword(User user, String link) {
        return Map.of(
                "name", user.getFirstName(),
                "link", link);
    }
}
