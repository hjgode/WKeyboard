package com.demo.wkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    final static String TAG="MyMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("SENDKEYCODE");
                intent.putExtra("KEYCODE", KeyEvent.KEYCODE_F1);
                sendBroadcast(intent);
                addLog("onClick:sendBroadcast");
            }
        });

        changeIME();
    }

    void changeIME(){
        final String wKeyboard = "com.demo.wkeyboard/.MyInputMethodService";
        // 'this' is an InputMethodService
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            final IBinder token = this.getWindow().getAttributes().token;
            imm.setInputMethod(token, wKeyboard);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Log.e(TAG,"cannot set input method:");
            t.printStackTrace();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.ACTION_DOWN:
                addLog("KeyDown: ");
                break;
            case KeyEvent.ACTION_UP:
                addLog("KeyUp: ");
                break;
        }
        if( !Character.isLetter((char)keyCode))
            addLog(printWithHex(keyCode));
        else
            addLog(Character.toString((char)keyCode));
        return super.onKeyUp(keyCode, event);
    }

    void addLog(String s){
        Log.d(TAG, s);
    }
    String printWithHex(String sIn){
        StringBuilder sOut=new StringBuilder();
        // Step-1 - Convert ASCII string to char array
        char[] ch = sIn.toCharArray();

        for (char c : ch) {
            if(c<0x20 || c>0x7F) {
                // Step-2 Use %H to format character to Hex
                String hexCode = String.format("<0x%H (%d)>", c, (int)c);
                sOut.append(hexCode);
            }else
                sOut.append(c);
        }
        return  sOut.toString();
    }
    String printWithHex(int c){
        StringBuilder sOut=new StringBuilder();
        if(c<0x20 || c>0x7F) {
            // Step-2 Use %H to format character to Hex
            String hexCode = String.format("<0x%H (%d)>", c, (int)c);
            sOut.append(hexCode);
        }else
            sOut.append(c);

        return  sOut.toString();
    }
}
