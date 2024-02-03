package com.nitconf.backend.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.nitconf.backend.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Value("nitconf")
    private String jwtCookie;

    @Value("29ce3084da6f2368800ff6a99bc2c4715087fc092caa2d2c06f0a886e9b1ddfb")
    private String jwtSecret;

    @Value("86400000")
    private int jwtExpirationMs;

    @SuppressWarnings("null")
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie =WebUtils.getCookie(request, jwtCookie) ;
        if(cookie != null){
            return cookie.getValue();

        }else{
            return null;
        }
    }
    @SuppressWarnings("null")
    public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails){
            String jwt= generateTokenFromUsername(userDetails.getEmail());
            ResponseCookie cookie=ResponseCookie.from(jwtCookie,jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
            return cookie;
    }
    @SuppressWarnings("null")
    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie=ResponseCookie.from(jwtCookie,null).path("/api").maxAge(24*60*60).httpOnly(true).build();
        return cookie;
    }
    
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUsernameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String jwt) {
        
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(jwt);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Invalid Jwt :{}", e.getMessage());
        }catch(ExpiredJwtException e){
            logger.error("Jwt token is expired :{}", e.getMessage());
        }catch(UnsupportedJwtException e){
            logger.error(" Jwt not supported :{}", e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("Jwt says string is empty :{}", e.getMessage());
        }
        return false;
    }

    public String generateTokenFromUsername(String username) {   
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
  }

  @SuppressWarnings("null")
public ResponseCookie deleteJwtCookie(){
    return ResponseCookie.from(jwtCookie,"").path("/api").maxAge(0).httpOnly(true).build();
  }
}