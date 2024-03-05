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

import org.springframework.data.jpa.repository.JpaRepository;
import commons.Event;
import org.springframework.stereotype.Repository;

//@Repository
public interface EventRepository extends JpaRepository<Event, Long>  {
    /**
     * gets the event by its ID
     * @param id the id of the event
     * @return the event of the given id
     */
    Event getEventById(long id);

    /**
     * gets the creator of a certain event
     * @param id of the event
     * @return the creator
     */
    User getCreatorById(long id);

    /**
     * gets the title of the event by its id
     * @param id of the event
     * @return title
     */
    String getEventTitleById(long id);

    /**
     * gets the expenses from the event
     * @param id of the event
     * @return expenses in a list
     */
    List<Expense> getExpensesById(long id);

    /**
     * gets the description of a event by its id
     * @param id of the event
     * @return the description
     */
    String getDescriptionById(long id);
}

