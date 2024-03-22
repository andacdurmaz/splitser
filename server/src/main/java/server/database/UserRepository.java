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

import commons.Debt;
import commons.exceptions.NoUserFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import commons.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks if a user with a certain id exists
     *
     * @param id the id of the checked user
     * @return true if such a user exists, false otherwise
     */
    boolean existsById(long id);

    /**
     * Retrieves the user from the database given its id
     *
     * @param id of the User
     * @return the User given its id
     */
    User getUserById(long id) throws NoUserFoundException;

    /**
     * Retrieves the username of a User from the database given its id
     *
     * @param id of the User
     * @return the username of the User
     */
    String getUsernameById(long id) throws NoUserFoundException;

    /**
     * Retrieves the e-mail of a User from the database given its id
     *
     * @param id of the User
     * @return the e-mail of the User
     */
    String getEmailById(long id) throws NoUserFoundException;

    /**
     * Retrieves the IBAN of a User from the database given its id
     *
     * @param id of the User
     * @return the IBAN of the User
     */
    String getIbanById(long id) throws NoUserFoundException;

    /**
     * Retrieves the BIC of a User from the database given its id
     *
     * @param id of the User
     * @return the BIC of the User
     */
    String getBicById(long id) throws NoUserFoundException;

    /**
     * Retrieves the server URL of a User from the database given its id
     *
     * @param id of the User
     * @return the server URL of the User
     */
    String getServerURLById(long id) throws NoUserFoundException;

    /**
     * Retrieves the money of a User from the database given its id
     *
     * @param id of the User
     * @return the wallet of the User
     */
    double getWalletById(long id) throws NoUserFoundException;

    /**
     * Retrieves the debts of a User from the database given its id
     *
     * @param id of the User
     * @return the debts of the User
     */
    List<Debt> getDebtsById(long id) throws NoUserFoundException;

}
