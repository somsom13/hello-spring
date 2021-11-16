package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;//실제 서비스 객체(중복네임 체크 등)
    MemoryMemberRepository memberRepository;//기본 저장 동작 객체(id,name 저장, 찾기 등)


    @BeforeEach
    public void beforeEach() {
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberService(memberRepository);
        //하나의 member repository를 사용하기 위함! (매번 다른 repository객체를 만들어서는 안되니까)
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    } //각 test를 진행한 후, store(repository)를 비워준다! -> test 과정에서 중복되는 데이터 발생 방지하기 위해

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