package CustomComponent;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main {
     public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Component");

        JDial jDial = new JDial();
        jDial.setBounds(20, 20, 150, 150);
        jDial.setShowValue(true);
        jDial.setFontSize(16);
        jDial.setMinValue(0);
        jDial.setMaxValue(1000);
        jDial.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(jDial.getValue());
            }
        });
        frame.add(jDial);
        
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
