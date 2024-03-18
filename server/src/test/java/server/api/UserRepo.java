package server.api;

import commons.Debt;
import commons.User;
import commons.exceptions.NoUserFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import server.database.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepo implements UserRepository {

    public List<User> users = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    /**
     * getter method for the users in the repository
     *
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * getter method for the called methods for the repository
     *
     * @return calledMethods
     */
    public List<String> getCalledMethods() {
        return calledMethods;
    }

    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     * finds a User given its Id
     *
     * @param id of the wanted User
     * @return an Optional for User in case it doesn't exist
     */
    private Optional<User> find(long id) {
        return users.stream().filter(q -> q.getUserID() == id).findFirst();
    }

    /**
     * checks if a User given its id exists
     *
     * @param Id the id of the checked user
     * @return true if the user with the given id exists
     */
    @Override
    public boolean existsById(long Id) {
        return find(Id).isPresent();
    }

    /**
     * returns a user given its id
     *
     * @param Id of the User
     * @return the user that is searched for
     */
    @Override
    public User getUserById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get();
    }

    /**
     * returns the username of a user given its id
     *
     * @param Id of the User
     * @return the username of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public String getUsernameById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getUsername();
    }

    /**
     * returns the e-mail of a user given its id
     *
     * @param Id of the User
     * @return the e-mail of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public String getEmailById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getEmail();
    }

    /**
     * returns the IBAN of a user given its id
     *
     * @param Id of the User
     * @return the IBAN of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public String getIbanById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getIBAN();
    }

    /**
     * returns the BIC of a user given its id
     *
     * @param Id of the User
     * @return the BIC of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public String getBicById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getBIC();
    }

    /**
     * returns the server URL of a user given its id
     *
     * @param Id of the User
     * @return the server URL of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public String getServerURLById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getServerURL();
    }

    /**
     * returns the money of a user given its id
     *
     * @param Id of the User
     * @return the wallet of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public double getWalletById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getWallet();
    }

    /**
     * returns the debts of a user given its id
     *
     * @param Id of the User
     * @return the debts of the user that is searched for
     * @throws NoUserFoundException thrown if no user with such id is found
     */
    @Override
    public List<Debt> getDebtsById(long Id) throws NoUserFoundException {
        if (!existsById(Id))
            throw new NoUserFoundException();
        return find(Id).get().getDebts();
    }


    public <S extends User> S save(S entity) {
        call("save");
        entity.setUserID((long) users.size());
        users.add(entity);
        return entity;
    }
    @Override
    public void flush() {

    }

    @Override
    public void deleteAllInBatch(Iterable entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Object getOne(Object o) {
        return null;
    }

    @Override
    public Object getById(Object o) {
        return null;
    }

    @Override
    public Object getReferenceById(Object o) {
        return null;
    }

    @Override
    public List findAll(Example example, Sort sort) {
        return null;
    }

    @Override
    public List findAll(Example example) {
        return null;
    }

    @Override
    public List saveAllAndFlush(Iterable entities) {
        return null;
    }

    @Override
    public Object saveAndFlush(Object entity) {
        return null;
    }

    @Override
    public List saveAll(Iterable entities) {
        return null;
    }

    @Override
    public Object save(Object entity) {
        return null;
    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }

    @Override
    public List findAll() {
        return users;
    }

    @Override
    public List findAllById(Iterable iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void deleteAllById(Iterable iterable) {

    }

    @Override
    public void deleteAll(Iterable entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List findAll(Sort sort) {
        return null;
    }

    @Override
    public Page findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional findOne(Example example) {
        return Optional.empty();
    }

    @Override
    public Page findAll(Example example, Pageable pageable) {
        return null;
    }

    @Override
    public long count(Example example) {
        return 0;
    }

    @Override
    public boolean exists(Example example) {
        return false;
    }

    @Override
    public Object findBy(Example example, Function queryFunction) {
        return null;
    }
}
