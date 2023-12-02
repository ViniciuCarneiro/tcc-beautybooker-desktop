package com.beautybooker.beautybooker.config;

import com.beautybooker.beautybooker.models.bean.AcessToken;
import com.beautybooker.beautybooker.models.bean.Profissional;
import com.beautybooker.beautybooker.models.bean.ProfissionalClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtDecoder {

    private final static SecretKey CHAVE = Keys.hmacShaKeyFor("minhachavesecretadotokenjwtdaapidotcc".getBytes(StandardCharsets.UTF_8));

    public static AcessToken getTokenDecoded(String jwtToken) {
        Claims claims = getClaimsFromToken(jwtToken);

        LinkedHashMap<String, Object> profissionalClaims = claims.get("profissional", LinkedHashMap.class);

        ProfissionalClaims profissional = new ObjectMapper().convertValue(profissionalClaims, ProfissionalClaims.class);

        Integer iat = claims.get("iat", Integer.class);
        Integer exp = claims.get("exp", Integer.class);

        AcessToken acessToken = new AcessToken();
        acessToken.setProfissional(new Profissional(profissional.getId(), profissional.getNome(), profissional.getSobrenome(), profissional.getEmail(), profissional.getAdmin()));
        acessToken.setIat(iat);
        acessToken.setExp(exp);

        return acessToken;
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(CHAVE)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        getTokenDecoded("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9maXNzaW9uYWwiOnsibm9tZSI6IkFuYSIsInNvYnJlbm9tZSI6IlBlcmVpcmEiLCJlbWFpbCI6ImFuYV9wcm9maXNzaW9uYWxAZW1haWwuY29tIiwiYWRtaW4iOjB9LCJpYXQiOjE2OTk0OTI2MTMsImV4cCI6MTY5OTQ5NjIxM30.hXIE92-VMer-T2QpYeWXqxBZvEo4_PyWA17gTzE1Xj8");
    }
}




