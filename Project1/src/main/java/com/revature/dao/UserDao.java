package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private User userDao;

    public UserDao(){

    }

    public User getUserByUsernameAndPassword(String userName, String password) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()){

            String sql = "SELECT a.id, a.username, a.password, a.first_name, a.last_name, a.EMAIL, b.role " +
                    "FROM users a " +
                    "JOIN USER_ROLES b ON a.role_id = b.id " +
                    "WHERE a.username= ? AND a.password = ?";

            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,password);

            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String rUserName = rs.getString("username");
                String rPassword = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String role = rs.getString("role");

                return userDao = new User(id, rUserName, rPassword, firstName, lastName, email, role);
            }

        }
            return null;
    }


}
