package com.example.yossi.ap2_ex4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;


public class MessageActivity extends AppCompatActivity implements OnGestureListener {
    private SensorManager sensorMgr;
    private ShakeListener listener;
    private Sensor accelerometer;
    private OnSwipeTouchListener screenSwipListener;
    TextView textview;
    GestureDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //swipe
        textview = new TextView(this);
        textview.setText("Gesture Demo");
        detector = new GestureDetector(this, this);
        setContentView(textview);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setGravity(Gravity.CENTER);
        textview.setText(ab.getTitle().toString());
        textview.setTextSize(20);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(textview);
        //sensor
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        listener = new ShakeListener();

//        //swipe
//        screenSwipListener = new OnSwipeTouchListener(MessageActivity.this) {
//            public void onSwipeTop() {
//                Toast.makeText(MessageActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeBottom() {
//                Toast.makeText(MessageActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        View.setOnTouchListener(screenSwipListener);
//
   }


    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(listener);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Toast.makeText(getApplicationContext(), "Scroll Gesture", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    //shake
    class ShakeListener implements SensorEventListener {
        private static final int FORCE_THRESHOLD = 1500, TIME_THRESHOLD = 100, SHAKE_TIMEOUT = 500;
        private static final int SHAKE_DURATION = 1000, SHAKE_COUNT = 3;
        private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
        private int mShakeCount = 0;
        private long mLastShake, mLastForce, mLastTime;

        @Override
        public void onSensorChanged(SensorEvent event) {
            long now = System.currentTimeMillis();
            if ((now - mLastForce) > SHAKE_TIMEOUT) {
                mShakeCount = 0;
            }

            float[] values = event.values;
            if ((now - mLastTime) > TIME_THRESHOLD) {
                long diff = now - mLastTime;
                float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
                if (speed > FORCE_THRESHOLD) {
                    if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                        mLastShake = now;
                        mShakeCount = 0;
                        Toast.makeText(MessageActivity.this, "Loading", Toast.LENGTH_LONG).show();
                    }
                    mLastForce = now;
                }
                mLastTime = now;
                mLastX = values[SensorManager.DATA_X];
                mLastY = values[SensorManager.DATA_Y];
                mLastZ = values[SensorManager.DATA_Z];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    }
}