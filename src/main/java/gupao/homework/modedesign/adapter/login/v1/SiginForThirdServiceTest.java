package gupao.homework.modedesign.adapter.login.v1;


import gupao.homework.modedesign.adapter.login.v1.service.SinginForThirdService;

/**
 * Created by Tom on 2019/3/16.
 */
public class SiginForThirdServiceTest {
    public static void main(String[] args) {
        SinginForThirdService service = new SinginForThirdService();
        service.login("tom","123456");
        service.loginForQQ("sdfasdfasf");
        service.loginForWechat("sdfasfsa");
    }
}
