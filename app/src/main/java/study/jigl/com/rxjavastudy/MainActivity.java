package study.jigl.com.rxjavastudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import study.jigl.com.rxjavastudy.demo.RxjavaDemo2;
import study.jigl.com.rxjavastudy.demo.RxjavaDemo3;


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

        RxjavaDemo3.getInstance().pollingRequest();




    }


}
