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

    public ExpenseService getExpenseService() {
        return expenseService;
    }

    public DebtService getDebtService() {
        return debtService;
    }

    public void setExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
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
        return repo.save(user);
    }


    public void addDebts(User payer, User payee, Double debt) throws NoDebtFoundException {
        Debt amount1 = debtService.getDebtByPayerAndPayee(payer, payer);
        if (amount1 == null && debtService.getDebtByPayerAndPayee(payer, payee) == null) {
            debtService.addDebt(payer, payee, debt);
            List<Debt> oldDebts = payer.getDebts();
            oldDebts.add(new Debt(payer, payee, debt));
            payer.setDebts(oldDebts);
        }
        else if (amount1 == null) {
            Double initialAmount = debtService.getDebtByPayerAndPayee(payer, payee).getAmount();
            debtService.deleteDebt(payer, payee);
            debtService.addDebt(payer, payee, initialAmount + debt);
            List<Debt> oldDebts = payer.getDebts();
            Debt removed = new Debt(payer, payee, debt);
            oldDebts.remove(removed);
            oldDebts.add(new Debt(payer, payee, initialAmount + debt));
            payer.setDebts(oldDebts);
        }
        else if (amount1.getAmount() >= debt){
            debtService.deleteDebt(payee, payer);
            debtService.addDebt(payee, payer, amount1.getAmount() - debt);
        }
        else {
            debtService.deleteDebt(payee, payer);
            debtService.addDebt(payer, payee, debt - amount1.getAmount());

            List<Debt> oldDebtsPayer = payer.getDebts();
            oldDebtsPayer.add(new Debt(payer, payee, debt - amount1.getAmount()));
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
     * @param expense the debt of the expense
     * @throws NoSuchExpenseException if the use ris not a part of the given expense
     */
    public void settleDebt(User payer, Expense expense) throws NoSuchExpenseException, NoDebtFoundException {
        if (!payer.getExpenses().contains(expense))
            throw new NoSuchExpenseException();

        if (expense.getPayer().equals(payer))
            return;
        int people = expense.getPayingParticipants().size() + 1;
        double payment = expense.getAmount() / people;
        this.addDebts(payer, expense.getPayer(), payment);
    }
}
