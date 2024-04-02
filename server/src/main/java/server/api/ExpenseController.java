/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import commons.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.ExpenseService;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {


    private final ExpenseService service;
    private final Random random;

    /**
     * Constructor
     *
     * @param random  random
     * @param service ExpenseService
     */
    public ExpenseController(Random random, ExpenseService service) {
        this.random = random;
        this.service = service;
    }

    /**
     * Get method
     *
     * @return all expenses
     */
    @GetMapping(path = {"", "/"})
    public List<Expense> getAll() {
        return service.findAll();
    }

    /**
     * Get method
     *
     * @param id id of expense
     * @return expense with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable("id") long id) {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.findById(id).get());
    }

    /**
     * Post method
     *
     * @param expense to add
     * @return added expense or bad request
     */
    @PostMapping("/add")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        if ((expense == null)) {
            return ResponseEntity.badRequest().build();
        }

        Expense saved = service.save(expense);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get method
     *
     * @return random expense
     */
    @GetMapping("/random")
    public ResponseEntity<Expense> getRandom() {
        var expenses = service.findAll();
        var idx = random.nextInt((int) service.count());
        return ResponseEntity.ok(expenses.get(idx));
    }

    /**
     * Updates the name of an existing expense with the given id
     * to be renamed to the given newName
     *
     * @param id      expense id
     * @param expense expense to edit
     * @return updated expense or bad request
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") long id,
                                                 @RequestBody Expense expense) {
        if (!service.existsById(id)) {
            ResponseEntity.badRequest().build();
        }
        Expense updatedExpense = service.updateExpense(id, expense);
        return ResponseEntity.ok(updatedExpense);
    }
}
