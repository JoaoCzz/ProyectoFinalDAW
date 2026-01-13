package com.joaoczz.backend.persistence.entity.featureProductEntity;

import com.joaoczz.backend.persistence.entity.FeatureEntity;
import com.joaoczz.backend.persistence.entity.ProductEntity;
import com.joaoczz.backend.persistence.entity.orderDetailEntity.OrderDetailID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feature_products")
public class FeatureProductEntity {
    @EmbeddedId
    private FeatureProductID id;
    @ManyToOne
    @MapsId("featureId")
    private FeatureEntity feature;
    @ManyToOne
    @MapsId("productId")
    private ProductEntity product;
    @NotBlank
    private String details;
}
