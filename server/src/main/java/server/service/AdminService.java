package server.service;

import commons.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.AdminRepository;

import java.util.List;

@Service
public class AdminService {


    private final AdminRepository repo;

    @Autowired
    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public List<Admin> getAllAdmins() {
        return repo.findAll();
    }
    public ResponseEntity<Admin> getAdminByEmail(String email) {
        if (isNullOrEmpty(email) || !repo.existsById(email)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(email).get());
    }

    public ResponseEntity<Admin> addNewAdmin(Admin admin) {
        if (isNullOrEmpty(admin.getEmail()) || isNullOrEmpty(admin.getPassword()) || repo.existsById(admin.getEmail())){
            return ResponseEntity.badRequest().build();
        }

        Admin saved = repo.save(admin);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<Admin> changePassword(String email) {

        if (isNullOrEmpty(email) || !repo.existsById(email)){
            return ResponseEntity.badRequest().build();
        }

        Admin newAdmin = repo.findById(email).get();

        // Needs to make a randomized password
        newAdmin.setPassword("Random Password");

        Admin saved = repo.save(newAdmin);
        return ResponseEntity.ok(saved);
    }



    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


}
