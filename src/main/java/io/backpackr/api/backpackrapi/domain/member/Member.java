package io.backpackr.api.backpackrapi.domain.member;

import io.backpackr.api.backpackrapi.domain.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;

    private String gender;

    @Setter
    private Long lastOrderId;

    @OneToMany(mappedBy="member")
    List<Order> order;
}
