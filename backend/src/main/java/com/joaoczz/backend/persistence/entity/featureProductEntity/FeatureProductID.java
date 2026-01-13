package com.joaoczz.backend.persistence.entity.featureProductEntity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureProductID implements Serializable {
    private long featureId;
    private long productId;
}
