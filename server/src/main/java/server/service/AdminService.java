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

    /**
     * Constructor
     * @param repo AdminRepository
     */
    @Autowired
    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    /**
     * Get method
     * @return all admins
     */
    public List<Admin> getAllAdmins() {
        return repo.findAll();
    }

    /**
     * Get method
     * @param email email to find admins
     * @return all admins with the specified email
     */
    public ResponseEntity<Admin> getAdminByEmail(String email) {
        if (isNullOrEmpty(email) || !repo.existsById(email)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(email).get());
    }
    /**
     * Add method
     * @param admin admin to add
     * @return added admin
     */
    public ResponseEntity<Admin> addNewAdmin(Admin admin) {
        if (isNullOrEmpty(admin.getEmail())
                || isNullOrEmpty(admin.getPassword())
                || repo.existsById(admin.getEmail())){
            return ResponseEntity.badRequest().build();
        }

        Admin saved = repo.save(admin);
        return ResponseEntity.ok(saved);
    }

    /**
     * Change password for admin
     * @param email email of admin
     * @return the new password
     */
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


    /**
     * Shortcut method
     * @param s string to check
     * @return true if the parameter string is empty or null, false otherwise
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


}
