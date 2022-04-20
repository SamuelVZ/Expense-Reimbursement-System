package com.revature.service;

import com.revature.Controller.ReimbursementController;
import com.revature.Exceptions.ImageNotFoundException;
import com.revature.Exceptions.InvalidImageException;
import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDto;
import com.revature.dto.ResponseReimbursementDto;
import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    public static Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    private ReimbursementDao reimbursementDao;
    private ResponseReimbursementDto responseReimbursementDto;

    public ReimbursementService(){
        this.reimbursementDao = new ReimbursementDao();
        this.responseReimbursementDto = new ResponseReimbursementDto();
    }

    public ReimbursementService (ReimbursementDao mockDao) {
        this.reimbursementDao = mockDao;
    }

    public List<ResponseReimbursementDto> getAllReimbursements() throws SQLException {
            logger.info("A manager tried to get all the reimbursements");

            List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursements();

            List<ResponseReimbursementDto> responseReimbursementDtos = new ArrayList<>();

            for(Reimbursement r : reimbursements){
                responseReimbursementDtos.add(new ResponseReimbursementDto(r.getId(), r.getAmount(), r.getDateSubmitted(), r.getDateResolved(), r.getDescription(),
                        r.getEmployee().getUsername(), r.getManager().getUsername(), r.getStatusName(), r.getTypeName()));

            }
            return responseReimbursementDtos;


    }


    public List<ResponseReimbursementDto> getAllReimbursementsByEmployeeID(String employeeId) throws SQLException {
        logger.info("A employee tried to get all their reimbursements");

        try{
            int empId = Integer.parseInt(employeeId);

            List <Reimbursement> reimbursements = reimbursementDao.getAllReimbursementsByUserId(empId);

            List<ResponseReimbursementDto> dtos = new ArrayList<>();

            for(Reimbursement r : reimbursements){

                dtos.add(new ResponseReimbursementDto(r.getId(), r.getAmount(), r.getDateSubmitted(), r.getDateResolved(), r.getDescription(),
                        r.getEmployee().getUsername(), r.getManager().getUsername(), r.getStatusName(), r.getTypeName()));

            }
            return  dtos;

        }catch (NumberFormatException e){
            logger.warn("Service layer - the client id: " + employeeId + " is invalid");
            throw new IllegalArgumentException("the client id provided is invalid: " + employeeId);
        }

    }

    public ResponseReimbursementDto addReimbursementByUserId(int empID, AddReimbursementDto addDto) throws SQLException, IOException, InvalidImageException {

        Tika tika = new Tika();
        String mimeType = tika.detect(addDto.getImage());

        if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }


        Reimbursement reimbursement = reimbursementDao.addReimbursement(empID, addDto);

        ResponseReimbursementDto dto = new ResponseReimbursementDto(reimbursement.getId(),reimbursement.getAmount(),
                reimbursement.getDateSubmitted(), reimbursement.getDateResolved(), reimbursement.getDescription(),
                reimbursement.getEmployee().getUsername(), reimbursement.getManager().getUsername(), reimbursement.getStatusName(), reimbursement.getTypeName());



        return  dto;
    }

    public InputStream getReimbursementImage(String reimbursementId) throws SQLException, ImageNotFoundException {

            try{
                int iReimbursementId = Integer.parseInt(reimbursementId);


                InputStream is = reimbursementDao.getReimbursementImage(iReimbursementId);

                if(is == null){
                    throw new ImageNotFoundException("Reimbursement id " + reimbursementId + " does not have an image");
                }

                return is;

            }catch (NumberFormatException e){
                logger.warn("the reimbursement id: " + reimbursementId + " is invalid");
                throw new IllegalArgumentException("the reimbursement id: " + reimbursementId + " is invalid");
            }

    }

    public ResponseReimbursementDto updateStatus(String status, int userId, String reimId) throws SQLException {

        try{
            int iReimbursementId = Integer.parseInt(reimId);
            int iStatus = Integer.parseInt(status);

            Reimbursement reimbursement = reimbursementDao.updateReimbursements(iStatus, userId, iReimbursementId);

            ResponseReimbursementDto dto = new ResponseReimbursementDto(reimbursement.getId(),reimbursement.getAmount(),
                    reimbursement.getDateSubmitted(), reimbursement.getDateResolved(), reimbursement.getDescription(),
                    reimbursement.getEmployee().getUsername(), reimbursement.getManager().getUsername(), reimbursement.getStatusName(), reimbursement.getTypeName());



            return  dto;


        }catch (NumberFormatException e){
            logger.warn("the status id: " + status + " or reimbursement id: " + reimId + " is invalid");
            throw new IllegalArgumentException("the status id: " + status + " or reimbursement id: " + reimId + " are invalid");
        }
    }
}
