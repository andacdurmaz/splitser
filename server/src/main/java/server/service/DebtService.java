package server.service;

import commons.Debt;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.stereotype.Service;
import server.database.DebtRepository;

import java.util.List;

@Service
public class DebtService {
    private final DebtRepository repo;

    public DebtService(DebtRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    public Debt getDebtById(long payer_id, long payee_id) throws NoDebtFoundException {
        return repo.getDebtByPayers(payer_id, payee_id);
    }

    public void addDebt(long payer_id, long payee_id, Double amount) {
        repo.save(new Debt(payer_id,payee_id, amount));
    }

    public void deleteDebt(long payer_id, long payee_id) throws NoDebtFoundException {
        repo.deleteByPayers(payer_id, payee_id);
    }

}
