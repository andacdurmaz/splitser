package server.api;

import commons.Event;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Main;
import server.database.ServRepository;
import server.service.ServService;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServController {


    private ServRepository repo1;

    private transient final ServService sserv;

    /**
     * Constructor
     *
     * @param sserv ServService
     */
    @Autowired
    public ServController(ServService sserv) {
        this.sserv = sserv;
        Main.password(String.valueOf(sserv.getPass()));
    }

    /**
     * Get method
     *
     * @return all events
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(sserv.findAll());
    }

    /**
     * Get method
     *
     * @param id id of events
     * @return event with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        if (id < 0 || !sserv.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sserv.findById(id).get());
    }

    /**
     * Post method
     *
     * @param e event to add
     * @return added event
     */
    @PostMapping("/")
    public ResponseEntity<?> addEvent(@RequestBody Event e) {
        sserv.addEvent(e);
        return ResponseEntity.ok().build();
    }

    /**
     * Login method
     * @param password password to login with
     * @return body
     */
    @GetMapping("/login")
    public ResponseEntity<Boolean> login
    (@PathParam("password") String password) {
        try{
            Boolean b  = false;
            if (sserv.login(Long.valueOf(password))) {
                b = true;
                return ResponseEntity.ok(b);
            }
        }catch (NumberFormatException e){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * method to get the randomly generated
     * password of the server
     * @return returns the password of the server
     */
    @GetMapping( "/pass")
    public ResponseEntity<Long> getPass() {
        return ResponseEntity.ok(sserv.getPass());
    }


    /**
     * Shortcut method
     *
     * @param s string to check if it is null or empty
     * @return true if String s is null or empty, false otherwise
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


}