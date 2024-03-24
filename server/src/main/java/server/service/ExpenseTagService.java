package server.service;
import commons.ExpenseTag;
import commons.exceptions.NoSuchEventException;
import org.springframework.aop.interceptor.ExposeBeanNameAdvisors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.database.ExpenseTagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseTagService {

    /**
     * The repository for the expenseTag
     */
    private final ExpenseTagRepository expenseTagRepository;

    /**
     * Constructor for the expense atg service
     *
     * @param expenseTagRepository expenseTagRepository
     */

    @Autowired
    public ExpenseTagService(ExpenseTagRepository expenseTagRepository) {
        this.expenseTagRepository = expenseTagRepository;
    }

    /**
     * Method to get all expenseTags from the database
     *
     * @return the list of events
     */
    public List<ExpenseTag> getAllExpenseTags() {
        return expenseTagRepository.findAll();
    }

    /**
     * Method to get an expenseTags by its id
     *
     * @param id of the expenseTag
     * @return the expenseTag
     */
    public ExpenseTag getExpenseTagById(long id) {
        return expenseTagRepository.getById(id);
    }

    /**
     * Add an expenseTag
     * @param e
     * @return
     */
    public ResponseEntity<ExpenseTag> addExpenseTag(ExpenseTag e) {
        expenseTagRepository.save(e);
        return ResponseEntity.ok(e);
    }

    /**
     * Updates the name of the event with the given id to be the given name
     *
     * @param id      event id
     * @param newName new name of the event
     * @return the event with the new name
     * @throws NoSuchEventException if there is no expense with the given id
     */
    public ExpenseTag updateExpenseTag(long id, String newName, String newColour)
            throws NoSuchEventException {
        Optional<ExpenseTag> expenseTag = expenseTagRepository.findById(id);
        if (expenseTag.isPresent()) {
            ExpenseTag e = expenseTag.get();
            e.setName(newName);
            e.setColour(newColour);
            return expenseTagRepository.save(e);
        } else {
            throw new NoSuchEventException();
        }

    }
}
