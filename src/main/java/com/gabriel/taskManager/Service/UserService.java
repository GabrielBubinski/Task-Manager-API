package com.gabriel.taskManager.Service;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gabriel.taskManager.DTOs.UserRequestDTO;
import com.gabriel.taskManager.Model.Role;
import com.gabriel.taskManager.Model.User;
import com.gabriel.taskManager.Repository.RoleRepository;
import com.gabriel.taskManager.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(UserRequestDTO userRequestDTO) {
        var userRole = roleRepository.findByName(Role.Values.USER.name());

        var userFromDb = userRepository.findByUsername(userRequestDTO.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(userRequestDTO.username());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
