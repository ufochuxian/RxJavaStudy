package study.jigl.com.rxjavastudy.demo;


import com.alibaba.fastjson.JSON;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import study.jigl.com.rxjavastudy.MyApplication;
import study.jigl.com.rxjavastudy.bean.LoginResult;
import study.jigl.com.rxjavastudy.bean.RegisterResult;
import study.jigl.com.rxjavastudy.bean.Source;
import study.jigl.com.rxjavastudy.bean.Student;
import study.jigl.com.rxjavastudy.bean.Students;
import study.jigl.com.rxjavastudy.retrofit.GetRequest_Interface;
import study.jigl.com.rxjavastudy.util.JLGLLog;
import study.jigl.com.rxjavastudy.util.JsonFileReader;

public class RxjavaDemo4 {

    private static final String TAG = "RxjavaDemo4";
    private static RxjavaDemo4 instance = null;

    private RxjavaDemo4() {

    }

    public static RxjavaDemo4 getInstance() {
        synchronized (RxjavaDemo4.class) {
            if (instance == null) {
                instance = new RxjavaDemo4();
            }
        }
        return instance;
    }


    //将被观察者发送的每一个事件都通过指定的函数处理，从而变成另外一种事件
    public void useMap() {
        Integer[] array = new Integer[]{1, 2, 3};
        Observable.fromArray(array)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "map变换符号，将integer参数从整形" + integer + "变换成字符串类型:" + integer;
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[useMap],[onSubscribe]");
            }

            @Override
            public void onNext(String s) {
                JLGLLog.i(TAG, "[onNext]:%s", s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    //作用：将被观察者发送的事件序列进行拆分 & 单独转换，再合并成一个新的事件序列，然后再进行发送

    /**
     * 1.为事件序列中每个事件都创建一个 Observable 对象；
     * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     */
    public void useFlatMap() {

        //  获取json数据
        String province_data_json = JsonFileReader.getJson(MyApplication.getContext(), "Students.json");
        Students students = JSON.parseObject(province_data_json, Students.class);

        Observable.fromIterable(students.students)
                .flatMap(new Function<Student, ObservableSource<Source>>() {
                    @Override
                    public ObservableSource<Source> apply(Student student) throws Exception {
                        JLGLLog.i(TAG, "[useFlatMap],[apply],student.id = %s", student.id);
                        return Observable.fromIterable(student.mSources);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Source>() {
                    @Override
                    public void accept(Source source) throws Exception {
                        JLGLLog.i(TAG, "[accept],source.name:%s,source.score:%s", source.name, source.score);

                    }
                });

    }

    /**
     * 1.为事件序列中每个事件都创建一个 Observable 对象；
     * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * 这个方法与flatMap操作符的区别是，拆分 & 重新合并生成的事件序列 = 被观察者旧序列生产的顺序
     */
    public void useConcatMap() {
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(MyApplication.getContext(), "Students.json");
        Students students = JSON.parseObject(province_data_json, Students.class);

        Observable.fromIterable(students.students)
                .concatMap(new Function<Student, ObservableSource<Source>>() {
                    @Override
                    public ObservableSource<Source> apply(Student student) throws Exception {
                        JLGLLog.i(TAG, "[useConcatMap],[apply],student.id = %s", student.id);
                        return Observable.fromIterable(student.mSources);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Source>() {
                    @Override
                    public void accept(Source source) throws Exception {
                        JLGLLog.i(TAG, "[useConcatMap],[accept],source.name:%s,source.score:%s", source.name, source.score);

                    }
                });
    }

    //定期从被观察者（Observable）需要发送的事件中获取一定数量的事件，放到缓存区中，最终发送
    public void useBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        //
                        JLGLLog.d(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            JLGLLog.d(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        JLGLLog.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        JLGLLog.d(TAG, "对Complete事件作出响应");
                    }
                });

    }



    //使用flatmap解决网络请求嵌套回调的问题
    public void useFlatMapWithScene() {

        //step 1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();


        //step 2
        // 步骤2：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //step3
        Observable<RegisterResult> registerObservable = request.getRegisterCall();

        final Observable<LoginResult> loginObservable = request.getLoginCall();


        registerObservable.subscribeOn(Schedulers.io())  // subscribe 会进行初始化创建Observable对象的操作 & subscribeOn(Schedulers.io())，切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) //切换到主线程，处理Register网络请求结果
                .doOnNext(new Consumer<RegisterResult>() {
                    @Override
                    public void accept(RegisterResult registerResult) throws Exception {
                        JLGLLog.i(TAG, "Register网络请求成功");
                        registerResult.show();

                    }
                })
                .observeOn(Schedulers.io()) //特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeron进行切换线程
                .flatMap(new Function<RegisterResult, ObservableSource<LoginResult>>() {
                    @Override
                    public ObservableSource<LoginResult> apply(RegisterResult registerResult) throws Exception {
                        return loginObservable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())// （初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe(new Consumer<LoginResult>() {
                               @Override
                               public void accept(LoginResult loginResult) throws Exception {
                                   JLGLLog.i(TAG, "Login网络请求成功");
                                   loginResult.show();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                JLGLLog.i(TAG, "Login网络请求失败");
                            }
                        }
                );

    }

}
