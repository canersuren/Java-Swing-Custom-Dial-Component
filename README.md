# Java-Swing-Custom-Dial-Component
A custom Dial component to use in your Swing applications.

<br>
<br>

![Example](https://github.com/Clowo/Java-Swing-Custom-Dial-Component/blob/master/example.png)

<br>
<br>

| Method | Type | Description |
| --- | --- | --- |
| JDial() | super | Creates new instance of JDial object. |
| setBounds(int x, int y, int width, int height) | void | Moves to (x,y) on parent and sets size (width, height).|
| addChangeListener(ChangeListener listener) | void | Adds a change listener to calls on change event. |
| setShowValue(boolean show) | void | If true writes value at the top of the circle.|
| setPointerColor(Color pointerColor| void | Sets pointer's color. |
| void setBackground(Color bg)| void | Sets background color. |
| setFontSize(int size) | void| Set font size of the value whic is writtef if showValue is set true.|
| setMinValue(int min) | void | Sets the minimum value.|
| setMaxValue(int max) | void | Sets the maximum value.|
| getValue() | int | Returns value mapped from 0 - 360 to min - max|
