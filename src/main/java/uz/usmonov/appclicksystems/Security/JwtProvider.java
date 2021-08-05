package uz.usmonov.appclicksystems.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

   private final String secretKey="thisIsTheSecretKeyForToken";
   private long expireTime=50_000_000;

   public String generateToken(String username){

       Date expireDate=new Date (System.currentTimeMillis ()+expireTime);

       String token = Jwts
               .builder ()
               .setSubject ( username )
               .setExpiration ( expireDate )
               .signWith ( SignatureAlgorithm.HS512, secretKey )
               .compact ();
       return token;
   }

   public boolean validateToken(String token){
       try{
           Jwts
                   .parser ()
                   .setSigningKey ( secretKey )
                   .parseClaimsJws ( token );
           return true;
       }catch (Exception e){
           e.printStackTrace ();
       }
       return false;
   }

   public String getUsernameFromToken(String token){
       String username = Jwts
               .parser ()
               .setSigningKey ( secretKey )
               .parseClaimsJws ( token )
               .getBody ()
               .getSubject ();
       return username;
   }



}
