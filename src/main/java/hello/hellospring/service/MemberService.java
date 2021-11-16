package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//@Service

/**
 * MemberService는 scanner 사용안하고 springConfig로 직접 등록함!
 * 이런 경우에는 나중에 memberservice를 바꿀 필요가 있을 때, 변경이 더 쉬워짐 (다른 코드는 건들지 않고 springConfig만 변경하면 됨) -> 실제 db 연결할 때!
 * 만약에 MemberService가 스프링빈에 등록되어있지 않다면, MemberService내에서 @Autowired 써도 동작하지 않음. 
 * 즉 spring의 관리하에 있는 곳에서만 동작한다
 */
public class MemberService {
    //회원가입 동작 로직을 작성

    private final MemberRepository memberRepository;//인터페이스형 변수 memberRepository가 실제 class 참조
    //MemoryRepository: interface     MemoryMemberRepository: class (실제 메소드 구현)
    //실제 구현된 class를 참조하는 인터페이스형 변수를 만든다면, 그 interface의 메소드들이 오버라이딩 된 값으로 불러와진다! (즉 실제 구현된 class의 메소드가 사용가능해짐)

   // @Autowired    MemberService는 SpringConfig에서 @Bean으로 따로 등록해줌.
    public MemberService(MemberRepository memberRepository){
        //MemberServiceTest.java를 보면, MemberService객체를 만들 때 beforeEach로 만든 MemoryMemberRepository 객체를 파라미터로 넒겨줌.
        this.memberRepository=memberRepository;
    }
    /**
     * 회원가입
     */
    public Long join(Member member){

        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }


        /*//이름 중복 안됨
        validateDuplicateMember(member); //중복 name 체크하는 로직

        memberRepository.save(member);//인터페이스형 참조변수인 memberRepository가 가지는 메소드 호출
        return member.getId();//새롭게 저장된 member 객체의 id return*/


    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        //Optional이면, 아래의 ifPresent를 사용할 수 있다! (과거에는 그냥 null로 확인했음)
            .ifPresent(m->{//present이면 m:true
                throw new IllegalStateException("이미 존재하는 계정");
            });
    }

    /**
     * 전체 회원 조회
     */

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
