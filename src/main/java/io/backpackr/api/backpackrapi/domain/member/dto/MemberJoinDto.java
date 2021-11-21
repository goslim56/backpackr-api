package io.backpackr.api.backpackrapi.domain.member.dto;

import io.backpackr.api.backpackrapi.domain.member.Member;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberJoinDto {
    @NotEmpty(message = "name(이름)은 필수값입니다.")
    @Size(max = 20, message = "password(패스워드)는 최소 10자리 이상 일력하세요.")
    @Pattern(regexp = "[a-zA-Z가-힣]", message = "이름(name)은 한글, 영문 대소문자만 허용합니다.")
    private String name;

    @NotEmpty(message = "nickname(별명)은 필수값입니다.")
    private String nickname;

    @NotEmpty(message = "password(패스워드)는 필수값입니다.")
    @Size(min = 10, message = "password(패스워드)는 최소 10자리 이상 일력하세요.")
    private String password;

    @NotEmpty(message = "phone(전화번호)는 필수값입니다.")
    private String phone;

    @NotEmpty(message = "email 은 필수값입니다.")
    @Email(message = "email 형식에 맞지않습니다 (ex: example@google.com)")
    private String email;

    private String gender;

    public Member converter() {
        return Member.builder()
                .name(this.name)
                .nickname(this.nickname)
                .password(this.password)
                .phone(this.phone)
                .email(this.email)
                .gender(this.gender).build();
    }
}
