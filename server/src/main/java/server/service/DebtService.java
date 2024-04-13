package server.service;

import commons.Debt;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.stereotype.Service;
import server.database.DebtRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return (repo).findAll();
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
     * Gets one participants debts
     *
     * @param payee participant to check the debt for
     * @return list of payee's debts
     */
    public List<Debt> getDebtsByPayee(User payee) {
        return repo.getDebtsByPayee(payee);
    }

    /**
     * adds a debt to the repository given its information
     *
     * @param payer  the id of the payer
     * @param payee  the id of the payee
     * @param amount the amount of the debt
     */
    public void addDebt(User payer, User payee, Double amount) {
        List<Debt> list = repo.findAll();
        list.add(new Debt(payer, payee, amount));
        repo.saveAll(list);
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

    /**
     * Calculates the money each person owns
     *
     * @return hashMap with money owned to the group per participant
     */
    public Map<User, Double> finalDebts() {
        List<Debt> allDebts = repo.findAll();
        Map<User, Double> res = new HashMap<>();
        for (Debt debt : allDebts) {
            User payer = debt.getPayer();
            User payee = debt.getPayee();
            Double amount = debt.getAmount();
            res.put(payer, res.getOrDefault(payer, 0.0) - amount);
            res.put(payee, res.getOrDefault(payee, 0.0) + amount);
        }
        return res;
    }

}
