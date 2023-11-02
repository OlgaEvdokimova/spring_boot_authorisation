package com.example.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenProperties {

    private String jwtSecretAccess;
    private String jwtSecretRefresh;
    private long jwtAccessExpiration;
    private  long jwtRefreshExpiration;
}
