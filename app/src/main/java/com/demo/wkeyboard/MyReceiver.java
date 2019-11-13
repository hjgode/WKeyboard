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
        if(intent.getAction()=="SENDKEYCODE") {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            addLog("onReceive:SENDKEYCODE");
            ObservableObject.getInstance().updateValue(intent);
        }
    }

    void addLog(String s){
        Log.d(TAG, s);
    }

}
