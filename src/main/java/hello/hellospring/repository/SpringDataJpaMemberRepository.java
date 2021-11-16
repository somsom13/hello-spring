package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>,MemberRepository {
    //interface가 interface를 상속할 때는 extends 사용!
    //JpaRepository를 상속하면, spring에서 자동으로 구현체를 가져다 쓴다,,?
    //JpaRepository에 자주쓰는 기능들이 구현되어 있다! (findAll 등..)
    @Override
    Optional<Member> findByName(String name); //이게 끝임!
}
