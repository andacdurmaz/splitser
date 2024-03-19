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

    public User save(User user) {
        return (User) repo.save(user);
    }


    public void addDebts(User payer, User payee, Double debt) throws NoUserFoundException, NoDebtFoundException {
        Double amount1 = debtService.getDebtByPayerAndPayee(payer, payer).getAmount();


        if (amount1 == null && debtService.getDebtByPayerAndPayee(payer, payee) == null) {
            debtService.addDebt(payer, payee, debt);
            List<Debt> oldDebts = payer.getDebts();
            oldDebts.add(new Debt(payer, payee, debt));
            payer.setDebts(oldDebts);
        }
        else if (amount1 == null) {
            Double initialAmount = debtService.getDebtByPayerAndPayee(payee, payer).getAmount();
            debtService.deleteDebt(payer, payee);
            debtService.addDebt(payer, payee, initialAmount + debt);
        }
        else if (amount1 >= debt){
            debtService.deleteDebt(payee, payer);
            debtService.addDebt(payee, payer, amount1 - debt);
        }
        else {
            debtService.deleteDebt(payee, payer);
            debtService.addDebt(payer, payee, debt - amount1);

            List<Debt> oldDebtsPayer = payer.getDebts();
            oldDebtsPayer.add(new Debt(payer, payee, debt - amount1));
            payer.setDebts(oldDebtsPayer);

            List<Debt> oldDebtsPayee = payee.getDebts();
            for (Debt d: oldDebtsPayee) {
                if (d.getPayer().equals(payer)) {
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
    public void settleDebt(User payer, Long expense_id) throws NoSuchExpenseException, NoUserFoundException, NoDebtFoundException {
        if (!payer.getExpenses().contains(expense_id))
            throw new NoSuchExpenseException();
        Expense expense = expenseService.findById(expense_id).get();

        if (expense.getPayer().equals(payer))
            return;
        int people = expense.getPayingParticipants().size() + 1;
        double payment = expense.getAmount() / people;
        this.addDebts(payer, expense.getPayer(), payment);
    }
}
