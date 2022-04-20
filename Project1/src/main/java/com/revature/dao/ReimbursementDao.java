package com.revature.dao;

import com.revature.Controller.ReimbursementController;
import com.revature.dto.AddReimbursementDto;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDao {



    public ReimbursementDao() {
    }

    public Reimbursement updateReimbursements(int statusId, int managerId, int reimbursementId) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){
            con.setAutoCommit(false); // we set it false, so it is not to commit until we finish the transaction

            String sql = "UPDATE REIMBURSEMENT " +
                    "SET STATUS_ID = ?, " +
                    "MANAGER_ID = ?, " +
                    "DATE_RESOLVED = CURRENT_TIMESTAMP " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, statusId);
            pstmt.setInt(2, managerId);
            pstmt.setInt(3, reimbursementId);

            pstmt.executeUpdate();

            String sql2 = "SELECT R.ID, R.AMOUNT, R.DATE_SUBMITTED, R.DATE_RESOLVED, R.DESCRIPTION, " +
                    "emp.id AS employee_id, emp.username AS employee_username, emp.PASSWORD AS employee_password, emp.first_name AS employee_firstname, emp.last_name AS employee_lastname, emp.EMAIL AS employee_email, " +
                    "man.id AS manager_id, man.username AS manager_username, man.PASSWORD AS manager_password, man.first_name AS manager_firstname, man.last_name AS manager_lastname, man.EMAIL AS manager_email, " +
                    "RS.STATUS, RT.type " +
                    "FROM reimbursement R " +
                    "JOIN REIMBURSEMENT_STATUS RS ON R.STATUS_ID = RS.ID " +
                    "JOIN REIMBURSEMENT_TYPE RT ON R.TYPE_ID = RT.ID " +
                    "LEFT JOIN USERS emp ON R.EMPLOYEE_ID = emp.ID " +
                    "LEFT JOIN USERS man ON R.MANAGER_ID = man.ID " +
                    "WHERE R.ID = ?";

            PreparedStatement pstmt2 = con.prepareStatement(sql2);

            pstmt2.setInt(1, reimbursementId);

            ResultSet rs = pstmt2.executeQuery();

            if (rs.next()){
                int id = rs.getInt("id");
                int amount = rs.getInt("amount");
                String dateSubmitted = rs.getString("date_submitted");
                String dateResolved = rs.getString("date_resolved");
                String description = rs.getString("description");

                //employee User object
                int employeeID = rs.getInt("employee_id");
                String employeeUsername = rs.getString("employee_username");
                String employeePassword = rs.getString("employee_password");
                String employeeFirstname = rs.getString("employee_firstname");
                String employeeLastname = rs.getString("employee_lastname");
                String employeeEmail = rs.getString("employee_email");
                String employeeRole = "employee";

                User employee = new User(employeeID, employeeUsername, employeePassword,employeeFirstname, employeeLastname, employeeEmail, employeeRole);

                //manager User object
                int managerID = rs.getInt("manager_id");
                String managerUsername = rs.getString("manager_username");
                String managerPassword = rs.getString("manager_password");
                String managerFirstname = rs.getString("manager_firstname");
                String managerLastname = rs.getString("manager_lastname");
                String managerEmail = rs.getString("manager_email");
                String managerRole = "manager";

                String status = rs.getString("status");
                String type = rs.getString("type");

                User manager = new User(managerID, managerUsername, managerPassword, managerFirstname, managerLastname, managerEmail, managerRole);


                Reimbursement reimbursement = new Reimbursement(id, amount, dateSubmitted, dateResolved, description, employee, manager, status, type);

                con.commit();
                return reimbursement;
            }

                return null;
        }

    }


    public Reimbursement addReimbursement (int employeeId, AddReimbursementDto dto) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {

            con.setAutoCommit(false);

            String sql = "INSERT INTO reimbursement (amount, description, recepit_image, employee_id, type_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt1.setInt(1, dto.getAmount());
            pstmt1.setString(2, dto.getDescription());
            pstmt1.setBinaryStream(3, dto.getImage());
            pstmt1.setInt(4, employeeId);
            pstmt1.setInt(5, dto.getTypeId());

            pstmt1.executeUpdate();

            ResultSet rs = pstmt1.getGeneratedKeys();
            rs.next();
            int reimbursementId = rs.getInt(1);

            String sql2 = "SELECT R.ID, R.AMOUNT, R.DATE_SUBMITTED, R.DATE_RESOLVED, R.DESCRIPTION, " +
                    "emp.id AS employee_id, emp.username AS employee_username, emp.PASSWORD AS employee_password, emp.first_name AS employee_firstname, emp.last_name AS employee_lastname, emp.EMAIL AS employee_email, " +
                    "man.id AS manager_id, man.username AS manager_username, man.PASSWORD AS manager_password, man.first_name AS manager_firstname, man.last_name AS manager_lastname, man.EMAIL AS manager_email, " +
                    "RS.STATUS, RT.type " +
                    "FROM reimbursement R " +
                    "JOIN REIMBURSEMENT_STATUS RS ON R.STATUS_ID = RS.ID " +
                    "JOIN REIMBURSEMENT_TYPE RT ON R.TYPE_ID = RT.ID " +
                    "LEFT JOIN USERS emp ON R.EMPLOYEE_ID = emp.ID " +
                    "LEFT JOIN USERS man ON R.MANAGER_ID = man.ID " +
                    "WHERE R.id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql2);

            pstmt.setInt(1, reimbursementId);

            ResultSet rs2 = pstmt.executeQuery();

            if (rs2.next()) {
                int id = rs2.getInt("id");
                int amount = rs2.getInt("amount");
                String dateSubmitted = rs2.getString("date_submitted");
                String dateResolved = rs2.getString("date_resolved");
                String description = rs2.getString("description");

                //employee User object
                int rEmployeeID = rs2.getInt("employee_id");
                String employeeUsername = rs2.getString("employee_username");
                String employeePassword = rs2.getString("employee_password");
                String employeeFirstname = rs2.getString("employee_firstname");
                String employeeLastname = rs2.getString("employee_lastname");
                String employeeEmail = rs2.getString("employee_email");
                String employeeRole = "employee";

                User employee = new User(rEmployeeID, employeeUsername, employeePassword, employeeFirstname, employeeLastname, employeeEmail, employeeRole);

                //manager User object
                int managerID = rs2.getInt("manager_id");
                String managerUsername = rs2.getString("manager_username");
                String managerPassword = rs2.getString("manager_password");
                String managerFirstname = rs2.getString("manager_firstname");
                String managerLastname = rs2.getString("manager_lastname");
                String managerEmail = rs2.getString("manager_email");
                String managerRole = "manager";

                String status = rs2.getString("status");
                String type = rs2.getString("type");

                User manager = new User(managerID, managerUsername, managerPassword, managerFirstname, managerLastname, managerEmail, managerRole);


                Reimbursement reimbursement = new Reimbursement(id, amount, dateSubmitted, dateResolved, description, employee, manager, status, type);

                con.commit();
                return reimbursement;
            }
            return null;
        }
    }

    public List<Reimbursement> getAllReimbursements() throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT R.ID, R.AMOUNT, R.DATE_SUBMITTED, R.DATE_RESOLVED, R.DESCRIPTION, " +
                    "emp.id AS employee_id, emp.username AS employee_username, emp.PASSWORD AS employee_password, emp.first_name AS employee_firstname, emp.last_name AS employee_lastname, emp.EMAIL AS employee_email, " +
                    "man.id AS manager_id, man.username AS manager_username, man.PASSWORD AS manager_password, man.first_name AS manager_firstname, man.last_name AS manager_lastname, man.EMAIL AS manager_email, " +
                    "RS.STATUS, RT.type " +
                    "FROM reimbursement R " +
                    "JOIN REIMBURSEMENT_STATUS RS ON R.STATUS_ID = RS.ID " +
                    "JOIN REIMBURSEMENT_TYPE RT ON R.TYPE_ID = RT.ID " +
                    "LEFT JOIN USERS emp ON R.EMPLOYEE_ID = emp.ID " +
                    "LEFT JOIN USERS man ON R.MANAGER_ID = man.ID";

            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int amount = rs.getInt("amount");
                String dateSubmitted = rs.getString("date_submitted");
                String dateResolved = rs.getString("date_resolved");
                String description = rs.getString("description");


                //employee User object
                int employeeID = rs.getInt("employee_id");
                String employeeUsername = rs.getString("employee_username");
                String employeePassword = rs.getString("employee_password");
                String employeeFirstname = rs.getString("employee_firstname");
                String employeeLastname = rs.getString("employee_lastname");
                String employeeEmail = rs.getString("employee_email");
                String employeeRole = "employee";

                User employee = new User(employeeID, employeeUsername, employeePassword,employeeFirstname, employeeLastname, employeeEmail, employeeRole);

                //manager User object
                int managerID = rs.getInt("manager_id");
                String managerUsername = rs.getString("manager_username");
                String managerPassword = rs.getString("manager_password");
                String managerFirstname = rs.getString("manager_firstname");
                String managerLastname = rs.getString("manager_lastname");
                String managerEmail = rs.getString("manager_email");
                String managerRole = "manager";

                String status = rs.getString("status");
                String type = rs.getString("type");

                User manager = new User(managerID, managerUsername, managerPassword, managerFirstname, managerLastname, managerEmail, managerRole);


                Reimbursement reimbursement = new Reimbursement(id, amount, dateSubmitted, dateResolved, description, employee, manager, status, type);


                reimbursements.add(reimbursement);
            }

            return reimbursements;
        }

    }


    public List<Reimbursement> getAllReimbursementsByUserId(int userId) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){

            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT R.ID, R.AMOUNT, R.DATE_SUBMITTED, R.DATE_RESOLVED, R.DESCRIPTION, " +
                    "emp.id AS employee_id, emp.username AS employee_username, emp.PASSWORD AS employee_password, emp.first_name AS employee_firstname, emp.last_name AS employee_lastname, emp.EMAIL AS employee_email, " +
                    "man.id AS manager_id, man.username AS manager_username, man.PASSWORD AS manager_password, man.first_name AS manager_firstname, man.last_name AS manager_lastname, man.EMAIL AS manager_email, " +
                    "RS.STATUS, RT.type " +
                    "FROM reimbursement R " +
                    "JOIN REIMBURSEMENT_STATUS RS ON R.STATUS_ID = RS.ID " +
                    "JOIN REIMBURSEMENT_TYPE RT ON R.TYPE_ID = RT.ID " +
                    "LEFT JOIN USERS emp ON R.EMPLOYEE_ID = emp.ID " +
                    "LEFT JOIN USERS man ON R.MANAGER_ID = man.ID " +
                    "WHERE R.EMPLOYEE_ID = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int amount = rs.getInt("amount");
                String dateSubmitted = rs.getString("date_submitted");
                String dateResolved = rs.getString("date_resolved");
                String description = rs.getString("description");

                //employee User object
                int employeeID = rs.getInt("employee_id");
                String employeeUsername = rs.getString("employee_username");
                String employeePassword = rs.getString("employee_password");
                String employeeFirstname = rs.getString("employee_firstname");
                String employeeLastname = rs.getString("employee_lastname");
                String employeeEmail = rs.getString("employee_email");
                String employeeRole = "employee";

                User employee = new User(employeeID, employeeUsername, employeePassword,employeeFirstname, employeeLastname, employeeEmail, employeeRole);

                //manager User object
                int managerID = rs.getInt("manager_id");
                String managerUsername = rs.getString("manager_username");
                String managerPassword = rs.getString("manager_password");
                String managerFirstname = rs.getString("manager_firstname");
                String managerLastname = rs.getString("manager_lastname");
                String managerEmail = rs.getString("manager_email");
                String managerRole = "manager";

                String status = rs.getString("status");
                String type = rs.getString("type");

                User manager = new User(managerID, managerUsername, managerPassword, managerFirstname, managerLastname, managerEmail, managerRole);


                Reimbursement reimbursement = new Reimbursement(id, amount, dateSubmitted, dateResolved, description, employee, manager, status, type);


                reimbursements.add(reimbursement);
            }

            return reimbursements;
        }

    }

    public InputStream getReimbursementImage (int reimbursementId) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()) {


            String sql = "SELECT RECEPIT_IMAGE " +
                    "FROM REIMBURSEMENT " +
                    "WHERE id = ? ";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, reimbursementId);


            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                InputStream is = rs.getBinaryStream("RECEPIT_IMAGE");

                return is;
            } else {
                return null;
            }

        }
    }

}
