package study.jigl.com.rxjavastudy.demo;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import study.jigl.com.rxjavastudy.bean.Translation;
import study.jigl.com.rxjavastudy.retrofit.GetRequest_Interface;
import study.jigl.com.rxjavastudy.util.JLGLLog;

/**
 * 使用Rxjava的interval操作符配合Retrofit实现轮询网络请求
 *
 */

public class RxjavaDemo3 {

    private static final String TAG = "RxjavaDemo3";
    private static RxjavaDemo3 instance = null;

    private RxjavaDemo3() {

    }

    public static RxjavaDemo3 getInstance() {
        synchronized (RxjavaDemo3.class) {
            if (instance == null) {
                instance = new RxjavaDemo3();
            }
        }

        return instance;
    }


    public void pollingRequest() {
        Observable.interval(1, 3, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long value) throws Exception {
                        JLGLLog.i(TAG, "第" + value + "次轮询");

                        //通过Retrofit发送网络请求
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") //设置网络请求url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持RxJava
                                .build();

                        //创建网络请求接口的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                        //采用Observable <...>形式，对网络请求进行封装,这里将request.getCall()转换成了rxJava的Observable，这里就可以使用
                        //RxJava的形式来处理请求和请求的结果了
                        Observable<Translation> observable = request.getCall();

                        observable.subscribeOn(Schedulers.io()) //切换到io线程,发送网络请求
                                .observeOn(AndroidSchedulers.mainThread()) //切换到主线程,处理请求结果
                                  .subscribe(new Observer<Translation>() {
                                      @Override
                                      public void onSubscribe(Disposable d) {
                                          JLGLLog.i(TAG, "[pollingRequest],[accept],[onSubscribe]");
                                      }

                                      @Override
                                      public void onNext(Translation translation) {
                                          JLGLLog.i(TAG,"[pollingRequest],[onNext]");
                                          translation.show();
                                      }

                                      @Override
                                      public void onError(Throwable e) {

                                      }

                                      @Override
                                      public void onComplete() {

                                      }
                                  });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG,"[pollingRequest],[onSubscribe]");

            }

            @Override
            public void onNext(Long value) {
                JLGLLog.i(TAG, "[pollingRequest],value = %s", value);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
