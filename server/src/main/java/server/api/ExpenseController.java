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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.service.ExpenseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {


    private final ExpenseService service;
    private final Random random;

    /**
     * gets the servide of the controller
     * @return the service
     */
    public ExpenseService getService() {
        return service;
    }

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
    @PostMapping(value = "/add")
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        if ((expense == null)) {
            return ResponseEntity.badRequest().build();
        }

        addMap.forEach((k, l) -> l.accept(expense));

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
    public Expense updateExpense(@PathVariable("id") long id,
                                                 @RequestBody Expense expense) {
        if (!service.existsById(id)) {
            ResponseEntity.badRequest().build();
        }
        Expense updatedExpense = service.updateExpense(id, expense);
        return updatedExpense;
    }


    //private Map<Object, Consumer<Expense>> delMap = new HashMap<>();

    /**
     * Creates a ws connection to the client
     * Returns a notification with
     * the expense that was edited
     * when it is edited by a client
     * @param e expense to update
     * @return expense
     */
    @MessageMapping("/expenses/edit")
    @SendTo("/updates/edit/expenses")
    public Expense getExUpdates(Expense e) {
        return updateExpense(e.getId(), e);
    }

    /**
     * Creates a ws connection to the client
     * Returns a notification with
     * the expense that was deleted
     * when it is edited by a client
     * @param e expense to delete
     * @return expense
     */
    @MessageMapping("/expenses/del")
    @SendTo("/updates/delete/expenses")
    public Expense getExDeletes(Expense e) {
        deleteExpense(e.getId());
        return e;
    }


    private Map<Object, Consumer<Expense>> addMap = new HashMap<>();

    /**
     * Creates a 1000 ms connection to the server
     * Returns a NO CONTENT entity when no updates
     * have been done, or the expense that was created
     * when it is created
     * @return defferedResult
     */
    @GetMapping("/add/updates")
    public DeferredResult<ResponseEntity<Expense>> getExpenseUpdates() {
        var result = new DeferredResult<ResponseEntity<Expense>>(1000L,
                ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        var key = new Object();
        addMap.put(key, expense -> {
            result.setResult(ResponseEntity.ok(expense));
        });
        result.onCompletion(() -> {
            addMap.remove(key);
        });

        return result;
    }

    /**
     * Deletes an expense by id.
     * @param id the expense id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable("id") long id) {

        return service.deleteExpense(id);
    }
}
