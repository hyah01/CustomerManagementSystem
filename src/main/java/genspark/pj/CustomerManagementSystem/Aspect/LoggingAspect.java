package genspark.pj.CustomerManagementSystem.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class LoggingAspect {

    @Before("execution(* genspark.pj.CustomerManagementSystem.Services.CustomerServiceImpl.*(..))")
    public void logBeforeService(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argsString = Arrays.toString(args); // Or Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        log.info("Executing method: " + methodName + ", Arguments: " + argsString);
    }

    @AfterReturning(pointcut = "execution(* genspark.pj.CustomerManagementSystem.Services.CustomerServiceImpl.*(..))", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argsString = Arrays.toString(args); // Or Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", "));
        log.info("Method executed: " + methodName + ", Arguments: " + argsString +", Return: " + result);
//        if (result.equals()){
//            log.info("No Customer with the" + joinPoint.getSignature().getDeclaringTypeName());
//        }
    }

//    private boolean isEmpty(Object obj){
//        if (obj = null)
//    }


}
