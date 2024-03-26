package server.api;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    }

    /**
     * Get method
     *
     * @return all events
     */
    @GetMapping(path = {"", "/"})
    public List<Event> getAllEvents() {
        return sserv.findAll();
    }

    /**
     * Get method
     *
     * @param id id of events
     * @return event with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
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
     * @param pass password to login with
     * @return body
     */
    @PostMapping("/login")
    public ResponseEntity<?> login
    (@PathVariable("pass") long pass) {
        if (sserv.login(pass))
            return ResponseEntity.ok("Login successful!");
        return ResponseEntity.badRequest().body(401);
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