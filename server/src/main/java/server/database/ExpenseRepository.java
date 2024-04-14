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

package server.database;

import commons.Expense;
import commons.User;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * gets expense by its id
     *
     * @param id
     * @return Expense
     */
    Expense getExpenseById(long id);

    /**
     * returns the name of the expense by its id
     *
     * @param id
     * @return the name of type String
     */
    String getNameById(long id);

    /**
     * returns the amount of the expense by its id
     *
     * @param id
     * @return the amount of type double
     */
    Double getAmountById(long id);

    /**
     * returns a list of the participants of the expense
     *
     * @param id
     * @return List of type User
     */
    List<User> getParticipantsById(long id);
}


