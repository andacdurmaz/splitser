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

import org.springframework.data.jpa.repository.JpaRepository;
import server.Serv;

public interface ServRepository extends JpaRepository<Serv, Long> {

    /**
     * Checks if the event with the specified id exists
     * @param id specified id
     * @return true if the event with the specified id exists, false otherwise
     */
    boolean existsById(long id);
    /**
     * Returns the event with the specified id
     * @param id specified id
     * @return returns the event with the specified id
     */
    Serv getServById(long id);
}