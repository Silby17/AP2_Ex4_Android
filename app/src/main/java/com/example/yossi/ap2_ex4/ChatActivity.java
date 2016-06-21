package com.example.yossi.ap2_ex4;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/*
    The class is in charge of the chat
 */
public class ChatActivity extends ActionBarActivity {

    ListView listview;
    EditText chat_text;
    Button SEND;
    boolean position = true;
    ChatAdapter adapter;
    Context ctx = this;

    /**
     * Perform initialization of all fragments and loaders.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        chat_text.setText("");
        adapter.add(new DataProvider(position, "Nothing much...you?"));
        position = !position;
        chat_text.setText("");
        adapter.add(new DataProvider(position, "Just chillin'"));
        position = !position;
        chat_text.setText("");
        //occurs every time send is being pressed
        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(new DataProvider(position, chat_text.getText()
                        .toString()));
                chat_text.setText("");
            }
        });
    }
}
