package com.demo.wkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

public class MyKeyboardView extends KeyboardView {
    int keyXAxis = getResources().getInteger(R.integer.keyXAxis);// 25;
    int keyYAxis = getResources().getInteger(R.integer.keyYAxis);// 50

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("LatinKeyboardView", "onDraw");

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(18);
        paint.setColor(Color.LTGRAY);

        List<Keyboard.Key> keys = this.getKeyboard().getKeys();// getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.label != null) {
                switch (key.codes[0]) {

                    //qQ
                    case 81:
                    case 113:
                    case 1602:
                    case 1618:
                        canvas.drawText("@" /*String.valueOf(1)*/, key.x + (key.width - keyXAxis), key.y + keyYAxis, paint);
                        break;
                        /*
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
*/

                }

            }

        }
    }}
