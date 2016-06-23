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
import android.view.WindowManager;
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
    protected long currentMessage = 0;
    protected long allMessages;
    boolean position = true;
    ListView listview;
    EditText chat_text;
    Button SEND;
    ChatAdapter adapter;
    Context ctx = this;


    /*************************************************************************
     * This method is called when the Chat Activity gets created
     *
     * @param savedInstanceState
     ************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getMessageCount();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Scroll
        textview = new TextView(this);
        textview.setText("");
        detector = new GestureDetector(this, this);
        setContentView(textview);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutParams);
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //define the objects in the chat
        listview = (ListView) findViewById(R.id.chat_list_view);
        chat_text = (EditText) findViewById(R.id.chatTxt);
        SEND = (Button) findViewById(R.id.send_button);
        adapter = new ChatAdapter(ctx, R.layout.message_item);

        /**Sets the Adapter for the list view**/
        listview.setAdapter(adapter);
        listview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listview.setSelection(adapter.getCount() - 1);
            }
        });
		/***Gets all the messages from the server*/
          updatesController();

        /**THe Send message button**/
        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator communicator = new Communicator();
                final String msg = chat_text.getText().toString();
                final String username = preferences.getString("username", "");

                communicator.newMessagePost(username, msg, new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {
                        if (resultResponse.getResult().equals("1")) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.sendSuccess, Toast.LENGTH_SHORT).show();
                            addLatestMessage();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        if (error != null) {
                            Log.e(TAG, error.getMessage());
                            error.printStackTrace();
                        }
                        Toast errorConnecting =
                                Toast.makeText(getApplicationContext(),
                                        R.string.errorWithServer, Toast.LENGTH_LONG);
                        TextView v = (TextView) errorConnecting.getView().
                                findViewById(android.R.id.message);
                        v.setGravity(Gravity.CENTER);
                        errorConnecting.show();
                    }
                });
                chat_text.setText("");
            }
        });
    }


    public void updateCount(long newCount){
        allMessages = newCount;
        updatesController();
    }

 //this function is in charge of showing 10 messages at a time according to the shown ones.
    public void updatesController(){
        //if there is still 10 messages to show
        long y = (allMessages / 10);
        long div = (allMessages % 10);
        //Example - if there are 34 messages run 4 times
        if(0 != div)
        {
            y++;
        }
        while(y > 0){
            tenAtAtime(allMessages - currentMessage);
            y--;
        }
    }

    public void tenAtAtime(long endIndex) {
      long index;
        //there are more than 10 messages
        if(endIndex > 10) {
            index = endIndex - 10;
        }
        else {
            index = 1;
        }

        //show last 10
        for(long i = endIndex; i >= index; i--){
        //for(long i = index; i<= endIndex; i++) {
            //chat_text.setText("");
            getMessageAtIndex(i);
            currentMessage++;
        }
    }

    public void getMessageAtIndex(long index) {
        String id = String.valueOf(index);
        Communicator communicator = new Communicator();
        communicator.getMessagePost(id, new Callback<GetMessageResponse>() {
            @Override
            public void success(GetMessageResponse getMessageResponse, Response response) {
                String username = getMessageResponse.getUsername();
                String message1 = getMessageResponse.getMessage();
                String date = getMessageResponse.getTime();
                adapter.add(new DataProvider(position, message1, username, date));
                position = !position;
                chat_text.setText("");
            }
            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
            }
        });
    }

    public void addLatestMessage(){
        getMessageAtIndex(allMessages + 1);

    }

    public void getMessageCount() {
        Communicator communicator = new Communicator();
        communicator.getMessageCount("count", new Callback<ResultResponse>() {
            @Override
            public void success(ResultResponse resultResponse, Response response) {
                updateCount(Long.valueOf(resultResponse.getResult()));
            }
            @Override
            public void failure(RetrofitError error) {
                if(error != null) {
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
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
        return detector.onTouchEvent(event);}

    @Override
    public boolean onDown(MotionEvent e) {return false;}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {return false;}

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {return false;}

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
                float speed = Math.abs(values[SensorManager.DATA_X] +
                        values[SensorManager.DATA_Y] +
                        values[SensorManager.DATA_Z] -
                        mLastX - mLastY - mLastZ) / diff * 10000;
                if (speed > FORCE_THRESHOLD) {
                    if ((++mShakeCount >= SHAKE_COUNT) &&
                            (now - mLastShake > SHAKE_DURATION)) {
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