package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Transactional //data저장, 변경할 때 transaction 반드시 필요!
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;//왜 필요하지??

    /*JpaMemberRepository 생성자 (해당 객체 생성시, 전달되는 EnitityManager가 할당됨) */
    public JpaMemberRepository(EntityManager em){

        this.em=em;
    }
    @Override
    public Member save(Member member) {
        em.persist(member);//persist: 영구저장! -> jpa가 insert query, setId까지 다 만들어서 자동으로 db에 저장함!!!
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member=em.find(Member.class,id);//Member.class는 뭐지,,,?
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //jpql이라는 객체지향 쿼리를 사용해야한다!
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)//jpql의 name 값을 지정해줌
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();

        //table이 아닌 memberEntity (객체)를 향해 쿼리를 날리는 것! Member이라는 엔티티 객체 자체를 찾아오는 것?????
        //return null;
    }
}
