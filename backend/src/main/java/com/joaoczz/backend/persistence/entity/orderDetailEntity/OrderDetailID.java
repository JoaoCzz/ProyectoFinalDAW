package com.joaoczz.backend.persistence.entity.orderDetailEntity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailID implements Serializable {
    private long orderId;
    private long productId;
}
