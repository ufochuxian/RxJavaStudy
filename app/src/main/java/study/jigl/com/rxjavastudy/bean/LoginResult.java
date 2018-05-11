package study.jigl.com.rxjavastudy.bean;

import study.jigl.com.rxjavastudy.util.JLGLLog;

public class LoginResult {
    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        JLGLLog.d("RxjavaDemo4", "翻译内容 = " + content.out);
    }
}
