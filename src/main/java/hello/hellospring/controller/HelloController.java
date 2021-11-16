package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {  //controller가 server side 역할 해주는듯

    //web app에서 url/hello라고 들어오면 얘가 실행됨 rest api처럼 url을 GetMapping으로 찾아주는 것! 
    @GetMapping("hello")   //입력할 url 명
    public String hello(Model model){
        model.addAttribute("data","spring second day!");
        //data라는 val에 hello!! 라는 값이 들어간 채로 model 전달
        return "hello";//여기의 hello: resources->templates->의 html파일을 렌더링 해준다!
        //여기서는 templates의 hello.html을 찾아서 렌더링 해주는것 : viewResolver의 역할(찾아주는 것)
    }

    //hello-mvc 라는 url로 요청 들어오면, 하단의 메소드 실행
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name,Model model ){//request한 name을 String name으로 넣어줌
        //Requestparam(value="name",requested=true)와 동일, requested의 default가 true라서 생략
        //url에서 값 전달시에, http get 방식으로 url?name=val 로 전달
        model.addAttribute("name",name);//전달받은 parameter name의 값을 value로 전달!
        return "hello-template";
    }//parameter를 받아오려면? RequestParam 사용!



    /*API 사용*/
     @GetMapping("hello-string")
     @ResponseBody  //Response Body 반드시 필요 : http의 body part에 하단의 data를 직접 넣어주겠다는 의미! 
     public String helloString(@RequestParam("name") String name) {
         //MVC와 다르게 Model을 사용하지 않는다!  -> html을 찾아준 것이아니라 그냥 http의 body에 data를 바로 넣어준 것이므로
         //소스코드를 열었을 때 html이 아닌 return한 data만 보여준다
         return "hello " + name;
     }


     /* JSON으로 data 주고받기 위해 API 사용하기! */
    @GetMapping("hello-api")
    @ResponseBody  //default response 값이 JSON  -> message converter 가 동작한다!
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello=new Hello();//Hello 객체 생성 (name값을 가지는 객체임)
        hello.setName(name);//setter
        return hello; //hello 객체의 key-value가 JSON 형태로 페이지에 바로 전달(http body에 바로 삽입)
    }

    static class Hello{  //static class: 큰 class인 HelloController 내부에서 사용되는 클래스, HelloController.Hello
        private String name;

        //getter -> 이 class로 부터 val 가져올 때 사용
        public String getName() {
            return name;
        }

        //setter -> 이 class의 변수에 값 할당할 때 사용
        public void setName(String name) {
            this.name = name;
        }
    }
}
