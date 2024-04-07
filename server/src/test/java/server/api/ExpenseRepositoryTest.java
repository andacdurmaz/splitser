package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


import commons.Expense;
import commons.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ExpenseRepository;

public class ExpenseRepositoryTest implements ExpenseRepository {

    public  List<Expense> expenses = new ArrayList<>();
    public  List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Expense> findAll() {
        calledMethods.add("findAll");
        return expenses;
    }

    @Override
    public List<Expense> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Expense> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Expense> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Expense> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Expense> entities) {
//        for (Expense deleted: entities) {
//            expenses = expenses.stream().filter(q -> !q.equals(deleted)).toList();
//        }
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
//        for (Long id: longs) {
//            expenses = expenses.stream().filter(q -> q.getExpenseId() != id).toList();
//        }
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    @Override
    public Expense getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Expense getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public Expense getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    private Optional<Expense> find(Long id) {
        return expenses.stream().filter(q -> q.getId() == id).findFirst();
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Expense> S save(S entity) {
        call("New expense saved");
        entity.setId((long) expenses.size());
        expenses.add(entity);
        return entity;
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return find(id);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public long count() {
        return expenses.size();
    }

    @Override
    public void deleteById(Long id) {
        expenses = expenses.stream().filter(q -> q.getId() != id).toList();
    }

    @Override
    public void delete(Expense entity) {

    }


    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Expense> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Expense> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Expense> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Expense> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends Expense> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends Expense, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Expense getExpenseById(long id) {
        return null;
    }

    @Override
    public String getNameById(long id) {
        return null;
    }

    @Override
    public Double getAmountById(long id) {
        return null;
    }

    @Override
    public List<User> getParticipantsById(long id) {
        return null;
    }
}