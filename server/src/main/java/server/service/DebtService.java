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

    /**
     * constructor for a debtservice
     * @param repo the repository of the service
     */
    public DebtService(DebtRepository repo) {
        this.repo = repo;
    }

    /**
     * getter method for the debt repository of a debt servoce
     * @return the debt service
     */
    public DebtRepository getRepo() {
        return repo;
    }

    /**
     * findAll method for the debt service
     * @return all debts in the repository
     */
    public List<Debt> findAll() {
        return repo.findAll();
    }

    /**
     * checks if an debt exists in the repository
     * @param id of the checked debt
     * @return true if the debt exists in the repository
     */
    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    /**
     * getter method for a debt
     * @param payer_id the id of the payer
     * @param payee_id the id of the payee
     * @return the debt given its payer's and payee's id
     * @throws NoDebtFoundException thrown if no such debt exists
     */
    public Debt getDebtByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        return repo.getDebtByPayerAndPayee(payer_id, payee_id);
    }

    /**
     * adds a debt to the repository given its information
     * @param payer_id the id of the payer
     * @param payee_id the id of the payee
     * @param amount the amount of the debt
     */
    public void addDebt(User payer_id, User payee_id, Double amount) {
        repo.save(new Debt(payer_id,payee_id, amount));
    }

    /**
     * removes a debt from the database
     * @param payer_id the id of the payer
     * @param payee_id the id of the payee
     * @throws NoDebtFoundException thrown if no such debt exists
     */
    public void deleteDebt(User payer_id, User payee_id) throws NoDebtFoundException {
        repo.deleteByPayerAndPayee(payer_id, payee_id);
    }

    /**
     * saves a debt to the repository
     * @param debt the saved debt
     * @return the debt that was saved
     */
    public Debt save(Debt debt) {
        return repo.save(debt);
    }

}
