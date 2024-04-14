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

import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commons.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.AdminService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController sut;
    @Mock
    private AdminService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        AdminRepositoryTest repo = new AdminRepositoryTest();
        sut = new AdminController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }
    @Test
    public void cannotAddNullPersonTest() throws Exception {
        Admin admin = new Admin(null, null);

        when(service.addNewAdmin(admin)).thenReturn(badRequest().build());

        mockMvc.perform(post("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(admin)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void addPersonTest() throws Exception {
        Admin admin = new Admin("test@email.nl", "Password");

        when(service.addNewAdmin(admin)).thenReturn(ok().build());

        mockMvc.perform(post("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(admin)))
                .andExpect(status().isOk());
    }

    @Test
    public void changePassword() throws Exception {
        Admin admin = new Admin("test@email.nl", "Password");

        when(service.changePassword(admin.getEmail())).thenReturn(ok().build());

        mockMvc.perform(put("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + admin.getEmail() + "\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void changePasswordIfNoAdminExist() throws Exception {
        Admin admin = new Admin("test@email.nl", "Password");

        when(service.changePassword("email@email.nl")).thenReturn(badRequest().build());

        mockMvc.perform(put("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"email@email.nl\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAdminByEmailTest() throws Exception {
        Admin admin = new Admin("test@email.nl" , "password");
        when(service.getAdminByEmail(admin.getEmail()) ).thenReturn(ok().build());

        mockMvc.perform(get("/api/admin/test@email.nl"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAdminByEmailIfNotExistsTest() throws Exception {
        Admin admin = new Admin("test@email.nl" , "password");
        when(service.getAdminByEmail("email@email.nl") ).thenReturn(badRequest().build());

        mockMvc.perform(get("/api/admin/email@email.nl"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        Admin admin = new Admin("test@email.nl" , "password");
        when(service.getAllAdmins() ).thenReturn(List.of(admin));

        mockMvc.perform(get("/api/admin/"))
                .andExpect(status().isOk());
    }

    public String toJson(Object admin) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(admin);
        return json;
    }
}
