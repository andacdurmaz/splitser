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

import commons.Admin;
import server.database.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping(path = { "", "/" })
    public List<Admin> getAll() {
        return service.getAllAdmins();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Admin> getByEmail(@PathVariable("email") String email) {
        return service.getAdminByEmail(email);
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Admin> addNewAdmin(@RequestBody Admin admin) {
        return service.addNewAdmin(admin);
    }

    @PutMapping(path = { "", "/" })
    public ResponseEntity<Admin> changePassword(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        return service.changePassword(email);
    }
}
