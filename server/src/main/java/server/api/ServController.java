package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Serv;
import server.database.ServRepository;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServController {


    private final ServRepository repo;

    @Autowired
    public ServController(ServRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Serv> getAllServ() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }



    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


}