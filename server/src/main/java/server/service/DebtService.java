package server.service;

import commons.Debt;
import commons.Event;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.DebtRepository;

import java.util.List;
import java.util.Optional;

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
     * @return the new debt
     */
    public Debt addDebt(User payer, User payee, Double amount) {
        List<Debt> list = repo.findAll();
        Debt debt = new Debt(payer, payee, amount);
        list.add(debt);
        repo.saveAll(list);
        return debt;
    }


    /**
     * deletes a debt from the database
     * @param id of the deleted debt
     * @return the deleted debt
     * @throws NoDebtFoundException if no debt with such id is found
     */
    public ResponseEntity<Debt> deleteDebt(long id) throws NoDebtFoundException {
        if (!existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
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
     * arranges the debts when a new debt is added
     * @param debt the added debt
     * @return the new debt
     * @throws NoDebtFoundException if no such debt is found
     */
    public ResponseEntity<Debt> settleDebt(Debt debt) throws NoDebtFoundException {
        Event e = debt.getEvent();
        List<Debt> eventDebts = findAll()
                .stream().filter(q -> q.getEvent().equals(e)).toList();
        Optional<Debt> existingDebt = eventDebts.stream()
                .filter(q ->( (q.getPayer().equals(debt.getPayer())
                        && q.getPayee().equals(debt.getPayee())) ||
                        (q.getPayee().equals(debt.getPayer())
                                && q.getPayer().equals(debt.getPayee())) )).findFirst();
        if (existingDebt.isPresent()) {
            Debt oldDebt = existingDebt.get();
            if (debt.getPayer().equals(oldDebt.getPayer())) {
                oldDebt.setAmount(oldDebt.getAmount() + debt.getAmount());
                Debt saved = oldDebt;
                save(saved);
                return ResponseEntity.ok(oldDebt);
            }
            else {
                double oldAmount = oldDebt.getAmount();
                double amount = debt.getAmount();
                if (oldAmount < amount) {
                    deleteDebt(oldDebt.getId());
                    Debt saved = new Debt(oldDebt.getPayee(), oldDebt.getPayer(),
                            amount - oldAmount, debt.getEvent());
                    saved.setId(oldDebt.getId());
                    save(saved);
                    return ResponseEntity.ok(saved);

                }
                else if (oldAmount > amount) {
                    oldDebt.setAmount(oldAmount - amount);
                    Debt saved = oldDebt;
                    save(saved);
                    return ResponseEntity.ok(oldDebt);
                }
                else {
                    deleteDebt(oldDebt.getId());
                    return ResponseEntity.ok(null);
                }
            }
        }
        Debt saved = debt;
        save(saved);
        return ResponseEntity.ok(saved);
    }

}
