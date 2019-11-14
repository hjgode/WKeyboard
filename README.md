# WKeyboard
A simple keyboard but with option to process external broadcasts

The idea is to enable the insert of keystrokes for barcode scanner wedge using the Data Editing Plugin. So you can have, for example, {kx70} or KEYCODE_F1 inside the suffix or prefix and the Data Editing plugin would invoke this keyboard's BroadcastReceiver to send the key stroke.

## TODO

* find a pattern syntax to be parsed to key strokes.
* think about modifier keys like ALT, CTRL, SHIFT. Do only keydown or keydown/keyup? Have to use pairs of ALT, CTRL, SHIFT key down and up? Manage a list of current modifiers?


