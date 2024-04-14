package server.api;


import commons.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.AdminRepository;

import server.service.AdminService;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private Admin admin1;
    private Admin admin2;
    private AdminRepository adminRepository;
    private AdminService adminService;

    @BeforeEach
    void setup() {
        admin1 = new Admin("test1@email.com", "password1");
        admin2 = new Admin(null, null);
        adminRepository = mock(AdminRepository.class);
        adminService = new AdminService(adminRepository);
    }

    @Test
    public void testGetAllAdmins(){
        List<Admin> adminsList = List.of(admin1,admin2);
        when(adminRepository.findAll()).thenReturn(adminsList);
        List<Admin> adminsResult = adminService.getAllAdmins();
        assertEquals(adminsList, adminsResult);
    }

    @Test
    public void testAddNewAdmin(){
        ResponseEntity<Admin> expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity<Admin> actualResponse = adminService.addNewAdmin(admin1);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAddNullAdmin(){
        ResponseEntity<Admin> expectedResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<Admin> actualResponse = adminService.addNewAdmin(admin2);

        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void testGetAdminByEmailNotExists(){
        ResponseEntity<Admin> expectedResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<Admin> actualResponse = adminService.getAdminByEmail("notexisting@email.com");

        assertEquals(expectedResponse, actualResponse);
    }



    @Test
    public void testChangePasswordNotExists(){
        ResponseEntity<Admin> expectedResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<Admin> actualResponse = adminService.changePassword("notexisting@email.com");

        assertEquals(expectedResponse, actualResponse);
    }
}
