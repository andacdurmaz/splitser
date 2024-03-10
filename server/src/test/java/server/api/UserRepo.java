package server.api;

import commons.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
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
     * @return users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * getter method for the called methods for the repository
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
     * @param id of the wanted User
     * @return an Optional for User in case it doesn't exist
     */
    private Optional<User> find(long id) {
        return users.stream().filter(q -> q.getUserID() == id).findFirst();
    }

    /**
     * checks if a User given its id exists
     * @param Id the id of the checked user
     * @return true if the user with the given id exists
     */
    @Override
    public boolean existsById(long Id) {
        return find(Id).isPresent();
    }

    /**
     * returns a user given its id
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

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {
        for (User deleted: entities) {
            users = users.stream().filter(q -> !q.equals(deleted)).toList();
        }
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        for (Long id: longs) {
            users = users.stream().filter(q -> q.getUserID() != id).toList();
        }

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        call("New User added.");
        users.add(entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {
        users = new ArrayList<>();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }



}
