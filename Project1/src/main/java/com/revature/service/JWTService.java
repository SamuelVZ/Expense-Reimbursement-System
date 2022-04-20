package com.revature.service;

import com.revature.Controller.ExceptionController;
import com.revature.dao.UserDao;
import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;

public class JWTService {
        public static Logger logger = LoggerFactory.getLogger(JWTService.class);

        private Key key; // = Keys.secretKeyFor(SignatureAlgorithm.HS256); //TODO to select the key algorithm

        //TODO Instance initialization block, this will run after the constructor
//        {
//            byte[] secret = "my_password".getBytes();
//            key = Keys.hmacShaKeyFor(secret);
//        }
        public JWTService(){
            byte[] secret = "my_password_jhgjhgjgjhgjhjhgfrsdffdghgfdfgd5654654".getBytes(); //use a long text here to avoid an error
            key = Keys.hmacShaKeyFor(secret); // we create the key here
            //key = Keys.secretKeyFor(SignatureAlgorithm.HS384);   //better to do this because it generates the key random
        }

        public String createJWT(User user){
            logger.info("Create a jwt for username:" + user.getUsername());

            String jwt = Jwts.builder()             //todo use the builder to build the jason web token, and sing it with the key
                    .setSubject(user.getUsername())
                    .claim("user_id", user.getId())
                    .claim("user_role", user.getRole())
                    .signWith(key)
                    .compact();

            logger.info("Jws created for username:" + user.getUsername());
            return jwt;
        }

        public Jws<Claims> parseJwt(String jwt){
            logger.info("Parsing the jwt for a request" );
            try{
                Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

                return token;


            }catch (JwtException e){
                logger.warn("Invalid JWT");
                throw new UnauthorizedResponse("JWT was invalid");
            }

        }
}
