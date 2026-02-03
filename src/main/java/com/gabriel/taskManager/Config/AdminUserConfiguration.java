package com.gabriel.taskManager.Config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gabriel.taskManager.Model.Role;
import com.gabriel.taskManager.Model.User;
import com.gabriel.taskManager.Repository.RoleRepository;
import com.gabriel.taskManager.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfiguration implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfiguration(RoleRepository roleRepository,
        UserRepository userRepository,
        BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
            User -> System.out.println("Admin user already exists."),
            () -> {
                var adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("123"));
                adminUser.setRoles(Set.of(roleAdmin));
                userRepository.save(adminUser);
                System.out.println("Admin user created.");
            }
        );
    }

}
