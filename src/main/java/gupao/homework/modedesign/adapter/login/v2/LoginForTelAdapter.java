package gupao.homework.modedesign.adapter.login.v2;


import gupao.homework.modedesign.adapter.login.ResultMsg;

/**
 * Created by Tom.
 */
public class LoginForTelAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
