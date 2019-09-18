package gupao.homework.Spring.AOP.service;

import gupao.homework.Spring.AOP.AnnotaionAspect;
import gupao.homework.modedesign.adapter.login.Member;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;



@Service
public class MemberService {
    private final static Logger log = Logger.getLogger(AnnotaionAspect.class);
    public Member get(long id) {
        log.info("getMemberById method ...");
        return new Member();
    }

    public Member get() {
        log.info("getMember method ...");
        return new Member();
    }

    public void save(Member member) {
        log.info("save member Method...");
    }

    public boolean delete(long id) throws Exception {
        log.info("delete Method...");
        throw new Exception("spring aop ThrowAdvice演示");
    }
}



































