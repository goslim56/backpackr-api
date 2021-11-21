package io.backpackr.api.backpackrapi.domain.member;


import io.backpackr.api.backpackrapi.domain.member.dto.MemberDetailDto;
import io.backpackr.api.backpackrapi.domain.member.dto.MemberJoinDto;
import io.backpackr.api.backpackrapi.domain.member.dto.MemberLoginDto;
import io.backpackr.api.backpackrapi.domain.member.dto.MembersDto;
import io.backpackr.api.backpackrapi.domain.order.dto.OrderDetailDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @ApiOperation(
            value = "회원 가입",
            notes = " OK - 성공 <br> "
    )
    @PostMapping("/member/join")
    public String createMemberResponse(
            @ApiParam(
                    name = "회원정보",
                    value = "회원정보가 입력된 JSON",
                    type = "JSON",
                    required = true,
                    example = "{\n" +
                            "    \"name\": \"다\",\n" +
                            "    \"nickname\": \"123\",\n" +
                            "    \"password\": \"123423123123\",\n" +
                            "    \"phone\": \"010-233-5667\",\n" +
                            "    \"email\": \"test2@gmail.com\"\n" +
                            "\n" +
                            "}"
            )
            @RequestBody @Valid MemberJoinDto memberJoinDto) {
        Member member = memberJoinDto.converter();

        if (memberService.isExistingEmail(member.getEmail())) {
            return "이미 가입한 이메일 입니다.";
        }

        if (memberService.isExistingPhone(member.getPhone())) {
            return "이미 가입한 휴대폰전호 입니다.";
        }

        memberService.join(member);
        return "OK";
    }

    @ApiOperation(
            value = "로그인",
            notes = " OK - 성공 <br> "
    )
    @PostMapping("/member/login")
    public String loginMember(
            @ApiParam(
                    name = "로그인",
                    value = "이메일과 비밀번호가 입력된 JSON",
                    type = "JSON",
                    required = true,
                    example = "{\n" +
                            "    \"email\": \"가\",\n" +
                            "    \"password\": \"123423123123\"\n" +
                            "}"
            )
                    HttpServletRequest httpServletRequest, @RequestBody @Valid MemberLoginDto memberLoginDto) {

        Member member = memberService.login(memberLoginDto.getEmail(), memberLoginDto.getPassword());
        if (member == null) {
            return "회원 정보가 일치하지 않습니다.";
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "OK";
    }

    @ApiOperation(
            value = "로그 아웃",
            notes = " Logout OK - 성공 <br> "
    )
    @PostMapping("/member/logout")
    public String loginMember(
            @ApiParam(
                    name = "로그아웃"
            )
                    HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        System.out.println("session = " + member);
        if (member == null) {
            return "로그인 하지 않았습니다.";
        }

        session.invalidate();
        return "Logout OK";
    }

    @ApiOperation(
            value = "단일 회원 상세 정보 조회",
            notes = "Json 형태  회원 정보 응답 - 성공 <br> "
    )
    @GetMapping("/member/detail")
    public MemberDetailDto getDetailMember(
            @ApiParam(
                    name = "단일 회원 상세 정보 조회",
                    value = "이메일 정보",
                    type = "String",
                    required = true,
                    example = "test2@gmail.com"
            )
            String email) {
        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            return null;
        }
        return MemberDetailDto.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .email(member.getEmail())
                .gender(member.getGender())
                .build();
    }

    @ApiOperation(
            value = "단일 회원의 주문 목록 조회",
            notes = "Json 형태  회원 주문 정보 응답 - 성공 <br> "
    )
    @GetMapping("/member/order")
    public List<OrderDetailDto> getOrders(
            @ApiParam(
                    name = "단일 회원의 주문 목록 조회",
                    value = "이메일 정보",
                    type = "String",
                    required = true,
                    example = "test2@gmail.com"
            )
            String email) {
        Member member = memberService.getMemberByEmail(email);

        if (member == null) {
            return null;
        }
        List<OrderDetailDto> orders = new ArrayList<>();
        member.getOrder().forEach(order -> {
            OrderDetailDto orderDetailDto = OrderDetailDto.builder().id(order.getId())
                    .price(order.getPrice())
                    .memberEmail(order.getMember().getEmail())
                    .build();
            orders.add(orderDetailDto);
        });
        return orders;
    }


    @ApiOperation(
            value = "여러 회원 목록 조회",
            notes = "Json 형태  회원 정보 응답 - 성공 <br> "
    )
    @GetMapping("/members")
    public  List<MemberDetailDto> getMembers(
            @ApiParam(
                    name = "여러 회원 목록 조회",
                    value = "이메일 정보",
                    type = "Json",
                    required = false,
                    example = "{\n" +
                            "    \"limit\": 10,\n" +
                            "    \"offset\": 0,\n" +
                            "    \"name\" : \"나\",\n" +
                            "    \"email\" : \"test2@gmail.com\"\n" +
                            "}"
            )
            @RequestBody MembersDto membersDto) {
        return memberService.getMembers(membersDto);
    }


    //
//    @GetMapping("/member/logout")
//    public String loginMember(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member) {
//        System.out.println("session = " + member);
//        if (member == null) {
//            return "로그인 하지 않았습니다.";
//        }
//
//        return "Logout OK";
//    }


}
