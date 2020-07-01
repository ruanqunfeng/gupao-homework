package gupao.homework.Spring.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author Zed
 * @version 1.0
 * @date 2020/5/20 16:37
 */
@Component
@Aspect
public class PrintCostTimeAspect {

    @Around("execution(* gupao.homework.Spring.AOP.service..*(..))*")
    public Object aroundPrintCostTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature(); //方法签名
        PrintCostTime printCostTime = signature.getMethod().getAnnotation(PrintCostTime.class);//从签名注解中获取注解内容配置项
        String methodCName = printCostTime.name();//方法中文名称[注解中获取 不填写 为""]
        String methodName = signature.getName();
        //        Class clazz = pjp.getTarget().getClass();  //取拦截的Class
        //RequestMapping requestMapping = signature.getMethod().getAnnotation(RequestMapping.class);//从这里可以获取 请求地址
        //方法名和参数值
        //String[] ArgsNames = signature.getParameterNames();
        //Object[] argsValue = pjp.getArgs();
        //参数打印目前没必要 result 中可以获取 返回的json值
//        String argsLog = getArgsLog(printCostTime, ArgsNames, argsValue);

        long start = System.currentTimeMillis();  //开始时间
        Object[] args = pjp.getArgs(); //取拦截的Args
        Object result = pjp.proceed(args);  //运行被拦截的方法
        if (printCostTime.enablePrint()==false){ //若开关为 true 开启打印
            return result;
        }

        long end = System.currentTimeMillis();
        long diff = end - start;//计算耗时

        //若超过配置的阀值则打印耗时日志
        long thresholdInMs = printCostTime.timeout();
        if (diff >= thresholdInMs) {
            System.out.println(methodCName + ":[" + methodName + "]" + ",响应时间: " + diff + " ms");
            //发邮件发作即可
        }
        return result;
    }
}
