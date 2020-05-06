package gupao.homework.modedesign.adapter.login.v2;


import gupao.homework.modedesign.adapter.login.ResultMsg;

/**
 * Created by Tom on 2019/3/16.
 */
public class RegistForQQAdapter implements RegistAdapter,LoginAdapter {
    public boolean support(Object adapter) {
        return false;
    }

    @Override
    public ResultMsg regist(String id, Object adapter) {
        return null;
    }

    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
