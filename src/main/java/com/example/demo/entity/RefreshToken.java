package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@RedisHash("refresh_token")
public class RefreshToken {

    @Id
    private String id;
    private String refreshToken;

}
