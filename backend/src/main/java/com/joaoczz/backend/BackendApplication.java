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

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
    // Inicializa datos de ejemplo (permisos, roles y usuarios) al iniciar la aplicación.
    @Bean
    CommandLineRunner init(UserRepository userRepository){
        return args -> {
            PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
            PermissionEntity readPermission   = PermissionEntity.builder().name("READ").build();
            PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
            PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();

            RoleEntity roleAdmin = RoleEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleUser = RoleEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissions(Set.of(readPermission))
                    .build();

            RoleEntity roleDeveloper = RoleEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleInvited = RoleEntity.builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permissions(Set.of(readPermission))
                    .build();

            UserEntity userDuzz = UserEntity.builder()
                    .username("Duzz")
                    .password("$2a$12$KdvpygtR.6Och0L.I5./..K7gC8WF9yFj99bX7BJsj1tK5kWQ.jsG")
                    .name("Joao")
                    .surname("Cozzarelli")
                    .email("joaoczz0205@gmail.com")
                    .birthDay(LocalDate.of(2003,05,02))
                    .createdAt(LocalDateTime.now())
                    .isEnabled(true)           // La cuenta está habilitada
                    .accountNoExpired(true)    // La cuenta no ha expirado
                    .accountNoLocked(true)     // La cuenta no está bloqueada
                    .credentialNoExpired(true) // Las credenciales son válidas
                    .roles(Set.of(roleDeveloper))
                    .build();

            userRepository.save(userDuzz);
        };
    }
}
