package com.revature.serviceTesting;

import com.revature.model.User;
import com.revature.service.JWTService;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTesting {

    @Test
    public void createJWTTest () {

        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");

        JWTService jwtService = new JWTService();

        String actual = jwtService.createJWT(user);

        String expected = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0MSIsInVzZXJfaWQiOjEsInVzZXJfcm9sZSI6ImVtcGxveWVlIn0.1rSqiArHqHqIZV0R1z3B026ehy4rwczc0aVSxMweEPyLiUl0xM3CZeSl9Xsr1PSx";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void parseJwtTest (){
        String jwtTest= "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0MSIsInVzZXJfaWQiOjEsInVzZXJfcm9sZSI6ImVtcGxveWVlIn0.1rSqiArHqHqIZV0R1z3B026ehy4rwczc0aVSxMweEPyLiUl0xM3CZeSl9Xsr1PSx";

        JWTService jwtService = new JWTService();

        //Jws<Claims> expected = "<header={alg=HS384},body={sub=test1, user_id=1, user_role=employee},signature=1rSqiArHqHqIZV0R1z3B026ehy4rwczc0aVSxMweEPyLiUl0xM3CZeSl9Xsr1PSx>";
        Jws<Claims> token = jwtService.parseJwt(jwtTest);
        int actual = token.getBody().get("user_id", Integer.class);

        Assertions.assertEquals(1, actual);
    }

    @Test
    public void parseJwtTest_negative (){
        String jwtFail = "ddd";
        JWTService jwtService = new JWTService();

        Assertions.assertThrows(UnauthorizedResponse.class, () -> {
            jwtService.parseJwt(jwtFail);
        });

    }
}
