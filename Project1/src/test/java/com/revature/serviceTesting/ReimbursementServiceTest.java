package com.revature.serviceTesting;

import com.revature.Exceptions.ImageNotFoundException;
import com.revature.Exceptions.InvalidImageException;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.dto.AddReimbursementDto;
import com.revature.dto.ResponseReimbursementDto;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReimbursementServiceTest {

    @Test
    public void getAllReimbursements () throws SQLException {

        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");
        User userm = new User(2, "test2", "abc", "aa", "bb", "a@email", "manager");

        List<Reimbursement> reimbursements = new ArrayList<>();

        reimbursements.add(new Reimbursement(1,1,"date", "dater", "desc", user, userm, "app", "f"));
        reimbursements.add(new Reimbursement(2,1,"date", "dater", "desc", user, userm, "app", "f"));
        reimbursements.add(new Reimbursement(3,1,"date", "dater", "desc", user, userm, "app", "f"));

        when(reimbursementDao.getAllReimbursements()).thenReturn(reimbursements);

        List<ResponseReimbursementDto> dtos = new ArrayList<>();
        dtos.add(new ResponseReimbursementDto(1,1,"date", "dater", "desc", "test1", "test2", "app", "f"));
        dtos.add(new ResponseReimbursementDto(2,1,"date", "dater", "desc", "test1", "test2", "app", "f"));
        dtos.add(new ResponseReimbursementDto(3,1,"date", "dater", "desc", "test1", "test2", "app", "f"));

        //Act

        List<ResponseReimbursementDto> actual = reimbursementService.getAllReimbursements();

        //Assert
        Assertions.assertEquals(dtos, actual);

    }
    @Test
    public void getAllReimbursementsByEmployeeID () throws SQLException {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");
        User userm = new User(2, "test2", "abc", "aa", "bb", "a@email", "manager");

        List<Reimbursement> reimbursements = new ArrayList<>();

        reimbursements.add(new Reimbursement(1,1,"date", "dater", "desc", user, userm, "app", "f"));
        reimbursements.add(new Reimbursement(2,1,"date", "dater", "desc", user, userm, "app", "f"));
        reimbursements.add(new Reimbursement(3,1,"date", "dater", "desc", user, userm, "app", "f"));

        when(reimbursementDao.getAllReimbursementsByUserId(1)).thenReturn(reimbursements);

        List<ResponseReimbursementDto> dtos = new ArrayList<>();
        dtos.add(new ResponseReimbursementDto(1,1,"date", "dater", "desc", "test1", "test2", "app", "f"));
        dtos.add(new ResponseReimbursementDto(2,1,"date", "dater", "desc", "test1", "test2", "app", "f"));
        dtos.add(new ResponseReimbursementDto(3,1,"date", "dater", "desc", "test1", "test2", "app", "f"));


        //Act

        List<ResponseReimbursementDto> actual = reimbursementService.getAllReimbursementsByEmployeeID("1");

        //Assert
        Assertions.assertEquals(dtos, actual);
    }


    @Test
    public void getAllReimbursementsByEmployeeID_negative () {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reimbursementService.getAllReimbursementsByEmployeeID("a");
        });

    }

//    @Test
//    public void addReimbursementByUserId_test () throws SQLException, InvalidImageException, IOException {
//        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
//        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);
//
//        InputStream inputStream =
//                IOUtils.toInputStream("image.jpeg", "UTF-8");
//
//
//        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");
//
//        Reimbursement reimbursement = new Reimbursement(1,1,"date", "dater", "desc", user, null, "app", "f");
//
//        AddReimbursementDto add = new AddReimbursementDto(1,  "desc", 1, null);
//
//        when(reimbursementDao.addReimbursement(1, add)).thenReturn(reimbursement);
//
//
//        ResponseReimbursementDto expected = new ResponseReimbursementDto(1,1,"date", "dater", "desc", "test1", null, "app", "f");
//
//        ResponseReimbursementDto actual = reimbursementService.addReimbursementByUserId(1, add);
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    public void addReimbursementByUserId_test_negative () {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        AddReimbursementDto add = new AddReimbursementDto(1,  "desc", 1, null);

        Assertions.assertThrows(InvalidImageException.class, () -> {
            reimbursementService.addReimbursementByUserId(1, add);
        });

    }

    @Test
    public void getReimbursementImage_negative_invalidString(){
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reimbursementService.getReimbursementImage("a");
        });
    }


    @Test
    public void getReimbursementImage_negative_nullimage() throws SQLException {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        when(reimbursementDao.getReimbursementImage(1)).thenReturn(null);

        Assertions.assertThrows(ImageNotFoundException.class, () -> {
            reimbursementService.getReimbursementImage("1");
        });
    }

    @Test
    public void updateStatus() throws SQLException {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        User user = new User(1, "test1", "abc", "aa", "bb", "a@email", "employee");
        User userm = new User(2, "test2", "abc", "aa", "bb", "a@email", "manager");

        Reimbursement reimbursement = new Reimbursement(1,1,"date", "dater", "desc", user, userm, "app", "f");

        when(reimbursementDao.updateReimbursements(2, 1, 1)).thenReturn(reimbursement);

        ResponseReimbursementDto expected = new ResponseReimbursementDto(1,1,"date", "dater", "desc", "test1", "test2", "app", "f");

        ResponseReimbursementDto  actual = reimbursementService.updateStatus("2", 1, "1");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateStatus_negative() throws SQLException {
        ReimbursementDao reimbursementDao = mock(ReimbursementDao.class);
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDao);

        when(reimbursementDao.getReimbursementImage(1)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reimbursementService.updateStatus("a", 1, "a");
        });
    }

}
