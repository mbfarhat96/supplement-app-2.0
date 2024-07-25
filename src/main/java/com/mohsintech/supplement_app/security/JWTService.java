package com.mohsintech.supplement_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/*
    This is a class that serves TWO functions as a service to generate a JWT for a user after verifying that they're credentials match
    what's in our database. And it also validates whether a token sent in by a user is valid or not.
 */
@Service
public class JWTService {
    //example secret key, NOT GOOD PRACTICE!
    private final String secret_key = "DHSABDUABOWBNOXASXAOUIXNAXNBSAOUBRI3YEGR938Q27GHR3B98B19RD";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        //creating actual token.
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 10))//token expires after 3 minutes
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //generate key to sign the token with.
    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //once a user is sending a subsequent request, this method is called from a JWT filter.
    //validate tokens username against server retrieved username && make sure token isn't expired.
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
    //claims must be extracted to actually validate them against server retrieved user info.
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);

    }
    //extract the user from TOKEN.
    public String extractUsername(String token) {
        return
                extractClaim(token,Claims::getSubject);
    }
    //extract expiration date from the TOKEN.
    private Date extractExpirationDate(String token){
        return
                extractClaim(token,Claims::getExpiration);
    }
    //check whether TOKEN DATE is expired or not, meaning is it past the current date or before. Returns false if token
    //is valid
    private boolean isExpired(String token){
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }




}
