package hello.biblecashe.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandleRuntimeException {
    Class<? extends RuntimeException>[] value() default {RuntimeException.class};
}
