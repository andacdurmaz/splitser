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

    /**
     * Constructor
     * @param repo EventRepository
     */
    public ServService(EventRepository repo) {
        this.repo = repo;
        Admin admin = new Admin("admin", "admin1");
        admins = new ArrayList<>();
        admins.add(admin);
    }

    /**
     * Login method
     * @param email email to login with
     * @param password password to login with
     * @return true if login was successful, false otherwise
     */
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

    /**
     * Get method
     * @return all events
     */
    public List<Event> findAll(){
        List<Event> results = repo.findAll();
        return results;
    }

    /**
     * Get method
     * @param id to check event
     * @return true if there is an event with the specified id
     */
    public boolean existsById(long id){
        boolean b;
        b = repo.existsById(id);
        return b;
    }

    /**
     * Get method
     * @param id to check event
     * @return event with the specified id
     */
    public Optional<Event> findById(long id){
        Optional<Event> e;
        e = repo.findById(id);
        return e;
    }

    /**
     * Add method
     * @param e event to add
     */
    public void addEvent(Event e){
        repo.save(e);
    }

    /**
     * Remove method
     * @param e event to remove
     */
    public void removeEvent(Event e){
        repo.delete(e);
    }

}
