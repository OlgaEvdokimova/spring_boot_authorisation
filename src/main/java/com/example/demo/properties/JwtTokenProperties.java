package com.example.demo.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenProperties {

    private String jwtSecret;
    private long jwtAccessExpiration;
    private  long jwtRefreshExpiration;
}
