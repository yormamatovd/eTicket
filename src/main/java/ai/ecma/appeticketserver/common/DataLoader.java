package ai.ecma.appeticketserver.common;

import ai.ecma.appeticketserver.entity.Role;
import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.enums.RoleEnum;
import ai.ecma.appeticketserver.repository.RoleRepository;
import ai.ecma.appeticketserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Role roleUser = new Role(
                    "USER",
                    null,
                    new HashSet<>(),
                    RoleEnum.ROLE_USER
            );

            Role roleAdmin = new Role(
                    "SUPER_ADMIN",
                    null,
                    new HashSet<>(Arrays.asList(PermissionEnum.values())),
                    RoleEnum.ROLE_ADMIN
            );

            roleRepository.saveAll(new ArrayList<>(Arrays.asList(roleAdmin, roleUser)));

            User adminUser = User.builder()
                    .setEmail("blabla@gmail.com")
                    .setFirstName("Admin")
                    .setPassword(passwordEncoder.encode("root123"))
                    .setPhoneNumber("+998999876543")
                    .setRole(roleAdmin)
                    .setEnabled(true)
                    .build();
            userRepository.save(adminUser);

        }
    }
}
