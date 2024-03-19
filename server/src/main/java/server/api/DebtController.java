package server.api;

import commons.Debt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * path to get all Users in the repository
     * @return all users in repository
     */
    @GetMapping(path = { "", "/" })
    public List<Debt> getAll() {
        return service.findAll();
    }
}
