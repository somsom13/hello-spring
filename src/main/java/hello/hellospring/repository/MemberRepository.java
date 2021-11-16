package hello.hellospring.repository;
import hello.hellospring.domain.Member; //domain에서 Member 객체 import

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);//member 객체 save
    Optional<Member> findById(Long id);//id, name으로 user find
    Optional<Member> findByName(String name);
    List<Member> findAll();//지금까지의 저장된 모든 회원 list
}
