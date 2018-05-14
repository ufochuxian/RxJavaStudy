package study.jigl.com.rxjavastudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import study.jigl.com.rxjavastudy.demo.RetrofitDemo;
import study.jigl.com.rxjavastudy.demo.RxjavaDemo2;
import study.jigl.com.rxjavastudy.demo.RxjavaDemo3;
import study.jigl.com.rxjavastudy.demo.RxjavaDemo4;
import study.jigl.com.rxjavastudy.demo.RxjavaDemo5;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RxjavaDemo1.getInstance().execute();
//        RxjavaDemo1.getInstance().runUseChainStyle();
//        RxjavaDemo1.getInstance().executeByConsumer();
//        RxjavaDemo1.getInstance().interpetSendUseDispose();

//        RxjavaDemo2.getInstance().useJust();
//        RxjavaDemo2.getInstance().useFromArray();
//        RxjavaDemo2.getInstance().useFromIterable();
//        RxjavaDemo2.getInstance().useDefer();
//        RxjavaDemo2.getInstance().useTimer();
//        RxjavaDemo2.getInstance().useInterval();
//        RxjavaDemo2.getInstance().useIntervalRange();
//        RxjavaDemo2.getInstance().useRange();
//        RxjavaDemo2.getInstance().useRangeLong();

//        RxjavaDemo3.getInstance().pollingRequest();

//        RetrofitDemo.getInstance().requestByPost();

//        RxjavaDemo4.getInstance().useMap();
//        RxjavaDemo4.getInstance().useFlatMap();
//        RxjavaDemo4.getInstance().useConcatMap();
//        RxjavaDemo4.getInstance().useFlatMapWithScene();

//        RxjavaDemo5.getInstance().useMerge();
//        RxjavaDemo5.getInstance().useWithOutConcatDelayError();
//        RxjavaDemo5.getInstance().useWithConcatDelayError();
//        RxjavaDemo5.getInstance().useZip();
//        RxjavaDemo5.getInstance().useCombindLatest();
//        RxjavaDemo5.getInstance().useReduce();
//        RxjavaDemo5.getInstance().useCollect();

          RxjavaDemo5.getInstance().useCount();









    }


}
