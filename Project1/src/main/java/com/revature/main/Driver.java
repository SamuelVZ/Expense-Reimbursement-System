package com.revature.main;

import com.revature.Controller.*;
import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDto;
import com.revature.model.Reimbursement;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class Driver {

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) throws SQLException {




        Javalin app = Javalin.create((config) -> {  //TODO to set the localhost 8080 (frontend) to be a trusted origin
            config.enableCorsForAllOrigins();

        });

        app.before((ctx) -> {
            logger.info(ctx.method() + " request received for " + ctx.path());
        });

        mapControllers(app, new AuthenticationController(), new ExceptionController(), new ReimbursementController());


        app.start(8081);




//        UserDao userDao = new UserDao();
//
//        try {
//            System.out.println( userDao.getUserByUsernameAndPassword("samuel1", "password1"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        ReimbursementDao reimbursementDao = new ReimbursementDao();
//
//        try {
//
//            AddReimbursementDto addReimbursementDto = new AddReimbursementDto(444, "hamburger", 3);
//            Reimbursement t = reimbursementDao.addReimbursement(3,addReimbursementDto);
//            System.out.println(t);
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
   }

    public static void mapControllers(Javalin app, Controller... controllers){
        for(Controller c :controllers){
            c.mapEndPoints(app);
        }
    }
}
