package com.demo.wkeyboard;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, Observer {
    private MyKeyboardView keyboardView;
    private Keyboard keyboard, keyboard2;
    private boolean caps = false;
    final static String TAG="MyInputMethodService";
    MyReceiver myReceiver=new MyReceiver();
    int mode=0;
    KeyStates keyStates;

    @Override
    public View onCreateInputView() {
        addLog("onCreateInputView");
        keyboardView = (MyKeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);

        keyboard = new Keyboard(this, R.xml.keys_layout);
        keyboard2=new Keyboard(this, R.xml.keyboard2);

        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);

        ObservableObject.getInstance().addObserver(this);
        IntentFilter intentFilter=new IntentFilter();

        intentFilter.addAction("SENDKEYCODES");
        registerReceiver(myReceiver, intentFilter);

        return keyboardView;
    }

    class KeyStates{
        boolean iReturn=false;
        public boolean ALT_PRESSED=false;
        public boolean CTRL_PRESSED=false;
        public boolean SHIFT_PRESSED=false;
        //toggle ALT, CTRL or SHIFT and return what was changed
        public boolean toggleKey(int keycode){
            if( keycode==KeyEvent.KEYCODE_ALT_LEFT || keycode==KeyEvent.KEYCODE_ALT_RIGHT){
                ALT_PRESSED=!ALT_PRESSED;
                iReturn=ALT_PRESSED;
                keystates[0]=!keystates[0];
            }else if(keycode==KeyEvent.KEYCODE_CTRL_LEFT || keycode==KeyEvent.KEYCODE_CTRL_RIGHT){
                CTRL_PRESSED=!CTRL_PRESSED;
                iReturn=CTRL_PRESSED;
                keystates[1]=!keystates[1];
            }else if(keycode==KeyEvent.KEYCODE_SHIFT_LEFT ||keycode==KeyEvent.KEYCODE_SHIFT_RIGHT) {
                SHIFT_PRESSED=!SHIFT_PRESSED;
                iReturn=SHIFT_PRESSED;
                keystates[2]=!keystates[2];
            }
            return iReturn;
        }
        public boolean[] keystates=new boolean[]{false, false, false};
    }

    @Override
    public void update(Observable observable, Object data) {
        addLog(String.valueOf("activity observer " + data));
        Intent intent=(Intent)data;
        addLog("update(Observable) : " + (intent!=null? intent.toString():""));
        if(intent.getAction().equals("com.demo.wkeyboard.MyReceiver.SENDKEYCODES")){

            int[] keycodes=intent.getIntArrayExtra("KEYCODES");
            if(keycodes!=null){
                for (int keycode:keycodes
                     ) {
                    addLog("processing: " + keycode);
                    if(     keycode==KeyEvent.KEYCODE_ALT_LEFT ||
                            keycode==KeyEvent.KEYCODE_CTRL_LEFT ||
                            keycode==KeyEvent.KEYCODE_SHIFT_LEFT ||
                            keycode==KeyEvent.KEYCODE_ALT_RIGHT ||
                            keycode==KeyEvent.KEYCODE_CTRL_RIGHT ||
                            keycode==KeyEvent.KEYCODE_SHIFT_RIGHT )
                    {
                        boolean kDownNow =keyStates.toggleKey(keycode);
                        if(kDownNow)
                            onKeyDown(keycode);
                        else
                            onKeyUp(keycode);
                    }else {
                        onKey(keycode, new int[]{});
                    }
                }
            }
        }
    }
    void addLog(String s){
        Log.d(TAG, s);
    }
    @Override
    public void onPress(int i) {
        addLog("onPress: "+i);
    }

    @Override
    public void onRelease(int i) {
        addLog("onRelease: "+i);
    }

    public void onKeyDown(int keyCode) {
        addLog("onKey " + keyCode);
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
        }
    }
    public void onKeyUp(int keyCode) {
        addLog("onKey " + keyCode);
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
        }
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        addLog("onKey " + primaryCode);
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch(primaryCode) {
                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                    break;
                case Keyboard.KEYCODE_CANCEL:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ESCAPE));

                    break;
                case Keyboard.KEYCODE_MODE_CHANGE:
                    if(mode==0) {
                        keyboardView.setKeyboard(keyboard);
                        mode=1;
                    }
                    else if(mode==1) {
                        keyboardView.setKeyboard(keyboard2);
                        mode=0;
                    }
                    break;
                default :
                    if(primaryCode > KeyEvent.KEYCODE_F1 && primaryCode < KeyEvent.KEYCODE_F10){
                        try {
                            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
                            Thread.sleep(1);
                            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, primaryCode));
                        }catch (InterruptedException ex){}
                    }
                    else {
                        char code = (char) primaryCode;
                        if (Character.isLetter(code) && caps) {
                            code = Character.toUpperCase(code);
                            inputConnection.commitText(String.valueOf(code), 1);
                            }
                        inputConnection.commitText(String.valueOf(code), 1);
                    }
            }
        }

    }
/*
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("LatinKeyboardView", "onDraw");

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
        paint.setColor(Color.LTGRAY);

        List<Keyboard.Key> keys = keyboardView.getKeyboard().getKeys();// getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.label != null) {
                switch (key.codes[0]) {

                    //qQ
                    case 81:
                    case 113:
                    case 1602:
                    case 1618:
                        canvas.drawText(String.valueOf(1), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //wW
                    case 87:
                    case 119:
                    case 1608:
                    case 1572:
                        canvas.drawText(String.valueOf(2), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //eE
                    case 69:
                    case 101:
                    case 1593:
                    case 1617:
                        canvas.drawText(String.valueOf(3), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;


                    //rR
                    case 82:
                    case 114:
                    case 1585:
                    case 1681:
                        canvas.drawText(String.valueOf(4), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //tT
                    case 84:
                    case 116:
                    case 1578:
                    case 1657:
                        canvas.drawText(String.valueOf(5), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //yY
                    case 89:
                    case 121:
                    case 1746:
                    case 1552:
                        canvas.drawText(String.valueOf(6), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //uU
                    case 85:
                    case 117:
                    case 1569:
                    case 1574:
                        canvas.drawText(String.valueOf(7), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //iI
                    case 73:
                    case 105:
                    case 1740:
                    case 1648:
                        canvas.drawText(String.valueOf(8), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //oO
                    case 79:
                    case 111:
                    case 1729:
                    case 1731:
                        canvas.drawText(String.valueOf(9), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //pP
                    case 80:
                    case 112:
                    case 1662:
                    case 1615:
                        canvas.drawText(String.valueOf(0), key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;


                    //aA
                    case 65:
                    case 97:
                    case 1575:
                    case 1570:
                        canvas.drawText("@", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //sS
                    case 83:
                    case 115:
                    case 1587:
                    case 1589:
                        canvas.drawText("#", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //dD
                    case 68:
                    case 100:
                    case 1583:
                    case 1672:
                        canvas.drawText("$", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //fF
                    case 70:
                    case 102:
                    case 1601:
                    case 1613:
                        canvas.drawText("%", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //gG
                    case 71:
                    case 103:
                    case 1711:
                    case 1594:
                        canvas.drawText("&", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //hH
                    case 72:
                    case 104:
                    case 1726:
                    case 1581:
                        canvas.drawText("-", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //jJ
                    case 74:
                    case 106:
                    case 1580:
                    case 1590:
                        canvas.drawText("+", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //kK
                    case 75:
                    case 107:
                    case 1705:
                    case 1582:
                        canvas.drawText("(", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //lL
                    case 76:
                    case 108:
                    case 1604:
                    case 1614:
                        canvas.drawText(")", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //zZ
                    case 90:
                    case 122:
                    case 1586:
                    case 1584:
                        canvas.drawText("*", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //xX
                    case 88:
                    case 120:
                    case 1588:
                    case 1679:
                        canvas.drawText("\"", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //cC
                    case 67:
                    case 99:
                    case 1670:
                    case 1579:
                        canvas.drawText("\'", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //vV
                    case 86:
                    case 118:
                    case 1591:
                    case 1592:
                        canvas.drawText(":", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //bB
                    case 66:
                    case 98:
                    case 1576:
                    case 1616:
                        canvas.drawText(";", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;

                    //nN
                    case 78:
                    case 110:
                    case 1606:
                    case 1722:
                        canvas.drawText("!", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                    //mM
                    case 77:
                    case 109:
                    case 1605:
                    case 1611:
                        canvas.drawText("?", key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;


                }

            }

        }
    }
*/
    @Override
    public void onText(CharSequence charSequence) {
        addLog("onText");
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

}
