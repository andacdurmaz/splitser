package server.database;

import commons.Debt;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    boolean existsByPayerAndPayee(User payer, User payee);

    Debt getDebtByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    User getPayerByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;
    User getPayeeByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;
    Double getAmoungByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    void deleteByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;
    boolean existsById(long id);

    Debt getDebtById(long id) throws NoDebtFoundException;

    User getPayerById(long id) throws NoDebtFoundException;
    User getPayeeById(long id) throws NoDebtFoundException;
    Double getAmoungById(long id) throws NoDebtFoundException;

    void deleteById(long id) throws NoDebtFoundException;


}
