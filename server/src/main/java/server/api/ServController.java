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

    @Autowired
    public ServController(ServService sserv) {
        this.sserv = sserv;
    }

    @GetMapping(path = { "", "/" })
    public List<Event> getAllEvents() {
        return sserv.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        if (id < 0 || !sserv.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sserv.findById(id).get());
    }

    @PutMapping("/")
    public ResponseEntity<?> addEvent(@RequestBody Event e) {
        sserv.addEvent(e);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login
            (@RequestBody String email, @RequestBody String password)
    {
        if(sserv.login(email, password))
            return ResponseEntity.ok("Login successful!");
        return ResponseEntity.badRequest().body(401);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


}