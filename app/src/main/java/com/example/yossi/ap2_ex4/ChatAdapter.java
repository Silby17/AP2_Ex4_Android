package com.example.yossi.ap2_ex4;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<DataProvider> {
    private List<DataProvider> chat_list = new ArrayList<DataProvider>();
    private TextView CHAT_TXT;
    private TextView USER_NAME;
    private TextView TIME;


    Context CTX;
    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        CTX = context;
        // TODO Auto-generated constructor stub
    }
    @Override
    public void add(DataProvider object) {
        // TODO Auto-generated method stub
        chat_list.add(object);
        super.add(object);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chat_list.size();
    }
    @Override
    public DataProvider getItem(int position) {
        // TODO Auto-generated method stub
        return chat_list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) CTX.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.message_item
                    ,parent,false);
        }
        CHAT_TXT = (TextView) convertView.findViewById(R.id.singleMessage);
        USER_NAME = (TextView) convertView.findViewById(R.id.txtUsername);
        TIME = (TextView) convertView.findViewById(R.id.time);
        String Message;
        String username;
        String time;
        boolean POSITION;
        DataProvider provider = getItem(position);
        Message = provider.message;
        username = provider.username;
        time = provider.date;
        POSITION = provider.position;
        USER_NAME.setText(username);
        CHAT_TXT.setText(Message);
        TIME.setText(time);
        CHAT_TXT.setBackgroundResource(POSITION ? R.drawable.chat6 : R.drawable
                .chat6);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)CHAT_TXT
                .getLayoutParams();
        params.gravity = Gravity.CENTER;
        CHAT_TXT.setLayoutParams(params);
        return convertView;
    }
}