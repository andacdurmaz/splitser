package server.api;

import commons.Debt;
import commons.exceptions.NoDebtFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import server.database.DebtRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DebtRepo implements DebtRepository {
    public List<Debt> debts = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    public List<String> getCalledMethods() {
        return calledMethods;
    }

    private void call(String name) {
        calledMethods.add(name);
    }
    private Optional<Debt> find(long payer_id, long payee_id) {
        return debts.stream().filter(q -> q.getPayer_id() == payer_id && q.getPayee_id() == payee_id).findFirst();
    }


    @Override
    public boolean existsByPayers(long payer_id, long payee_id) {
        return find(payer_id,payee_id).isPresent();
    }


    @Override
    public Debt getDebtByPayers(long payer_id, long payee_id) throws NoDebtFoundException {
        if (!existsByPayers(payer_id, payee_id))
            throw new NoDebtFoundException();
        return find(payer_id, payee_id).get();
    }
    @Override
    public long getPayerByPayers(long payer_id, long payee_id) throws NoDebtFoundException {
        if (!existsByPayers(payer_id,payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getPayer_id();
    }

    @Override
    public long getPayeeByPayers(long payer_id, long payee_id) throws NoDebtFoundException {
        if (!existsByPayers(payer_id, payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getPayee_id();
    }

    @Override
    public Double getAmoungByPayers(long payer_id, long payee_id) throws NoDebtFoundException {
        if (!existsByPayers(payer_id,payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getAmount();
    }

    public <S extends Debt> S save(S entity) {
        call("save");
        debts.add(entity);
        return entity;
    }

    public void deleteByPayers(long payer_id, long payee_id) throws NoDebtFoundException {
        if (!existsByPayers(payer_id, payee_id))
            throw new NoDebtFoundException();
        debts = debts.stream().filter(q -> q.getPayer_id() != payer_id || q.getPayee_id() != payee_id).toList();
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
        return debts;
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
