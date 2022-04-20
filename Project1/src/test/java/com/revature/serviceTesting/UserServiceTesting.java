package com.revature.serviceTesting;

import com.revature.dao.UserDao;
import com.revature.model.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTesting {

    @Test
    public void testLogin() throws FailedLoginException, SQLException, NoSuchAlgorithmException {

        UserDao userDao = mock(UserDao.class);

        UserService userService = new UserService(userDao);

        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");

        when(userDao.getUserByUsernameAndPassword("test1", "abc")).thenReturn(user);

        //Act
        User actual = userService.login("test1", "abc");
        //Assert
        Assertions.assertEquals(user, actual);
    }

    @Test
    public void testLogin_negative () throws SQLException {
        UserDao userDao = mock(UserDao.class);
        UserService userService = new UserService(userDao);

        when(userDao.getUserByUsernameAndPassword("test1", "abc")).thenReturn(null);

        //Act + assert
        Assertions.assertThrows(FailedLoginException.class, () -> {
           userService.login("test1", "pass");
        });
    }
}
