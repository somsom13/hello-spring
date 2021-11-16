package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;

//자바코드로 스프링 빈 연결하기!
@Configuration
//@Configuration 명시하면, 아래의 로직으로 스프링 빈에 등록
public class SpringConfig {

    //spring jpa가 구현체를 ㅁ나들어둔게 등록된다.
    private final MemberRepository memberRepository;
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //spring container에서 memberRepository를 찾는데, SpringDataJpa 인터페이스를 만들면서
    //MemberRepository interface를 상속했기 때문에 생성자로 호출할 때 찾아쓴다.

    /*private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/

    /*private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    //repository와 service 둘 다 등록하고, service 객체 생성시에(MemberService.java에서) 파라미터로 등록되어있는 repository를 넘겨준다!
    //controller는 어쩔 수 없이 @Controller와 @Autowired 사용!
    @Bean
    public MemberService memberService(){

        return new MemberService(memberRepository);//ctrl+p -> 어떤 파라미터가 필요한지!
    }

   /* @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }*/


    //아래는 jpa사용까지 호출하는 부분.
    //데이터 Jpa를 사용하면 인터페이스 까지만 구현하면 되기 때문에 MemberRepository를 spring에 등록할 필요가 없다.
    //SpringDataJpa 인터페이스에서 상속하면서 등록됨.
    /*@Bean
    public MemberRepository memberRepository(){

        //return new MemoryMemberRepository();
        //return new JdbcMemberTemplateRepository(dataSource);
        return new JpaMemberRepository(em);
    }*/
}
