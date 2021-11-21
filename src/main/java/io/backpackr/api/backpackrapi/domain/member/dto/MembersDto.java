package io.backpackr.api.backpackrapi.domain.member.dto;

import lombok.Getter;

@Getter
public class MembersDto {
    private String name;
    private String email;

    private Integer limit;

    private Integer offset;
}
