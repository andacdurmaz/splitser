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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserRepository repo;

    /**
     * getter method for the repository
     * @return repo
     */
    public UserRepository getRepo() {
        return repo;
    }

    /**
     * sets the repository for the User database
     * @param repo the repository for the users
     */
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * path to get all Users in the repository
     * @return all users in repository
     */
    @GetMapping(path = { "", "/" })
    public List<User> getAll() {
        return repo.findAll();
    }

    /**
     * path to get a specific user given its id
     * @param id of the user
     * @return the user with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) throws UserRepository.NoUserFoundException {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getUserById(id));
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<User> add(@RequestBody User user) {
        if ((user == null) ) {
            return ResponseEntity.badRequest().build();
        }
        User saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }
}
