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
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {


    private final ExpenseRepository repo;
    private final Random random;

    public ExpenseController(Random random, ExpenseRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Expense> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        if ((expense == null) ) {
            return ResponseEntity.badRequest().build();
        }

        Expense saved = repo.save(expense);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("random")
    public ResponseEntity<Expense> getRandom() {
        var expenses = repo.findAll();
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(expenses.get(idx));
    }
}
