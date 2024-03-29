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

import commons.ExpenseTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.ExpenseTagService;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class ExpenseTagController {


    private final ExpenseTagService expenseTagService;

    /**
     * initialize service for the controller
     * @param expenseTagService
     */
    @Autowired
    public ExpenseTagController(ExpenseTagService expenseTagService) {
        this.expenseTagService = expenseTagService;
    }

    /**
     * gets the expense tag service
     * @return service
     */
    public ExpenseTagService getService() {
        return expenseTagService;
    }

    /**
     * gets all expense tags
     * @return all expense tags
     */
    @GetMapping(path = {"", "/"})
    public List<ExpenseTag> getAll() {
        return expenseTagService.getAllExpenseTags();
    }


    /**
     * gets an expense tag by its id
     * @param id
     * @return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseTag> getById(@PathVariable("id") long id){
        if (id < 0 || !expenseTagService.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(expenseTagService.getExpenseTagById(id));
    }


    /**
     * adds expense tag
     * @param expenseTag
     * @return response entity
     */
    @PostMapping(value = "/add")
    public ResponseEntity<ExpenseTag> addExpenseTag(@RequestBody ExpenseTag expenseTag) {
        if ((expenseTag == null)) {
            return ResponseEntity.badRequest().build();
        }

        return expenseTagService.addExpenseTag(expenseTag);
    }


    /**
     * updates expense tag
     * @param id
     * @param expenseTag
     * @return returns response entity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseTag> updateExpenseTag(@PathVariable("id") long id,
                                                       @RequestBody ExpenseTag expenseTag) {
        if (!expenseTagService.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        ExpenseTag updatedExpenseTag = expenseTagService.updateExpenseTag(expenseTag);
        return ResponseEntity.ok(updatedExpenseTag);
    }

    /**
     *  Deleles expense tag
     * @param id
     * @return response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ExpenseTag> deleteExpenseTag(@PathVariable("id") long id) {
        return expenseTagService.deleteExpenseTag(id);
    }

}
