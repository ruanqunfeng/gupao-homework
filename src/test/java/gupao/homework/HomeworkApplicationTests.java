package gupao.homework;

import gupao.homework.Spring.AOP.AnnotaionAspect;
import gupao.homework.Spring.AOP.service.MemberService;
import gupao.homework.modedesign.adapter.login.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkApplicationTests {
    @Autowired
    MemberService annotationService;
    @Autowired
    ApplicationContext app;

    @Test
    public void contextLoads() {
        System.out.println("===================这是一条华丽的分割线=================");
        AnnotaionAspect aspect = app.getBean(AnnotaionAspect.class);
        System.out.println(aspect);
        annotationService.save(new Member());

        System.out.println("===================这是一条华丽的分割线=================");
        try {
            annotationService.delete(1L);
        } catch (Exception e) {

        }
    }

}

































