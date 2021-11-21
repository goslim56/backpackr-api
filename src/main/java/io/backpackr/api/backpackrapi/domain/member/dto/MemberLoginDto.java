package io.backpackr.api.backpackrapi.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberLoginDto {
    @NotEmpty(message = "email(이메일)은 필수값입니다.")
    @Size(max = 20, message = "password(패스워드)는 최소 10자리 이상 일력하세요.")
    @Pattern(regexp = "[a-zA-Z가-힣]", message = "이름(name)은 한글, 영문 대소문자만 허용합니다.")
    private String email;

    @NotEmpty(message = "password(패스워드)는 필수값입니다.")
    @Size(min = 10, message = "password(패스워드)는 최소 10자리 이상 일력하세요.")
    private String password;
}
