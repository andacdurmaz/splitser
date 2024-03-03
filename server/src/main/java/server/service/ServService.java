package server.service;

import commons.Admin;
import commons.Event;
import org.springframework.stereotype.Service;
import server.database.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            if(adminEmail.equals(email))
                return true;
            String adminPassword = a.getPassword();
            if(adminPassword.equals(password))
                return true;
            return false;
        }
        return false;
    }

    public List<Event> findAll(){
        List<Event> results = repo.findAll();
        return results;
    }

    public boolean existsById(long id){
        boolean b;
        b = repo.existsById(id);
        return b;
    }

    public Optional<Event> findById(long id){
        Optional<Event> e;
        e = repo.findById(id);
        return e;
    }

    public void addEvent(Event e){
        repo.save(e);
    }

    public void removeEvent(Event e){
        repo.delete(e);
    }

}
