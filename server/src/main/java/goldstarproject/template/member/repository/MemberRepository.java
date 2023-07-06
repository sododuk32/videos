package goldstarproject.template.member.repository;

import goldstarproject.template.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);


    //아이디 중복검사
    boolean existsByUsername(String username);

    //아이디 찾기
    Member findByNameAndPhone(String name,String phone);
    Member findByUsername(String username);


    //비밀번호 찾기
    Member findByPhoneAndNameAndUsername(String phone,String name, String username);
}
