package server.service;

import commons.Debt;
import commons.Expense;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import commons.exceptions.NoSuchExpenseException;
import commons.exceptions.NoUserFoundException;
import org.springframework.stereotype.Service;
import server.database.UserRepository;

import java.util.List;

@Service
public class UserService {
    private UserRepository repo;
    private ExpenseService expenseService;
    private DebtService debtService;

    public UserRepository getRepo() {
        return repo;
    }

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    public User getUserById(long id) throws NoUserFoundException {
        return repo.getUserById(id);
    }

    public Object save(User user) {
        return repo.save(user);
    }


    public void addDebts(Long payer_id, Long payee_id, Double debt) throws NoUserFoundException, NoDebtFoundException {
        Double amount1 = debtService.getDebtById(payee_id, payer_id).getAmount();
        User payer = getUserById(payer_id);
        User payee = getUserById(payee_id);

        if (amount1 == null && debtService.getDebtById(payer_id, payee_id) == null) {
            debtService.addDebt(payer_id, payee_id, debt);
            List<Debt> oldDebts = payer.getDebts();
            oldDebts.add(new Debt(payer_id, payee_id, debt));
            payer.setDebts(oldDebts);
        }
        else if (amount1 == null) {
            Double initialAmount = debtService.getDebtById(payee_id, payer_id).getAmount();
            debtService.deleteDebt(payer_id, payee_id);
            debtService.addDebt(payer_id, payee_id, initialAmount + debt);
        }
        else if (amount1 >= debt){
            debtService.deleteDebt(payee_id, payer_id);
            debtService.addDebt(payee_id, payer_id, amount1 - debt);
        }
        else {
            debtService.deleteDebt(payee_id, payer_id);
            debtService.addDebt(payer_id, payee_id, debt - amount1);

            List<Debt> oldDebtsPayer = payer.getDebts();
            oldDebtsPayer.add(new Debt(payer_id, payee_id, debt - amount1));
            payer.setDebts(oldDebtsPayer);

            List<Debt> oldDebtsPayee = payee.getDebts();
            for (Debt d: oldDebtsPayee) {
                if (d.getPayee_id() == payer_id) {
                    oldDebtsPayee.remove(d);
                }
            }
            payee.setDebts(oldDebtsPayee);

        }
    }

    /**
     * creates a debt for a user for a given expense
     * @param expense_id the debt of the expense
     * @throws NoSuchExpenseException if the use ris not a part of the given expense
     */
    public void settleDebt(long payer_id, Long expense_id) throws NoSuchExpenseException, NoUserFoundException, NoDebtFoundException {
        if (!getUserById(payer_id).getExpenses().contains(expense_id))
            throw new NoSuchExpenseException();
        Expense expense = expenseService.findById(expense_id).get();

        if (expense.getPayer() == payer_id)
            return;
        int people = expense.getPayingParticipants().size() + 1;
        double payment = expense.getAmount() / people;
        this.addDebts(payer_id, expense.getPayer(), payment);
    }
}
