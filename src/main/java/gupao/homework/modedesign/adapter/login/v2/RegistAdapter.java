package gupao.homework.modedesign.adapter.login.v2;


import gupao.homework.modedesign.adapter.login.ResultMsg;

/**
 * Created by Tom on 2019/3/16.
 */
public interface RegistAdapter {
    boolean support(Object adapter);
    ResultMsg login(String id, Object adapter);
}
