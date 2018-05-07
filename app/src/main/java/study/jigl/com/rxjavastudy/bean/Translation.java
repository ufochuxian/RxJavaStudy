package study.jigl.com.rxjavastudy.bean;

import study.jigl.com.rxjavastudy.util.JLGLLog;

public class Translation {

    private int status;

    private content content;

    private static class content {
        private String from; //原文内容类型
        private String to; // 译文内容类型
        private String vendor; //来源平台
        private String out; //译文内容
        private int errNo; //请求成功时取 0
    }

    //定义 输出返回数据 的方法
    public void show() {
        JLGLLog.i("RxjavaDemo3", content.out);
    }
}
