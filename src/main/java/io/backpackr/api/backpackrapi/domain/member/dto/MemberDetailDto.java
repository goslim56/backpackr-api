package io.backpackr.api.backpackrapi.domain.member.dto;

import io.backpackr.api.backpackrapi.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDetailDto {
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String gender;
    private Order lastOrder;
}
