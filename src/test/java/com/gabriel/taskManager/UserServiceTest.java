package com.gabriel.taskManager;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gabriel.taskManager.Model.User;
import com.gabriel.taskManager.Repository.RoleRepository;
import com.gabriel.taskManager.Repository.UserRepository;
import com.gabriel.taskManager.Service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class listUsers {

        @Autowired
        private MockMvc mockMvc;

        @Test
        @DisplayName("Should forbid access to list users when not admin")
        @WithMockUser(username = "user", authorities = { "SCOPE_user" })
        void shouldForbidListUsersForNonAdmin() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isForbidden()); // 403
        }

        @Test
        @DisplayName("Should allow access to list users when admin")
        @WithMockUser(username = "admin", authorities = { "SCOPE_admin" })
        void shouldAllowListUsersForAdmin() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk()); // 200
        }

    }
}
