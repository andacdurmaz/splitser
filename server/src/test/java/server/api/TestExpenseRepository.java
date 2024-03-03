package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import commons.Expense;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import server.database.ExpenseRepository;

public abstract class TestExpenseRepository implements ExpenseRepository {

    public final List<Expense> expenses = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

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
        return expenses.stream().filter(q -> q.id == id).findFirst();
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
        entity.id = (long) expenses.size();
        expenses.add(entity);
        return entity;
    }

    @Override
    public Optional<Expense> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
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


}