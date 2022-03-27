package com.platzi.platzimarket.web.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String KEY = "platzi";

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000))
                .signWith(SignatureAlgorithm.HS256, KEY )
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        System.out.println( userDetails.getUsername().equals(extractUsername(token))  + " - " + isTokenExpired(token));
        return userDetails.getUsername().equals(extractUsername(token)) && isTokenExpired(token);
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        System.out.println(getClaims(token).getExpiration() + " - " +(new Date(System.currentTimeMillis())) );
        return getClaims(token).getExpiration().after(new Date());
    }

    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();

    }
}
