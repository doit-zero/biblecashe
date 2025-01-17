package hello.biblecashe.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ClassExceptionHandlerAspect {
    @Pointcut("within(@HandleRuntimeException *)")
    public void classAnnotatedWithHandleException(){}

    @Around("classAnnotatedWithHandleException()")
    public Object handleRuntimeException(ProceedingJoinPoint joinPoint){
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable){
            if(throwable instanceof RuntimeException){
                HandleRuntimeException handleRuntimeException = joinPoint.getTarget().getClass().getAnnotation(HandleRuntimeException.class);

                for(Class<? extends RuntimeException> exceptionClass : handleRuntimeException.value()){
                    if(exceptionClass.isInstance(throwable)){
                        log.info("Handle exception: {}",throwable.getClass().getSimpleName());
                        log.info("Message: {}",throwable.getMessage());
                        return null; // 예외 처리 후 기본 반환 값
                    }
                }
            }
            throw new RuntimeException(throwable);
        }
    }
}
