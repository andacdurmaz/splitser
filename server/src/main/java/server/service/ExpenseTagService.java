package server.service;
import commons.ExpenseTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server.database.ExpenseTagRepository;

import java.util.List;

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
     * returns all expense tags
     * @return List of ExpenseTag
     */
    public List<ExpenseTag> getAllExpenseTags() {
        return expenseTagRepository.findAll();
    }

    /**
     * returns an expense tag by its id
     * @param id
     * @return ExpenseTag
     */
    public ExpenseTag getExpenseTagById(long id) {
        return expenseTagRepository.getById(id);
    }

    /**
     * checks whether an expense tag exists by its id
     * @param id
     * @return boolean
     */
    public boolean existsById(long id) {
        return expenseTagRepository.existsById(id);
    }

    /**
     * adds an expense tag to the database
     * @param e
     * @return ReponseEntity of expenseTag
     */
    public ResponseEntity<ExpenseTag> addExpenseTag(ExpenseTag e) {
        expenseTagRepository.save(e);
        return ResponseEntity.ok(e);
    }

    /**
     * updates the expense tag in the repository
     * @param expenseTag
     * @return ExpenseTag
     */
    public ExpenseTag updateExpenseTag(ExpenseTag expenseTag) {
        return expenseTagRepository.save(expenseTag);
    }

    /**
     * deletes an expense tag by its id from the repository
     * @param id
     * @return response entity
     */
    public ResponseEntity<ExpenseTag> deleteExpenseTag(long id) {
        if (!existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        expenseTagRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
