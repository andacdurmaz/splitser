package server.service;

import commons.Expense;
import org.springframework.http.ResponseEntity;
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
     * @param id expense id
     * @param oldExpense expense to update
     * @return updated expense
     */
    public Expense updateExpense(long id, Expense oldExpense) {
        return repo.save(oldExpense);
    }





    /**
     * This method is a sub-method of sharePerPerson method.
     * This calculates number of participants in all expenses, i.e.
     * participants are counted multiple times.
     * The +1 is for the payer since paying participants don't contain
     * the payer of the expense
     *
     * @return total number of participants in all expenses
     */
    public int getParticipantsForAllExpenses() {
        List<Expense> allExpenses = repo.findAll();
        int people = 0;
        for (Expense e : allExpenses) {
            people += e.getPayingParticipants().size() + 1;
        }
        return people;
    }



    /**
     * Method to delete a expense id
     * @param expense id of the expense to be deleted
     * @return the response entity
     */
    public ResponseEntity<Expense> deleteExpense(long expense) {
        if (!existsById(expense)) {
            return ResponseEntity.badRequest().build();
        }
        repo.deleteById(expense);
        return ResponseEntity.ok().build();
    }

    /**
     * getter method for the expense repo
     * @return the repository
     */
    public ExpenseRepository getRepo() {
        return repo;
    }
}
