package io.backpackr.api.backpackrapi.domain.member;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmail(String email);


    Member findByPhone(String phone);


    Member findByNameAndPassword(String name, String password);
    Member findByEmailAndPassword(String email, String password);

    List<Member> findByEmail(String email, Pageable pageable);

    List<Member> findByName(String name, Pageable pageable);

    List<Member> findByNameAndEmail(String name, String email, Pageable pageable);
}
