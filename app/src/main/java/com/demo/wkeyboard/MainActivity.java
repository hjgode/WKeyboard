package com.demo.wkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context=this;
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
                final String sPackage="com.demo.wkeyboard";
                final String sClass="MyReceiver";
                final Intent intent=new Intent(sPackage+"."+sClass+"."+ "SENDKEYCODES");
                intent.setComponent(new ComponentName(sPackage,sPackage+"."+sClass));
                intent.putExtra("KEYCODES", new int[]{KeyEvent.KEYCODE_F1});
                sendBroadcast(intent);
                addLog("onClick:sendBroadcast: " + intent.toString());
            }
        });

        changeIME();
    }

    void changeIME(){
        utils.changeIME(context);
/*
        final String wKeyboard = "com.demo.wkeyboard/.MyInputMethodService";
        //get the old default keyboard in case you want to use it later, or keep it enabled
        String oldDefaultKeyboard;// = Settings.Secure.getString(resolver, Setting.Secure.DEFAULT_INPUT_METHOD);
        if (null == context) return ;
        final String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.DEFAULT_INPUT_METHOD
        );
        oldDefaultKeyboard=id;
        if (TextUtils.isEmpty(id))
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> mInputMethodProperties = inputMethodManager.getEnabledInputMethodList();
        Log.d(TAG, "InputMethods");
        for (InputMethodInfo mInputMethod : mInputMethodProperties) {
            Log.d(TAG, mInputMethod.getComponent().getClassName());
            if (id.equals(mInputMethod.getId())) {
                String sKeyboard = mInputMethod.getComponent().getClassName();
                Log.d(TAG, "found: "+sKeyboard);
            }
        }

        // 'this' is an InputMethodService
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            final IBinder token = this.getWindow().getAttributes().token;
            imm.setInputMethod(token, wKeyboard);
        } catch (Throwable t) { // java.lang.NoSuchMethodError if API_level<11
            Log.e(TAG,"cannot set input method:");
            t.printStackTrace();
            InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imeManager.showInputMethodPicker();
        }
*/
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
