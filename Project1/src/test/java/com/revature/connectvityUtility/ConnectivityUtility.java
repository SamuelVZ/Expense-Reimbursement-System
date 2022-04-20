package com.revature.connectvityUtility;

import com.revature.utility.ConnectionUtility;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectivityUtility {

   @Test
    public void testConnection () throws SQLException {
       Connection con = ConnectionUtility.getConnection();
   }
}
