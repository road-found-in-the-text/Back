package com.example.Back.service;


import com.example.Back.domain.LoginType;
import com.example.Back.domain.Member;
import com.example.Back.dto.response.ApplePublicKeyRes;
import com.example.Back.repository.ProxyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppleService implements ProxyRepository {

    @Autowired
    private final WebClient webClient;

    @Override
    public Member getMemberData(String identityToken) {

        ApplePublicKeyRes applePublicKeyRes = webClient.get()
                .uri("https://appleid.apple.com/auth/keys")
                .retrieve()
                .bodyToMono(ApplePublicKeyRes.class)
                .block();

        String appleSocialId = null;

        String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));

        Map<String, String> header = null;
        try {
            header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken)), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("kid" + header.get("kid"));

        ApplePublicKeyRes.Key key = applePublicKeyRes.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

        byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(key.getKty());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
        PublicKey publicKey = null;
        try {
            publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            invalidKeySpecException.printStackTrace();
        }

        Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();

        JsonObject userInfoObject = null;

        userInfoObject = new Gson().fromJson(new Gson().toJson(claims), JsonObject.class);

        JsonElement appleAlg = userInfoObject.get("sub");
        appleSocialId = appleAlg.getAsString();
        System.out.println("this is " + appleSocialId);

        return Member.builder()
                .socialId(String.valueOf(appleSocialId))
                .loginType(LoginType.Apple)
                .build();
    }


}