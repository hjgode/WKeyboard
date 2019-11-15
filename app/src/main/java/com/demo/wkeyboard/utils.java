package com.demo.wkeyboard;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import android.app.Application;
import static com.demo.wkeyboard.MainActivity.TAG;

public class utils {

    public static void changeIME(Context context){
        final String wKeyboard = "com.demo.wkeyboard/.MyInputMethodService";
        //get the old default keyboard in case you want to use it later, or keep it enabled
        String oldDefaultKeyboard;
        if (null == context)
            return ;
        final String id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.DEFAULT_INPUT_METHOD
        );
        oldDefaultKeyboard=id;
        if (TextUtils.isEmpty(id))
            return;
        if(oldDefaultKeyboard.equals(wKeyboard))
            return;

        /* //######### in case to list IMEs
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> mInputMethodProperties = inputMethodManager.getEnabledInputMethodList();
        Log.d(TAG, "InputMethods");
        for (InputMethodInfo mInputMethod : mInputMethodProperties) {
            Log.d(TAG, mInputMethod.getComponent().getClassName());
            if (id.equals(mInputMethod.getId())) {
                String sKeyboard = mInputMethod.getComponent().getClassName();
                Log.d(TAG, "found: "+sKeyboard);
            }
        }
        */

        // ######## change automatically (will work mostly not) or present IME selection
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            final IBinder token = ((Activity)context).getWindow().getAttributes().token;
            imm.setInputMethod(token, wKeyboard);
        } catch (Exception ex) { // java.lang.NoSuchMethodError if API_level<11
            Log.e(TAG,"cannot set input method: " + ex.getMessage());

            try {
                InputMethodManager imeManager = (InputMethodManager) context.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }catch (Exception ex2){
                Log.e(TAG,"cannot set input method: " + ex2.getMessage());
            }
        }
    }

}
