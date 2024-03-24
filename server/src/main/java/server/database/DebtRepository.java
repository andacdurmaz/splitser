package server.database;

import commons.Debt;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DebtRepository extends JpaRepository<Debt, Long> {
    /**
     * checks if a debt exists given its payer and payee
     *
     * @param payer of the checked debt
     * @param payee of the checked debt
     * @return true if the debt exists
     */
    boolean existsByPayerAndPayee(User payer, User payee);

    /**
     * returns a debt given its payer and payee
     *
     * @param payer of the debt
     * @param payee of the debt
     * @return the debt
     * @throws NoDebtFoundException if no debt with given info is found
     */
    Debt getDebtByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    /**
     * returns the payer of a debt given its payer and payee
     *
     * @param payer of the debt
     * @param payee of the debt
     * @return the payer
     * @throws NoDebtFoundException if no debt with given info is found
     */
    User getPayerByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    /**
     * returns the payee of a debt given its payer and payee
     *
     * @param payer of the debt
     * @param payee of the debt
     * @return the payee
     * @throws NoDebtFoundException if no debt with given info is found
     */
    User getPayeeByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    /**
     * returns the amount of a debt given its payer and payee
     *
     * @param payer of the debt
     * @param payee of the debt
     * @return the amount
     * @throws NoDebtFoundException if no debt with given info is found
     */
    Double getAmoungByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    /**
     * deletes a debt given its payer and payee
     *
     * @param payer of the debt
     * @param payee of the debt
     * @throws NoDebtFoundException if no debt with given info is found
     */

    void deleteByPayerAndPayee(User payer, User payee) throws NoDebtFoundException;

    /**
     * checks if a debt exists given its id
     *
     * @param id of the debt
     * @return true if the debt exists
     */
    boolean existsById(long id);

    /**
     * gets a debt given its id
     *
     * @param id of the debt
     * @return the debt
     * @throws NoDebtFoundException if the debt given id doesn't exist
     */
    Debt getDebtById(long id) throws NoDebtFoundException;

    /**
     * gets a list of all debts by the payer
     *
     * @param payee payee of all the debts
     * @return list of debts
     */
    List<Debt> getDebtsByPayee(User payee);

    /**
     * gets the payer of a debt given its id
     *
     * @param id of the debt
     * @return the payer
     * @throws NoDebtFoundException if the debt given id doesn't exist
     */
    User getPayerById(long id) throws NoDebtFoundException;

    /**
     * gets the payee of a debt given its id
     *
     * @param id of the debt
     * @return the payee
     * @throws NoDebtFoundException if the debt given id doesn't exist
     */
    User getPayeeById(long id) throws NoDebtFoundException;

    /**
     * gets the amount of a debt given its id
     *
     * @param id of the debt
     * @return the amount
     * @throws NoDebtFoundException if the debt given id doesn't exist
     */
    Double getAmoungById(long id) throws NoDebtFoundException;

    /**
     * deletes a debt given its id
     *
     * @param id of the debt
     * @throws NoDebtFoundException thrown if no such debt exists
     */
    void deleteById(long id) throws NoDebtFoundException;

}
