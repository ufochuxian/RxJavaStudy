package study.jigl.com.rxjavastudy.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import study.jigl.com.rxjavastudy.util.JLGLLog;

public class RxjavaDemo1 {

    private static final String TAG = "RxjavaDemo1";

    private Observer<Integer> mObserver;

    private Observable<Integer> mObservable;

    private static RxjavaDemo1 instance = null;

    public Disposable mDisposable = null;

    private RxjavaDemo1() {

    }

    public static RxjavaDemo1 getInstance() {
        synchronized (RxjavaDemo1.class) {
            if (instance == null) {
                instance = new RxjavaDemo1();
            }
        }

        return instance;
    }

    public void execute() {
        //step1
        createObservable();

        //step2

        createObserver();

        // step 3 create subscriber
        mObservable.subscribe(mObserver);
    }

    private void createObservable() {

        //通过ObservableEmitter对象"发送事件"，并且通知"Observer"
        //作用：1 定义要发送的"事件" 2 发送事件给"Observer"(观察者)
        mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //通过ObservableEmitter对象"发送事件"，并且通知"Observer"
                //作用：1 定义要发送的"事件" 2 发送事件给"Observer"(观察者)
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

                emitter.onComplete();

            }
        });


        //Observable.create Rxjava内部内置了"操作符"简化写法：

        // 方法1：just(T...)：直接将传入的参数依次发送出来
        //Observable observable = Observable.just("A", "B", "C");

        //方法2： from操作符
//        Integer[] numbers = new Integer[]{1,2,3};
//        Observable<Integer> observable = Observable.fromArray(numbers);

    }

    private void createObserver() {
        mObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[createObserver],onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                JLGLLog.i(TAG, "[createObserver],onNext:%s", integer);

            }

            @Override
            public void onError(Throwable e) {
                JLGLLog.i(TAG, "[createObserver],onError:%s", e.toString());

            }

            @Override
            public void onComplete() {
                JLGLLog.i(TAG, "[createObserver],onComplete");

            }
        };


        //RxJava内置了一种Subscriber的实现方式
        // 说明：Subscriber类 = RxJava 内置的一个实现了 Observer 的抽象类，对 Observer 接口进行了扩展

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            //创建对象时候，通过覆写对应事件方法，从而响应事件

            @Override
            public void onSubscribe(Subscription s) {
                //观察者在响应事件之前，默认会最先调用覆写onSubscribe（）方法

            }


            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onNext(Integer o) {

            }

            // 当被观察者生产Error事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onError(Throwable t) {

            }

            // 当被观察者生产Complete事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onComplete() {

            }
        };


        //<--特别注意：2种方法的区别，即Subscriber 抽象类与Observer 接口的区别 -->
        // 相同点：二者基本使用方式完全一致（实质上，在RxJava的 subscribe 过程中，Observer总是会先被转换成Subscriber再使用）
        // 不同点：Subscriber抽象类对 Observer 接口进行了扩展，新增了两个方法：
        // 1. onStart()：在还未响应事件前调用，用于做一些初始化工作
        // 2. unsubscribe()：用于取消订阅。在该方法被调用后，观察者将不再接收 & 响应事件
        // 调用该方法前，先使用 isUnsubscribed() 判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，如果引用不能及时释放，就会出现内存泄露


    }


    //优雅的实现方法 - 基于事件流的链式调用
    //在实际应用中，会将上述步骤&代码连在一起，从而更加简洁、更加优雅，即所谓的 RxJava基于事件流的链式调用
    public void runUseChainStyle() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onNext("D");

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                JLGLLog.i(TAG, "[runUseChainStyle],onSubscribe");
            }

            @Override
            public void onNext(String s) {
                JLGLLog.i(TAG, "[runUseChainStyle],onNext:%s", s);
            }

            @Override
            public void onError(Throwable e) {
                JLGLLog.i(TAG, "[runUseChainStyle],onError");

            }

            @Override
            public void onComplete() {
                JLGLLog.i(TAG, "[runUseChainStyle],onComplete");
            }
        });
    }

    //Rxjava 2.x版本 提供了多个函数式的接口，用于实现简便式的观察者模式
    //以Consumer为列子，实现简便式的观察者模式
    public void executeByConsumer() {
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                JLGLLog.i(TAG, "[executeByConsumer],s = %s", s);
            }
        });
    }


    // 使用Disposable 可以切断"被观察者"和"观察者"之间的联系，但是Observable（被观察者）仍然可以发送事件
    public void interpetSendUseDispose() {
        String[] array = new String[]{"A", "B", "c"};
        Observable.fromArray(array).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String strings) {
                if (strings.equals("B")) {
                    JLGLLog.i(TAG, "interrupt");
                    mDisposable.dispose();
                }
                JLGLLog.i(TAG, "[interpetSendUseDispose],strings:%s", strings);

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
