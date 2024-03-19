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

    public List<Debt> findAll() {
        return repo.findAll();
    }

    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    public Debt getDebtByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        return repo.getDebtByPayerAndPayee(payer_id, payee_id);
    }

    public void addDebt(User payer_id, User payee_id, Double amount) {
        repo.save(new Debt(payer_id,payee_id, amount));
    }

    public void deleteDebt(User payer_id, User payee_id) throws NoDebtFoundException {
        repo.deleteByPayerAndPayee(payer_id, payee_id);
    }

}
