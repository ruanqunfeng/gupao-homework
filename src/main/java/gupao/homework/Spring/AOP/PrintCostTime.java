package gupao.homework.Spring.AOP;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zed
 * @version 1.0
 * @date 2020/5/20 16:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintCostTime {
    /** 输出方法名称 **/
    @AliasFor("name")
    String name() default "";

    /**
     * 接口超时时间,单位毫秒.默认值100毫秒
     * @return 设置的超时时间
     */
    int timeout() default 100;

    /**
     * 注解上的打印参数
     * @return 输出的时候打印出来
     */
    String[] printArgs() default {};

    /**
     * 是否开启日志监控功能默认开
     * @return 返回ture需要发送邮件
     */
    boolean enablePrint() default true;

    /**
     * 是否允许打印在默认列表里的参数(默认 true)
     * @return
     */
    boolean enablePrintDefaultArgs() default true;

    /**
     * 当接口响应超时时,是否发送邮件.默认发送
     * @return 返回ture需要发送邮件
     */
    boolean emailIfTimeout() default true;
}
