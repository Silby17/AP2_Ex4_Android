package com.example.yossi.ap2_ex4;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/***************************************************************************
 * This Class handles the message screen
 **************************************************************************/
public class ChatActivity extends AppCompatActivity implements OnGestureListener {
    private String TAG = "ChatActivity";
    private SharedPreferences preferences;
    private SensorManager sensorMgr;
    private ShakeListener listener;
    private Sensor accelerometer;
    private TextView textview;
    private GestureDetector detector;

    ListView listview;
    EditText chat_text;
    Button SEND;
    boolean position = true;
    ChatAdapter adapter;
    Context ctx = this;

    /*************************************************************************
     * This method is called when the Chat Activity gets created
     * @param savedInstanceState
     ************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Scroll
        textview = new TextView(this);
        textview.setText("");
        detector = new GestureDetector(this, this);
        setContentView(textview);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
        //uploads the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //define the objects in the chat
        listview = (ListView) findViewById(R.id.chat_list_view);
        chat_text = (EditText) findViewById(R.id.chatTxt);
        SEND = (Button) findViewById(R.id.send_button);
        adapter = new ChatAdapter(ctx,R.layout.message_item);
        //setting the addapter of the listview
        listview.setAdapter(adapter);
        listview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listview.setSelection(adapter.getCount()-1);
            }
        });
        //adding static messages to the chat
        adapter.add(new DataProvider(position, "What's up?"));
        position = !position;
       // chat_text.setText("");
        //occurs every time send is being pressed
        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator communicator = new Communicator();
                String msg = chat_text.getText().toString();
                String username = preferences.getString("username", "");
                communicator.newMessagePost(username, msg, new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {
                        if(resultResponse.getResult().equals("1")){
                            Toast.makeText(getApplicationContext(),
                                    R.string.sendSuccess, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(error != null ){
                            Log.e(TAG, error.getMessage());
                            error.printStackTrace();
                        }
                        Toast errorConnecting =
                                Toast.makeText(getApplicationContext(),
                                        R.string.errorWithServer, Toast.LENGTH_LONG);
                        TextView v = (TextView)errorConnecting.getView().
                                findViewById(android.R.id.message);
                        v.setGravity(Gravity.CENTER);
                        errorConnecting.show();
                    }
                });

                //adapter.add(new DataProvider(position, chat_text.getText()
                  //      .toString()));
                //chat_text.setText("");
                chat_text.setText("");
            }

        });
    }

    //scroll
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
    public boolean onDown(MotionEvent e) {return false;}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Toast.makeText(getApplicationContext(), "Scroll Gesture", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {return false;}

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
                        Toast.makeText(ChatActivity.this, "Loading", Toast.LENGTH_LONG).show();
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
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }
}
