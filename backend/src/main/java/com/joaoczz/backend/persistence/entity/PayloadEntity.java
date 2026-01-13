package com.joaoczz.backend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payloads")
public class PayloadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime purchaseDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PayloadStatus payloadStatus;
    private Long gatewayTransactionId;
    private String usedPaymentToken;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_id")
    private UserPaymentEntity userPayment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
