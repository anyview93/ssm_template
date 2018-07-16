package cn.mine.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GolbalExceptionResolver {

    @Pointcut("execution(* cn.mine.web.*.*(..))")
    public void golbalExceptionResolver(){}

    @After("golbalExceptionResolver()")
    public void doAfter(JoinPoint joinPoint) throws Throwable{
        log.info("方法执行之后");
    }

}
