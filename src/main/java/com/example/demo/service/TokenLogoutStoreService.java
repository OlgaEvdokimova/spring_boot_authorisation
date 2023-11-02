package com.example.demo.service;

import com.example.demo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenLogoutStoreService {

    private final RefreshTokenRepository logoutStoreRepository;

    public void delete(String tokenId) {
        logoutStoreRepository.deleteById(tokenId);
    }
}
