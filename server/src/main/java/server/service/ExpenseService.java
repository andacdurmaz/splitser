package server.service;

import commons.Expense;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public List<Expense> findAll() {
        return repo.findAll();
    }

    public Object count() {
        return repo.count();
    }

    public Expense save(Expense expense) {
        return repo.save(expense);
    }

    public Optional<Expense> findById(long id) {
        return findById(id);
    }

    public boolean existsById(long id) {
        return existsById(id);
    }
}
