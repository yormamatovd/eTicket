package ai.ecma.appeticketserver.security;

import ai.ecma.appeticketserver.exception.RestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expire.access_token}")
    private long accessTokenExpire;

    @Value("${jwt.expire.refresh_token}")
    private long refreshTokenExpire;

    public String generateToken(String phoneNumber, boolean forAccess) {
        Date expire = new Date(System.currentTimeMillis() + (forAccess ? accessTokenExpire : refreshTokenExpire));
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        return Jwts
                .builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .compact();
    }

    public String getUsername(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw RestException.forbidden();
        }
    }

    public void validateToken(String token){
         Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
