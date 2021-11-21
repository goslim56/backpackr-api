package io.backpackr.api.backpackrapi.domain.member;

import io.backpackr.api.backpackrapi.domain.member.dto.MemberDetailDto;
import io.backpackr.api.backpackrapi.domain.member.dto.MembersDto;
import io.backpackr.api.backpackrapi.domain.order.Order;
import io.backpackr.api.backpackrapi.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final OrderService orderService;

    public Long join(Member member) {
        memberRepository.save(member);
        initOrder(member);
        return member.getId();
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public boolean isExistingEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null;
    }

    public boolean isExistingPhone(String phone) {
        Member member = memberRepository.findByPhone(phone);
        return member != null;
    }

    private void initOrder(Member member) {
        Order order1 = Order.builder()
                .price(10000L)
                .member(member)
                .build();
        Order order2 = Order.builder()
                .price(20000L)
                .member(member)
                .build();

        Order order3 = Order.builder()
                .price(30000L)
                .member(member)
                .build();

        orderService.save(order1);
        orderService.save(order2);
        orderService.save(order3);

        member.setLastOrderId(order3.getId());

    }

    public List<MemberDetailDto> getMembers(MembersDto membersDto) {

        List<MemberDetailDto> memberDetails = new ArrayList<>();
        List<Member> members = null;
        Integer offset = membersDto.getOffset() == null ? 0 : membersDto.getOffset();
        Integer limit = membersDto.getLimit() == null ? 1 : membersDto.getLimit();
        System.out.println("limit = " + limit);
        System.out.println("offset = " + offset);
        Pageable pageable = PageRequest.of(offset, limit);


        if (membersDto.getName() != null && membersDto.getEmail() != null) {
            members = memberRepository.findByNameAndEmail(membersDto.getName(), membersDto.getEmail(), pageable);
        } else if (membersDto.getEmail() != null) {
            members = memberRepository.findByEmail(membersDto.getEmail(), pageable);
        } else if (membersDto.getName() != null) {
            members = memberRepository.findByName(membersDto.getName(), pageable);
        } else {
            members = memberRepository.findAll(pageable).getContent();
        }


        members.forEach(member -> {
            Order lastOrder = null;
            try {
             lastOrder = orderService.findById(member.getLastOrderId());
            } catch (Exception ignored) {
            }
            memberDetails.add(MemberDetailDto.builder()
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .phone(member.getPhone())
                    .email(member.getEmail())
                    .gender(member.getGender())
                    .lastOrder(lastOrder)
                    .build());

        });
        return memberDetails;
    }
}
