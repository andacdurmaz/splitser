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

import commons.User;
import commons.exceptions.NoUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService service;


    /**
     * sets the repository for the User database
     *
     * @param service the repository for the users
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Getter method
     *
     * @return UserService
     */
    public UserService getService() {
        return service;
    }

    /**
     * path to get all Users in the repository
     *
     * @return all users in repository
     */
    @GetMapping(path = {"", "/"})
    public List<User> getAll() {
        return service.findAll();
    }

    /**
     * path to get a specific user given its id
     *
     * @param id of the user
     * @return the user with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id));
    }

    /**
     * path to get a specific user's username given its id
     *
     * @param id of the user
     * @return the username of the user with the given id
     */
    @GetMapping("/{id}/username")
    public ResponseEntity<?> getUsernameById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getUsername());
    }

    /**
     * path to get a specific user's email given its id
     *
     * @param id of the user
     * @return the email of the user with the given id
     */
    @GetMapping("/{id}/email")
    public ResponseEntity<?> getEmailById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getEmail());
    }

    /**
     * path to get a specific user's server URL given its id
     *
     * @param id of the user
     * @return the server URL of the user with the given id
     */
    @GetMapping("/{id}/server")
    public ResponseEntity<?> getServerById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getServerURL());
    }

    /**
     * path to get a specific user's IBAN given its id
     *
     * @param id of the user
     * @return the IBAN of the user with the given id
     */
    @GetMapping("/{id}/iban")
    public ResponseEntity<?> getIBANById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getIban());
    }

    /**
     * path to get a specific user's BIC given its id
     *
     * @param id of the user
     * @return the BIC of the user with the given id
     */
    @GetMapping("/{id}/bic")
    public ResponseEntity<?> getBICById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getBic());
    }

    /**
     * path to get a specific user's money given its id
     *
     * @param id of the user
     * @return the wallet of the user with the given id
     */
    @GetMapping("/{id}/wallet")
    public ResponseEntity<?> getWalletById(@PathVariable("id") long id)
            throws NoUserFoundException {
        if (id < 0 || !service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getUserById(id).getWallet());
    }

    /**
     * path to get a specific user's money given its id
     *
     * @param id of the user
     * @return the wallet of the user with the given id
     */
//    @GetMapping("/{id}/debts")
//    public ResponseEntity<?> getDebtsById(@PathVariable("id") long id)
//            throws NoUserFoundException {
//        if (id < 0 || !service.existsById(id)) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(service.getUserById(id).getDebts());
//    }

    /**
     * Add method
     *
     * @param user user to add
     * @return added user
     */
    @PostMapping(value = "/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        if ((user == null)) {
            return ResponseEntity.badRequest().build();
        }
        User saved = service.save(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * Updates the information of a user with the given id
     * to be renamed to the given newName
     *
     * @param id    user id
     * @param user user to update
     * @return the updated user or bad request
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
                                             @RequestBody User user) {
        if (!service.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        User updatedUser = service.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by id.
     * @param id the user's id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        return service.deleteUser(id);
    }


    /**
     * Gets the specified users debts
     *
     * @param id id of user
     * @return users debts or bad request
     */
//    @GetMapping("{id}/debts")
//    public ResponseEntity<?> getUsersDebt(@PathVariable("id") long id)
//            throws NoUserFoundException {
//        if (!service.existsById(id)) {
//            ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(service.getUsersDebt(id));
//    }
}
