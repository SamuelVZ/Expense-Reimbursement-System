package com.revature.Controller;

import com.revature.dto.AddReimbursementDto;
import com.revature.dto.ResponseReimbursementDto;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class ReimbursementController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(ReimbursementController.class);

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
        this.reimbursementService = new ReimbursementService();
    }

    //only accessible by managers
    private Handler getAllReimbursements = (ctx) -> {
        logger.info("Trying to retrieve all reimbursements");

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);


        if(!token.getBody().get("user_role").equals("manager")){
            logger.warn("A non manager user tried to retrieve all reimbursements. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be a manager to access this information");
        }


        List<ResponseReimbursementDto> responseReimbursementDtoList = this.reimbursementService.getAllReimbursements();

        logger.info("All reimbursements retrieve successfully");


        ctx.json(responseReimbursementDtoList);
    };


    private Handler getAllReimbursementByEmployeeId = (ctx) -> {

        String employeeId =  ctx.pathParam("employeeId");

        logger.info("Trying to retrieve all reimbursements for employee: " + employeeId);


        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("employee")){
            logger.warn("A non employee user tried to retrieve all reimbursements of an employee. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be an employee to access this information");
        }

        int empID = Integer.parseInt(employeeId);
        if(!token.getBody().get("user_id").equals(empID)){
            logger.warn("The employee: "+ token.getBody().getId() + ". user tried to retrieve all reimbursements for employee : "+ employeeId);

            throw new UnauthorizedResponse("you can only see your reimbursements!!");
        }


        List<ResponseReimbursementDto> responseReimbursementDtoList = this.reimbursementService.getAllReimbursementsByEmployeeID(employeeId);

        logger.info("All reimbursements retrieve successfully");
        ctx.json(responseReimbursementDtoList);
    };

    private Handler addReimbursementToAnEmployee = ctx -> {

        String employeeId =  ctx.pathParam("employeeId");

        logger.info("Trying to add a reimbursement for employee: " + employeeId);


        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("employee")){
            logger.warn("A non employee user tried to retrieve all reimbursements of an employee. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be an employee to access this information");
        }

        int empID = Integer.parseInt(employeeId);
        if(!token.getBody().get("user_id").equals(empID)){
            logger.warn("The employee: "+ token.getBody().getId() + ". Tried to add a reimbursement for employee : "+ employeeId);

            throw new UnauthorizedResponse("you can only add reimbursements to yourself!!");
        }
        //to populate the dto with the info(text)
        String amount = ctx.formParam("amount");
        String description = ctx.formParam("description");
        String typeId = ctx.formParam("typeId");

        int iAmount = Integer.parseInt(amount);
        int iTypeId = Integer.parseInt(typeId);

        AddReimbursementDto addDto = new AddReimbursementDto();
        addDto.setAmount(iAmount);
        addDto.setDescription(description);
        addDto.setTypeId(iTypeId);


        //to populate the image
        UploadedFile image = ctx.uploadedFile("image");
        InputStream is = image.getContent();
        addDto.setImage(is);



        ResponseReimbursementDto response = reimbursementService.addReimbursementByUserId(empID, addDto);

        ctx.json(response);

    };


    private Handler getImageByReimbursementId = (ctx) -> {


        String reimbursementId = ctx.pathParam("reimbursementId");

        logger.info("Trying to get an image for the reimbursement id: " + reimbursementId);

        InputStream image = this.reimbursementService.getReimbursementImage(reimbursementId);

        Tika tika = new Tika();
        String mimeType = tika.detect(image);

        ctx.header("Content-Type", mimeType); // tell the client what type of image is being sent in the response
        ctx.result(image);
    };

    private Handler updateStatus = ctx -> {

      String reimId = ctx.pathParam("reimbursementId");

        logger.info("Trying to update the status for the reimbursement " + reimId);

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = jwtService.parseJwt(jwt);


        if(!token.getBody().get("user_role").equals("manager")){
            logger.warn("A non manager user tried to update a reimbursement. user: " + token.getBody().getSubject());

            throw new UnauthorizedResponse("you must be a manager to access this information");
        }


        String status = ctx.queryParam("statusId");

        int userId = token.getBody().get("user_id", Integer.class);

        if(status == null){
            throw new IllegalArgumentException("You need to provide a status query parameter when attempting to update the reimbursement");
        }

        ResponseReimbursementDto responseReimbursementDto = reimbursementService.updateStatus(status, userId, reimId);


        ctx.json(responseReimbursementDto);
    };

    @Override
    public void mapEndPoints(Javalin app) {

        app.get("/reimbursements", getAllReimbursements);
        app.get("/employees/{employeeId}/reimbursements", getAllReimbursementByEmployeeId);
        app.post("employees/{employeeId}/reimbursements", addReimbursementToAnEmployee);
        app.get("/reimbursements/{reimbursementId}/image", getImageByReimbursementId);
        app.patch("/reimbursements/{reimbursementId}", updateStatus);

    }
}
