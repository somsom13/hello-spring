package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //component scanner로 자동연결 (controller를 spring container에 자동등록)
public class MemberController {
    //@Controller를 명시해주는 순간, spring boot의 스프링컨트롤러에 해당 컨트롤러가 등록되고 관리된다!

    private final MemberService memberService;
    /**
     * memberservice객체를 만드는데, 사실 멤버 서비스에는 별 다른 기능이 없다. (회원가입 및 로그인인데)
     * memberservice를 사용하게 되는 모든 class파일에서 매번 새롭게 new로 객체를 만들어줄 필요가 없다는 것! (그럴만한 기능이 아님)
     * 그래서 위와 같이 참조변수를 선언하고, autowired를 해준다. -> MemberController를 생성함과 동시에 하나의 memberService객체를 연결!
     */
    @Autowired  //-> 생성자를 통해 주입하는 방법.
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }

    //이름 입력 페이지 get
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //입력 페이지의 폼을 post
    @PostMapping("/members/new")//html의 form에서 넣어둔 action url과 동일한 url!(form이 여기로 submit)
    public String create(MemberForm form){//MemberForm은 어떻게 가져오는거지 controller로 등록되어있어서 가능한건가?
        Member member=new Member();
        member.setName(form.getName());//post된 form으로 부터 name val을 가져와서 set

        memberService.join(member);//가입

        return "redirect:/";//홈화면으로 redirect
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();//findAll을 수행하는 메소드
        model.addAttribute("members",members);//list 전체를 model에 담아 넘김
        return "members/memberList";
    }

    /**
     * 이렇게 생성자를 만든 후, autowired하면 membercontroller가 생성될 때 생성자가 호출되면서 스프링빈에서 MemberService가 자동으로 불러와지면서 controller와 연결이 된다!
     * 그런데 스프링이 어떻게 MemberService를 인식해서 가져올 수 있는거지 ? -> MemberService 위에 @Service를 달아준다. 그럼 스프링이 서비스임을 인지하고
     *  MemberService를 등록해줌 repository도 @Repository로 명시!
     */



}
