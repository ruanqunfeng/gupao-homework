package gupao.homework.modedesign.proxy.staticproxy;

public class Father {
    private Son son;
    public Father(Son son) {
        this.son = son;
    }

    public void findLove() {
        System.out.println("父母替儿子物色对象");
        son.findLove();
        System.out.println("同意交往，确认关系");
    }
}
