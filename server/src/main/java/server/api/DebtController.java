package server.api;

import commons.Debt;
import commons.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.DebtService;

import java.util.List;

@RestController
@RequestMapping("/api/debts")
public class DebtController {
    private final DebtService service;

    /**
     * constructor for the Debt Controller
     * @param service
     */
    public DebtController( DebtService service) {
        this.service = service;
    }

    /**
     * getter method for the service of the debtcontroller
     * @return the debtservice
     */
    public DebtService getService() {
        return service;
    }

    /**
     * path to get all Users in the repository
     * @return all users in repository
     */
    @GetMapping(path = { "", "/" })
    public List<Debt> getAll() {
        return service.findAll();
    }


    /**
     * adds a debt to the database
     * @param debt the added debt
     * @return the added debt or a failure if there is no success
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Debt> add(@RequestBody Debt debt) {
        if ((debt == null) ) {
            return ResponseEntity.badRequest().build();
        }
        Debt saved = service.save(debt);
        return ResponseEntity.ok(saved);
    }

}
