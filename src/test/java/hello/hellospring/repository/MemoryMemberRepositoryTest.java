package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();//이 기능 test

    @AfterEach//모든 @Test 끝날 때 마다 실행행
   public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){//save를 test 하는것! (id와 name이 잘 저장되는지)
        Member member=new Member();
        member.setName("spring");//Member 객체의 setter

        repository.save(member);//save는 MemoryMemberRepository 내의 메소드

        Member result=repository.findById(member.getId()).get();
        //Optional 형태의 data는 get으로 가져온다! 
        Assertions.assertEquals(result,member);//result와 mem이 같은지 비교 -> id,name value가 정상저장 되었는지 확인
    }

    @Test
    public void findByName(){//name일치하는 객체 잘 가져와지는지 확인
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();
        //then
        assertThat(result).isEqualTo(member1);
    }


    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //when
        List<Member> result = repository.findAll();//list len=2
        //then
        assertThat(result.size()).isEqualTo(2); //expected=2, len=2
    }

}
