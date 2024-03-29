package server.database;

import commons.Expense;
import commons.ExpenseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTagRepository extends JpaRepository<ExpenseTag, Long> {

    /**
     * gets expense tag by its id
     *
     * @param id
     * @return Expense tag
     */
    Expense getExpenseById(long id);
}
