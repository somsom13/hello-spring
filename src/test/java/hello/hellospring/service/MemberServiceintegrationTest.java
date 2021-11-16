package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 스프링 통합 테스트!!!!
 */
@SpringBootTest
@Transactional
class MemberServiceintegrationTest {

    @Autowired MemberService memberService;//실제 서비스 객체(중복네임 체크 등)
    @Autowired MemberRepository memberRepository;//기본 저장 동작 객체(id,name 저장, 찾기 등)


    @Test
    void 회원가입() {//test code는 한글로 작성해도됨  => 중복X일 때 정상회원가입 확인
        //given : 주어진 값
        Member member=new Member();
        member.setName("hello");
        //when : 이런이런 부분을 검증했을 때
        Long saveId=memberService.join(member);//join에서 save동작(store로의)

        //then : 나와야 할 결과
        Member findMember=memberService.findOne(saveId).get();//findOne이라는 service객체 메소드
        assertThat(member.getName()).isEqualTo(findMember.getName());//중복name 아닐 때, 정상적으로 회원가입 되는걸 보여주어야함
    }

    @Test
    void 중복_회원_예외(){  //중복 O 일 때 에러 확인
        //given
        Member member1=new Member();
        member1.setName("spring");

        Member member2=new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//member2 join에서 발생하는 중복예외 확인
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 계정");
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail();
//        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 계정");
//        }
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}