package com.joaoczz.backend.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_payments")
public class UserPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @NotNull
    private String alias;
    @NotNull
    private String lastFourDigits;
    private LocalDateTime created_at;
    @Column(unique = true)
    private String secure_token;
}
