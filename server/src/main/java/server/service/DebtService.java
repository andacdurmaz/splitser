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
     * constructor for a debtService
     *
     * @param repo the repository of the service
     */
    public DebtService(DebtRepository repo) {
        this.repo = repo;
    }

    /**
     * getter method for the debt repository of a debt service
     *
     * @return the debt service
     */
    public DebtRepository getRepo() {
        return repo;
    }

    /**
     * findAll method for the debt service
     *
     * @return all debts in the repository
     */
    public List<Debt> findAll() {
        return repo.findAll();
    }

    /**
     * checks if an debt exists in the repository
     *
     * @param id of the checked debt
     * @return true if the debt exists in the repository
     */
    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    /**
     * getter method for a debt
     *
     * @param payer the id of the payer
     * @param payee the id of the payee
     * @return the debt given its payer's and payee's id
     * @throws NoDebtFoundException thrown if no such debt exists
     */
    public Debt getDebtByPayerAndPayee(User payer, User payee) throws NoDebtFoundException {
        return repo.getDebtByPayerAndPayee(payer, payee);
    }

    /**
     * adds a debt to the repository given its information
     *
     * @param payer  the id of the payer
     * @param payee  the id of the payee
     * @param amount the amount of the debt
     */
    public void addDebt(User payer, User payee, Double amount) {
        repo.save(new Debt(payer, payee, amount));
    }

    /**
     * removes a debt from the database
     *
     * @param payer the id of the payer
     * @param payee the id of the payee
     * @throws NoDebtFoundException thrown if no such debt exists
     */
    public void deleteDebt(User payer, User payee) throws NoDebtFoundException {
        repo.deleteByPayerAndPayee(payer, payee);
    }

    /**
     * saves a debt to the repository
     *
     * @param debt the saved debt
     * @return the debt that was saved
     */
    public Debt save(Debt debt) {
        return repo.save(debt);
    }

}
