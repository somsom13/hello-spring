package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//SpringBean에 등록해서 쓰는게 좋다!
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    //hellospring 하위 모든 것에 적용

    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start= System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());
        try{
            Object restult=joinPoint.proceed(); //다음 동작 수행?
            return restult;
        }finally {
            long finish=System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
