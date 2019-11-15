package com.demo.wkeyboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class MyReceiver extends BroadcastReceiver {

    final static String TAG="MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        addLog("onReceive: " + (intent!=null? intent.toString():""));
        if(intent.getAction()=="com.demo.wkeyboard.MyReceiver.SENDKEYCODES") {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            addLog("onReceive:SENDKEYCODE(S)");
            ObservableObject.getInstance().updateValue(intent);
        }
    }

    void addLog(String s){
        Log.d(TAG, s);
    }

}
