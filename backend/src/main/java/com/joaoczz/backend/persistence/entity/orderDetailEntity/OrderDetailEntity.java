package com.joaoczz.backend.persistence.entity.orderDetailEntity;

import com.joaoczz.backend.persistence.entity.OrderEntity;
import com.joaoczz.backend.persistence.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetailEntity {
    @EmbeddedId
    private OrderDetailID id;
    @ManyToOne
    @MapsId("orderId")
    private OrderEntity order;
    @ManyToOne
    @MapsId("productId")
    private ProductEntity product;
    @NotNull
    private int quantity;
    @Positive
    private Double unit_price;
    @Positive
    private Double line_total;

}
