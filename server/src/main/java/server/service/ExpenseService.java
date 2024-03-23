package server.service;

import commons.Expense;
import commons.exceptions.NoSuchExpenseException;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    /**
     * Constructor
     *
     * @param repo ExpenseRepository
     */
    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    /**
     * Get method
     *
     * @return all expenses
     */
    public List<Expense> findAll() {
        return repo.findAll();
    }

    /**
     * Get method
     *
     * @return the number of entities available
     */
    public Object count() {
        return repo.count();
    }

    /**
     * Saves a given entity
     *
     * @param expense entity to save
     * @return saved entity
     */
    public Expense save(Expense expense) {
        return repo.save(expense);
    }

    /**
     * Get method
     *
     * @param id id of expense
     * @return expense with the specified id
     */
    public Optional<Expense> findById(long id) {
        return repo.findById(id);
    }

    /**
     * Returns whether an entity exists with the given id
     *
     * @param id id of entity
     * @return true if an entity with the given id exists, false otherwise
     */
    public boolean existsById(long id) {
        return repo.existsById(id);
    }

    /**
     * Updates the name of the expense with the given id to be the given name
     *
     * @param id      expense id
     * @param newName new name of the expense
     * @return the expense with the new name
     * @throws NoSuchExpenseException if there is no expense with the given id
     */
    public Expense updateExpenseName(long id, String newName)
            throws NoSuchExpenseException {
        Optional<Expense> expense = repo.findById(id);
        if (expense.isPresent()) {
            Expense e = expense.get();
            e.setName(newName);
            return repo.save(e);
        } else {
            throw new NoSuchExpenseException();
        }

    }
}
