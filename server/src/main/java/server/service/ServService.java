package server.service;

import commons.Admin;
import org.springframework.stereotype.Service;
import server.database.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServService {
    private EventRepository repo;
    private List<Admin> admins;

    public ServService(EventRepository repo) {
        this.repo = repo;
        Admin admin = new Admin("admin", "admin1");
        admins = new ArrayList<>();
        admins.add(admin);
    }

    public boolean login(String email, String password){
        for(Admin a : admins){
            String adminEmail = a.getEmail();
            if(!adminEmail.equals(email))
                return false;
            String adminPassword = a.getPassword();
            if(adminPassword.equals(password))
                return true;
            return false;
        }
        return false;
    }



}
