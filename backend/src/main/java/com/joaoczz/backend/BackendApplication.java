package com.joaoczz.backend;

import com.joaoczz.backend.persistence.entity.PermissionEntity;
import com.joaoczz.backend.persistence.entity.RoleEntity;
import com.joaoczz.backend.persistence.entity.RoleEnum;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.UserRepository;
import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class BackendApplication {

        public static void main(String[] args) {
                SpringApplication.run(BackendApplication.class, args);
        }
//
//        // Inicializa datos de ejemplo (permisos, roles y usuarios) al iniciar la
//        // aplicación.
//        @Bean
//        @org.springframework.transaction.annotation.Transactional
//        CommandLineRunner init(UserRepository userRepository,
//                        com.joaoczz.backend.persistence.repository.RoleRepository roleRepository,
//                        com.joaoczz.backend.persistence.repository.PermissionRepository permissionRepository,
//                        org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
//                return args -> {
//                        PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
//                        PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
//                        PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
//                        PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();
//
//                        RoleEntity roleAdmin = RoleEntity.builder()
//                                        .roleEnum(RoleEnum.ADMIN)
//                                        .permissions(Set.of(createPermission, readPermission, updatePermission,
//                                                        deletePermission))
//                                        .build();
//
//                        RoleEntity roleUser = RoleEntity.builder()
//                                        .roleEnum(RoleEnum.USER)
//                                        .permissions(Set.of(readPermission))
//                                        .build();
//
//                        RoleEntity roleDeveloper = RoleEntity.builder()
//                                        .roleEnum(RoleEnum.DEVELOPER)
//                                        .permissions(Set.of(createPermission, readPermission, updatePermission,
//                                                        deletePermission))
//                                        .build();
//
//                        RoleEntity roleInvited = RoleEntity.builder()
//                                        .roleEnum(RoleEnum.INVITED)
//                                        .permissions(Set.of(readPermission))
//                                        .build();
//
//                        // Guardamos todos los roles y recuperamos las versiones "gestionadas" (con ID)
//                        java.util.List<RoleEntity> savedRoles = roleRepository
//                                        .saveAll(java.util.List.of(roleAdmin, roleUser, roleDeveloper, roleInvited));
//
//                        // Buscamos los roles específicos para Duzz entre los guardados
//                        Set<RoleEntity> duzzRoles = savedRoles.stream()
//                                        .filter(r -> r.getRoleEnum() == RoleEnum.ADMIN
//                                                        || r.getRoleEnum() == RoleEnum.DEVELOPER)
//                                        .collect(java.util.stream.Collectors.toSet());
//
//                        // Buscamos si el usuario ya existe para actualizarlo, o lo creamos si no
//                        UserEntity userDuzz = userRepository.findUserEntityByUsername("Duzz")
//                                        .orElse(new UserEntity());
//
//                        userDuzz.setUsername("Duzz");
//                        userDuzz.setPassword(passwordEncoder.encode("admin123"));
//                        userDuzz.setName("Joao");
//                        userDuzz.setSurname("Cozzarelli");
//                        userDuzz.setEmail("joaoczz0205@gmail.com");
//                        userDuzz.setBirthDay(LocalDate.of(2003, 05, 02));
//                        userDuzz.setCreatedAt(LocalDateTime.now());
//                        userDuzz.setEnabled(true);
//                        userDuzz.setAccountNoExpired(true);
//                        userDuzz.setAccountNoLocked(true);
//                        userDuzz.setCredentialNoExpired(true);
//                        userDuzz.setRoles(duzzRoles); // Aquí es donde se actualiza la tabla Many-to-Many
//
//                        userRepository.save(userDuzz);
//                        System.out.println("DEBUG: Usuario Duzz sincronizado con roles: " + duzzRoles.size());
//                };
//        }

}

