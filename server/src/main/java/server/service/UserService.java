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

    /**
     * Get method
     * @return UserRepository
     */
    public UserRepository getRepo() {
        return repo;
    }

    /**
     * Constructor
     * @param repo UserRepository
     */
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Get method
     * @return ExpenseService
     */
    public ExpenseService getExpenseService() {
        return expenseService;
    }

    /**
     * Get method
     * @return DebtService
     */
    public DebtService getDebtService() {
        return debtService;
    }

    /**
     * Set method
     * @param expenseService expenseService to set
     */
    public void setExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Set method
     * @param debtService DebtService
     */
    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }


    /**
     * Get method
     * @return all users
     */
    public List<User> findAll() {
        return repo.findAll();
    }

    /**
     * Checks if the user with the specified id exists
     * @param id specified id
     * @return true if the user with the specified id exists, false otherwise
     */
    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    /**
     * Gets the user with the specified id
     * @param id specified id
     * @return the user with the specified id
     * @throws NoUserFoundException exception
     */
    public User getUserById(long id) throws NoUserFoundException {
        return repo.getUserById(id);
    }

    /**
     * Saves the given user
     * @param user given user
     * @return saved entity
     */
    public User save(User user) {
        return repo.save(user);
    }

    /**
     * Adds debts
     * @param payer User who will pay later
     * @param payee user who paid for the expense
     * @param debt amount to be debited
     * @throws NoDebtFoundException exception
     */
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
