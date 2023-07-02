package com.example.Back.service;


import com.example.Back.repository.SocialMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class MemberDetailServiceImpl implements UserDetailsService{
    @Autowired
    private SocialMemberRepository socialMemberRepository;

    @SneakyThrows
    @Override
    public CurrentUserDetails loadUserByUsername(String socialId) {
        return socialMemberRepository.findBySocialId(socialId)
                .map(u -> new CurrentUserDetails(u, Collections.singleton(new SimpleGrantedAuthority("USER"))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with socialId: " + socialId));
    }
}
