package com.example.Back.repository;

import com.example.Back.domain.Member;

import java.security.NoSuchAlgorithmException;

public interface ProxyRepository {
    Member getMemberData(String accessToken) throws NoSuchAlgorithmException;
}