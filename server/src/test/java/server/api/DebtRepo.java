package server.api;

import commons.Debt;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.DebtRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DebtRepo implements DebtRepository {
    public List<Debt> debts = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    @Override
    public List<Debt> getDebts() {
        return new ArrayList<>(debts);
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
    private Optional<Debt> find(User payer, User payee) {
        return debts.stream().filter(q -> q.getPayer().equals(payer) && q.getPayee().equals(payee)).findFirst();
    }

    private Optional<Debt> find(long id) {
        return debts.stream().filter(q -> q.getId() ==id).findFirst();
    }


    @Override
    public boolean existsByPayerAndPayee(User payer_id, User payee_id) {
        return find(payer_id,payee_id).isPresent();
    }


    @Override
    public Debt getDebtByPayerAndPayee(User payer, User payee) throws NoDebtFoundException {
            // Check if the debt exists for the given payer and payee
        if (!existsByPayerAndPayee(payer, payee))
            return null;
        return find(payer, payee).get();

    }


    @Override
    public void deleteById(long id) throws NoDebtFoundException {
        if (!existsById(id))
            throw new NoDebtFoundException();
        debts = debts.stream().filter(q -> q.getId() != id).toList();
    }

    @Override
    public boolean existsById(long id) {
        return find(id).isPresent();
    }

    @Override
    public Debt getDebtById(long id) throws NoDebtFoundException {
        if (!existsById(id))
            throw new NoDebtFoundException();
        return find(id).get();
    }

    @Override
    public User getPayerById(long id) throws NoDebtFoundException {
        if (!existsById(id))
            throw new NoDebtFoundException();
        return find(id).get().getPayer();
    }

    @Override
    public User getPayeeById(long id) throws NoDebtFoundException {
        if (!existsById(id))
            throw new NoDebtFoundException();
        return find(id).get().getPayee();
    }

    @Override
    public Double getAmoungById(long id) throws NoDebtFoundException {
        if (!existsById(id))
            throw new NoDebtFoundException();
        return find(id).get().getAmount();
    }

    @Override
    public User getPayerByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        if (!existsByPayerAndPayee(payer_id,payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getPayer();
    }

    @Override
    public User getPayeeByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        if (!existsByPayerAndPayee(payer_id, payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getPayee();
    }

    @Override
    public Double getAmoungByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        if (!existsByPayerAndPayee(payer_id,payee_id))
            throw new NoDebtFoundException();
        return find(payer_id,payee_id).get().getAmount();
    }

    @Override
    public void deleteByPayerAndPayee(User payer_id, User payee_id) throws NoDebtFoundException {
        if (!existsByPayerAndPayee(payer_id, payee_id))
            throw new NoDebtFoundException();
        debts = debts.stream().filter(q -> !q.getPayer().equals(payer_id) || !q.getPayee().equals(payee_id)).toList();
    }

    public Debt save(Debt entity) {
        call("save");
        debts.add(entity);
        return entity;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Debt> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Debt> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Debt> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Debt getOne(Long aLong) {
        return null;
    }

    @Override
    public Debt getById(Long aLong) {
        return null;
    }

    @Override
    public Debt getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Debt> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Debt> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Debt> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Debt> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Debt> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Debt> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Debt, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Debt> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Debt> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Debt> findAll() {
        return debts;
    }

    @Override
    public List<Debt> findAllById(Iterable<Long> longs) {
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
    public void delete(Debt entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Debt> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Debt> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Debt> findAll(Pageable pageable) {
        return null;
    }
}
