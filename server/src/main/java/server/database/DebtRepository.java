package server.database;

import commons.Debt;
import commons.exceptions.NoDebtFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository {
    boolean existsByPayers(long payer_id, long payee_id);

    Debt getDebtByPayers(long payer_id, long payee_id) throws NoDebtFoundException;

    long getPayerByPayers(long payer_id, long payee_id) throws NoDebtFoundException;
    long getPayeeByPayers(long payer_id, long payee_id) throws NoDebtFoundException;
    Double getAmoungByPayers(long payer_id, long payee_id) throws NoDebtFoundException;

    void deleteByPayers(long payer_id, long payee_id) throws NoDebtFoundException;
    boolean existsById(long id);

    Debt getDebtById(long id) throws NoDebtFoundException;

    long getPayerById(long id) throws NoDebtFoundException;
    long getPayeeById(long id) throws NoDebtFoundException;
    Double getAmoungById(long id) throws NoDebtFoundException;

    void deleteById(long id) throws NoDebtFoundException;


}
