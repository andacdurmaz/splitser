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
        Debt amount1 = debtService.getDebtByPayerAndPayee(payee, payer);
        if (amount1 == null && debtService.getDebtByPayerAndPayee(payer, payee) == null) {
            noDebts(payer, payee, debt);
        }
        else if (amount1 == null) {
            moreDebt(payer, payee, debt);
        }
        else if (amount1.getAmount() > debt){
            getPaidLess(payer, payee, debt, amount1);
        }
        else if (amount1.getAmount().equals(debt)) {
            cancelDebts(payer, payee);
        }
        else {
            payMore(payer, payee, debt, amount1);
        }
    }

    /**
     * if the payer's debt is more than the payee's debt, now the payer owes money
     * @param payer of the new debt
     * @param payee of the new debt
     * @param debt of the new debt
     * @param amount1 of the old debt
     * @throws NoDebtFoundException thrown if no such debt is found
     */
    private void payMore(User payer, User payee, Double debt, Debt amount1) throws NoDebtFoundException {
        debtService.deleteDebt(payee, payer);
        debtService.addDebt(payer, payee, debt - amount1.getAmount());

        List<Debt> oldDebtsPayer = payer.getDebts();
        oldDebtsPayer.add(new Debt(payer, payee, debt - amount1.getAmount()));
        payer.setDebts(oldDebtsPayer);

        List<Debt> oldDebtsPayee = payee.getDebts();
        oldDebtsPayee = oldDebtsPayee.stream().filter(q -> !q.getPayee().equals(payer)).toList();
        payee.setDebts(oldDebtsPayee);
    }
    /**
     * if the payer's debt is equal to the payee's debt, the debts are cancelled
     * @param payer of the new debt
     * @param payee of the new debt
     * @throws NoDebtFoundException thrown if no such debt is found
     */
    private void cancelDebts(User payer, User payee) throws NoDebtFoundException {
        List<Debt> oldDebtsPayee = payee.getDebts();
        oldDebtsPayee.remove(debtService.getDebtByPayerAndPayee(payee, payer));
        payee.setDebts(oldDebtsPayee);
        debtService.deleteDebt(payee, payer);
    }
    /**
     * if the payer's debt is less than the payee's debt, the payee owes less money now
     * @param payer of the new debt
     * @param payee of the new debt
     * @param debt of the new debt
     * @param amount1 of the old debt
     * @throws NoDebtFoundException thrown if no such debt is found
     */
    private void getPaidLess(User payer, User payee, Double debt, Debt amount1) throws NoDebtFoundException {
        List<Debt> oldDebtsPayee = payee.getDebts();
        oldDebtsPayee.remove(debtService.getDebtByPayerAndPayee(payee, payer));
        oldDebtsPayee.add(new Debt(payee, payer, amount1.getAmount() - debt));
        payee.setDebts(oldDebtsPayee);
        debtService.deleteDebt(payee, payer);
        debtService.addDebt(payee, payer, amount1.getAmount() - debt);
    }
    /**
     * if the payer already owes payee some money, the debt is increased
     * @param payer of the new debt
     * @param payee of the new debt
     * @param debt of the new debt
     * @throws NoDebtFoundException thrown if no such debt is found
     */
    private void moreDebt(User payer, User payee, Double debt) throws NoDebtFoundException {
        Double initialAmount = debtService.getDebtByPayerAndPayee(payer, payee).getAmount();
        debtService.deleteDebt(payer, payee);
        debtService.addDebt(payer, payee, initialAmount + debt);
        List<Debt> oldDebts = payer.getDebts();
        Debt removed = new Debt(payer, payee, debt);
        oldDebts.remove(removed);
        oldDebts.add(new Debt(payer, payee, initialAmount + debt));
        payer.setDebts(oldDebts);
    }
    /**
     * if none of the users owe each other anything, the first debt between them is created
     * @param payer of the new debt
     * @param payee of the new debt
     * @param debt of the new debt
     */
    private void noDebts(User payer, User payee, Double debt) {
        debtService.addDebt(payer, payee, debt);
        List<Debt> oldDebts = payer.getDebts();
        oldDebts.add(new Debt(payer, payee, debt));
        payer.setDebts(oldDebts);
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
