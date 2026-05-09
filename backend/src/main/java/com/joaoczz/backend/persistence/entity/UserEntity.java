package com.joaoczz.backend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotBlank
    @Size(min = 4, max = 10)
    private String username;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 100)
    private String surname;
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
    @NotBlank
//    @Size(min = 8, max = 15)
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,15}$",
//            message = "La contraseña debe tener entre 8 y 15 caracteres, incluir al menos una mayúscula y un número"
//            )
    @Column(length = 255)
    private String password;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;
    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;
    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;
    @Past
    private LocalDate birthDay;
    private LocalDateTime createdAt;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

}
