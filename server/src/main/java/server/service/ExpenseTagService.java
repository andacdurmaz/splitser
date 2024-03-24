package server.service;
import commons.ExpenseTag;
import commons.exceptions.NoSuchEventException;
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
     *
     * @return List of ExpenseTag
     */
    public List<ExpenseTag> getAllExpenseTags() {
        return expenseTagRepository.findAll();
    }

    /**
     *
     * @param id
     * @return ExpenseTag
     */
    public ExpenseTag getExpenseTagById(long id) {
        return expenseTagRepository.getById(id);
    }

    /**
     *
     * @param e
     * @return ReponseEntity of expenseTag
     */
    public ResponseEntity<ExpenseTag> addExpenseTag(ExpenseTag e) {
        expenseTagRepository.save(e);
        return ResponseEntity.ok(e);
    }

    /**
     *
     * @param id
     * @param newName
     * @param newColour
     * @return expenseTag
     * @throws NoSuchEventException
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
