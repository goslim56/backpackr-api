package io.backpackr.api.backpackrapi.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailDto {
    Long id;
    Long price;
    String memberEmail;
}
