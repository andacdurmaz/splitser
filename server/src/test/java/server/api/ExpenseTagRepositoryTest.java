package server.api;

import commons.Expense;
import commons.ExpenseTag;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ExpenseTagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ExpenseTagRepositoryTest implements ExpenseTagRepository {

    public  List<ExpenseTag> expenseTags = new ArrayList<>();
    public  List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<ExpenseTag> findAll() {
        calledMethods.add("findAll");
        return expenseTags;
    }

    @Override
    public List<ExpenseTag> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ExpenseTag> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ExpenseTag> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ExpenseTag> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ExpenseTag> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ExpenseTag> entities) {
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
    public ExpenseTag getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExpenseTag getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public ExpenseTag getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    private Optional<ExpenseTag> find(Long id) {
        return expenseTags.stream().filter(q -> q.getId() == id).findFirst();
    }

    @Override
    public <S extends ExpenseTag> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ExpenseTag> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<ExpenseTag> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ExpenseTag> S save(S entity) {
        call("New expense tag saved");
        entity.setId((long) expenseTags.size());
        expenseTags.add(entity);
        return entity;
    }

    @Override
    public Optional<ExpenseTag> findById(Long id) {
        return find(id);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public long count() {
        return expenseTags.size();
    }

    @Override
    public void deleteById(Long id) {
        expenseTags = expenseTags.stream().filter(q -> q.getId() != id).toList();
    }



    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends ExpenseTag> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends ExpenseTag> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ExpenseTag> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ExpenseTag> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends ExpenseTag> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends ExpenseTag, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Expense getExpenseById(long id) {
        return null;
    }

    @Override
    public void delete(ExpenseTag entity) {

    }
}