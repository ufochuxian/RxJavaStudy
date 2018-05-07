package study.jigl.com.rxjavastudy.demo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import study.jigl.com.rxjavastudy.bean.Translation1;
import study.jigl.com.rxjavastudy.retrofit.PostRequest_Interface;
import study.jigl.com.rxjavastudy.util.JLGLLog;

public class RetrofitDemo {

    //实现的功能：将 英文 翻译成 中文
    //实现方法：使用Retrofit采用post方法对有道API发送网络请求

    private static final String TAG = "RetrofitDemo";

    private static RetrofitDemo instance = null;

    private RetrofitDemo() {

    }

    public static RetrofitDemo getInstance() {
        synchronized (RetrofitDemo.class) {
            if (instance == null) {
                instance = new RetrofitDemo();
            }
        }
        return instance;
    }


    public void requestByGet() {
        RxjavaDemo3.getInstance().pollingRequest();
    }

    public void requestByPost() {

        //step4 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //step5 创建网络接口的实例
        PostRequest_Interface postRequest = retrofit.create(PostRequest_Interface.class);

        //对发送请求进行封装（设置需要翻译的内容）
        //这里直接使用了
        Call<Translation1> call = postRequest.getCall("I love you");



        //step6 发送网络请求（异步） (还有哥execute , 那是同步的发送请求)
        call.enqueue(new Callback<Translation1>() {

            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                JLGLLog.i(TAG, "[requestByPost],[Callback],reponse:%s", response.body().getTranslateResult().get(0).get(0).getTgt());

            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {
                JLGLLog.i(TAG, "[requestByPost],[onFailure],e:%s", t);

            }
        });


    }
}
