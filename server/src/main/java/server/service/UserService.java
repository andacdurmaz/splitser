package server.service;

import commons.Debt;
import commons.User;
import commons.exceptions.NoUserFoundException;
import org.springframework.http.ResponseEntity;
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
     *
     * @return UserRepository
     */
    public UserRepository getRepo() {
        return repo;
    }

    /**
     * Constructor
     *
     * @param repo UserRepository
     */
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Get method
     *
     * @return ExpenseService
     */
    public ExpenseService getExpenseService() {
        return expenseService;
    }

    /**
     * Get method
     *
     * @return DebtService
     */
    public DebtService getDebtService() {
        return debtService;
    }

    /**
     * Set method
     *
     * @param expenseService expenseService to set
     */
    public void setExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Set method
     *
     * @param debtService DebtService
     */
    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }


    /**
     * Get method
     *
     * @return all users
     */
    public List<User> findAll() {
        return repo.findAll();
    }

    /**
     * Checks if the user with the specified id exists
     *
     * @param id specified id
     * @return true if the user with the specified id exists, false otherwise
     */
    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    /**
     * Gets the user with the specified id
     *
     * @param id specified id
     * @return the user with the specified id
     * @throws NoUserFoundException exception
     */
    public User getUserById(long id) throws NoUserFoundException {
        return repo.getUserById(id);
    }

    /**
     * Gets one participants whole debt
     *
     * @param id id of participant
     * @return double value of participants debt
     * @throws NoUserFoundException if param id doesn't match any user's id
     */
    public Double getUsersDebt(long id) throws NoUserFoundException {
        User user = repo.getUserById(id);
        List<Debt> debts = debtService.getDebtsByPayee(user);
        Double sum = 0.0;
        for (Debt d : debts) {
            sum += d.getAmount();
        }
        return sum;
    }

    /**
     * Saves the given user
     *
     * @param user given user
     * @return saved entity
     */
    public User save(User user) {
        return repo.save(user);
    }


    /**
     * updates the user
     * @param user the updated version of the user
     * @return the new user
     */
    public User updateUser(User user) {
        return repo.save(user);
    }

    /**
     * deletes a user from the database
     * @param id of the user
     * @return user
     */
    public ResponseEntity<User> deleteUser(long id) {
        if (!existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
