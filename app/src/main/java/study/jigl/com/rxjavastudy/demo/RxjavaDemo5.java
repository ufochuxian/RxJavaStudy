package study.jigl.com.rxjavastudy.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import study.jigl.com.rxjavastudy.util.JLGLLog;

public class RxjavaDemo5 {
    private static final String TAG = "RxjavaDemo5";

    private static RxjavaDemo5 instance = null;

    private RxjavaDemo5() {

    }

    public static RxjavaDemo5 getInstance() {
        synchronized (RxjavaDemo5.class) {
            if (instance == null) {
                instance = new RxjavaDemo5();
            }
        }
        return instance;
    }


    //concat操作符[连接操作符]，用于合并多个被观察者的，一起按照顺序发送数据，组合的观察者数量 <=4
    //还有个concatArray，这个操作符号，与concat的区别是它组合的"被观察者"数量可以超过4个
    public void useConcat() {
        Observable.concat(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        JLGLLog.i(TAG, "[useConcat],[onSubscribe]");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        JLGLLog.i(TAG, "[useConcat],[onNext],interger:%s", integer);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //merge（）：组合多个被观察者（＜4个）一起发送数据
    // 注：合并后按照时间线并行执行
    public void useMerge() {
        Observable.merge(
                //发出事件: 0 1 2
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                //发出事件: 2 3 4
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS)) // 从2开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

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

// mergeArray（） = 组合4个以上的被观察者一起发送数据，此处不作过多演示，类似concatArray（）
    }

    //concatDelayError（） / mergeDelayError（）
    //作用：在使用操作符concat和merge的过程中，发生错误，是否停止发送事件（如果没有concatDelayError操作符，那么就会停止发送），如果有操作如果没有concatDelayError操作符符号，那么会略过异常
    //继续进行发送事件
    public void useWithOutConcatDelayError() {

        //这是没有使用concatDelayError符号的时候
        Observable.concat(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new NullPointerException()); // 发送Error事件，因为无使用concatDelayError，所以第2个Observable将不会发送事件
                        emitter.onComplete();
                    }
                }),
                Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

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

        //打印结果
        //接收到了事件1
        //接收到了事件2
        //接收到了事件3
        //对Error事件作出响应
    }

    //concatDelayError（） / mergeDelayError（）
    //作用：在使用操作符concat和merge的过程中，发生错误，是否停止发送事件（如果没有concatDelayError操作符，那么就会停止发送），如果有操作如果没有concatDelayError操作符符号，那么会略过异常
    public void useWithConcatDelayError() {

        //这是使用concatDelayError符号的时候
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new NullPointerException()); // 发送Error事件，因为无使用concatDelayError，所以第2个Observable将不会发送事件
                        emitter.onComplete();
                    }
                }),
                Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

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

//        接收到了事件1
//        接收到了事件2
//        接收到了事件3
//        接收到了事件4
//        接收到了事件5
//        接收到了事件6
//        对Error事件作出响应

    }

    // zip操作符，合并多个事件，该类型的操作符主要是对多个被观察者中的事件进行合并处理
    public void useZip() {
        Observable observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        });
        Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
            }
        });

        Observable.zip(observable1, observable2, new BiFunction() {
            @Override
            public Object apply(Object o, Object o2) throws Exception {
                return "合并事件之后的值:" + o + o2;
            }
        }).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                JLGLLog.i(TAG, "[useZip],[onNext],o:%s", o);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        });
    }


    //当两个Observables中的任何一个发射了数据时，使用一个函数结合每个Observable发射的最近数据项，并且基于这个函数的结果发射数据。
    //CombineLatest操作符行为类似于zip，但是只有当原始的Observable中的每一个都发射了一条数据时zip才发射数据。
    // CombineLatest则在原始的Observable中任意一个发射了数据时发射一条数据。
    // 当原始Observables的任何一个发射了一条数据时，CombineLatest使用一个函数结合它们最近发射的数据，然后发射这个函数的返回值。
    //combineLatestDelayError ,作用类似于concatDelayError（） / mergeDelayError（） ，即错误处理
    public void useCombindLatest() {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
            }
        });


        Observable<Long> observable2 = Observable.intervalRange(0, 4, 1, 1, TimeUnit.SECONDS);

        Observable.combineLatest(observable1, observable2, new BiFunction<Integer, Long, Object>() {
            @Override
            public Object apply(Integer integer, Long value) throws Exception {
                return "value:" + integer + value;
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                JLGLLog.i(TAG, "[useCombindLatest],[onNext],o:%s", o);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    //"聚合"函数，用于实现一些聚合的逻辑
    public void useReduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        JLGLLog.i(TAG, "apply: 聚合参数:integer:" + integer + ",integer2:" + integer2);

                        //重点:这里实现"聚合"的逻辑
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                JLGLLog.i(TAG, "聚合的结果:%s", integer);
            }
        });
    }

    //将被观察者Observable发送的数据事件收集到一个数据结构里
    public void useCollect() {
        Observable.just(1, 2, 3, 4)
                //第一个Callable函数用于生成"数据结构"
                //第二个函数用户使用前面生成的"数据结构"将Observable中发射的数据"收集起来"
                .collect(new Callable<List<Integer>>() {
                    @Override
                    public List<Integer> call() throws Exception {
                        return new ArrayList();
                    }
                }, new BiConsumer<List<Integer>, Integer>() {
                    @Override
                    public void accept(List<Integer> list, Integer integer) throws Exception {
                        JLGLLog.i(TAG, "发送的数据:" + integer);
                        list.add(integer);
                    }
                })
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> list) throws Exception {
                        JLGLLog.i(TAG, "收集的数据集合:%s", list);
                    }
                });
    }

    //startWith & startWithArray
    //在一个被观察者发送事件前，追加发送被观察者 & 发送数据
    // 注: 追加数据顺序 = 后调用先追加
    public void useStartWidth() {
        Observable.just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //统计被观察者发送事件数量
    public void useCount() {
        Observable.just(1, 2, 3)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        JLGLLog.i(TAG, "count:%s", aLong);

                    }
                });

    }


}
