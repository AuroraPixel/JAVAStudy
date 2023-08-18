package core;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //运行时生效
@Target(ElementType.TYPE) //作用于类上
public @interface ComponentScan {
    String value() default ""; //扫描路径
}
