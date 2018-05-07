package study.jigl.com.rxjavastudy.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import study.jigl.com.rxjavastudy.util.JLGLLog;

/**
 * 展示Rxjava中一些常用的操作符号
 */

public class RxjavaDemo2 {
    private static final String TAG = "RxjavaDemo2";
    private static RxjavaDemo2 instance = null;

    private RxjavaDemo2() {
    }

    public static RxjavaDemo2 getInstance() {
        synchronized (RxjavaDemo2.class) {
            if (instance == null) {
                instance = new RxjavaDemo2();
            }
        }

        return instance;
    }


    public void useJust() {

        //注意最多只能传入10个参数，可以用于数组的遍历

        Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[useJust],onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                JLGLLog.i(TAG, "[useJust],onNext,integer:%s", integer);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void useFromArray() {
        // 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        //可用于数组的遍历 这里数组内容可以超过10个以上的元素
        String[] items = new String[]{"1", "2", "3"};
        Observable.fromArray(items).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[useFromArray],onSubscribe");

            }

            @Override
            public void onNext(String s) {
                JLGLLog.i(TAG, "[useFromArray],onNext,s:%s", s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    // fromIterable（）
    //快速创建1个被观察者对象（Observable）
    //发送事件的特点：直接发送 传入的集合List数据
    //应用场景:快速创建 被观察者对象（Observable） & 发送10个以上事件（集合形式）
    //集合元素遍历

    public void useFromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 2. 通过fromIterable()将集合中的对象 / 数据发送出去
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        JLGLLog.d(TAG, "集合遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        JLGLLog.d(TAG, "集合中的数据元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JLGLLog.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        JLGLLog.d(TAG, "遍历结束");
                    }
                });
    }

    public void otherExtra() {
        // 下列方法一般用于测试使用
        //<-- empty()  -->
        // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
        Observable observable1 = Observable.empty();
        // 即观察者接收后会直接调用onCompleted（）

        //<-- error()  -->
        // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
        // 可自定义异常
        Observable observable2 = Observable.error(new RuntimeException());
        // 即观察者接收后会直接调用onError（）

        //                <-- never()  -->
        // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
        Observable observable3 = Observable.never();
        // 即观察者接收后什么都不调用
    }


    /**
     * 延时创建
     */

    //直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件

    //应用场景：动态创建被观察者对象（Observable） & 获取最新的Observable对象数据

    public String str = "first";

    public void useDefer() {


        //此时observable对象被没有被创建，需要有观察者"订阅"后才会进行创建
        Observable observable = Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.just(str);
            }
        });

        str = "second";

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                JLGLLog.i(TAG, "[useDefer],s:%s", s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //因为是在订阅时才创建，所以str值会取第2次的赋值

    }

    //快速创建一个被观察者对象
    //发送事件的特点：延迟指定时间后，发送一个数值0（Long类型）
    //实际就是延迟指定时间之后，调用一次onNext(0)


    public void useTimer() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "开始采用subscribe连接");

            }

            @Override
            public void onNext(Long value) {
                JLGLLog.i(TAG, "[useTimer],value:%s", value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // timer运行在一个新的线程上
        // 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
    }


    //3秒后，每1s发送一个"数字"增长1，interval默认在computation调度器上执行
    public void useInterval() {
        Observable.interval(3, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[useInterval],onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                JLGLLog.i(TAG, "[useInterval],value:%s", value);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void useIntervalRange() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        JLGLLog.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        JLGLLog.d(TAG, "接收到了事件" + value);
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


    public void useRange() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        Observable.range(3, 10)
                // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        JLGLLog.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        JLGLLog.d(TAG, "接收到了事件" + value);
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

    public void useRangeLong() {
        //rangeLong（）
        //此方法与上面的Range是一样的，区别是支持Long数据类型，这里就不做过多描述了
    }

}
