package CustomComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JDial extends JComponent{
    
    private Color border = new Color(80, 80, 80);
    private Color inside = new Color(210, 210, 210);
    private Color pointer = Color.DARK_GRAY;
    private boolean showValue = false;
    private int fontSize = 16;
    
    private Dimension center = new Dimension();

    private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();

    private int x, y;
    private int minValue = 0, maxValue = 360;
    private double angle = 0;
    
    public JDial() {
        super();
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JDial.this.x = e.getX();
                JDial.this.y = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JDial.this.mouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JDial.this.mouseExited();
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JDial.this.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        
        
        g2.setColor(border);
        g2.fillOval(0, 0, this.getWidth(), this.getHeight());
        g2.setColor(inside);
        g2.fillOval(2, 2, this.getWidth() - 4, this.getHeight() - 4);
        
        g2.rotate(angle, center.getWidth(), center.getHeight());
        g2.setColor(this.pointer);
        g2.fillRoundRect((getWidth() / 2) - (getWidth() / 60), (getHeight() / 2) + (getHeight() / 8), getWidth() / 30, getHeight() / 3, 10, 10);
        g2.rotate(-angle, center.getWidth(), center.getHeight());
        
        g2.setColor(Color.BLACK);
        g2.fillOval((int)center.getWidth(), (int)center.getHeight(), 3, 3);
        
        if(this.showValue){
            g2.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
            String text = String.valueOf(this.getValue());
            g2.drawString(text, (this.getWidth() / 2)  - (text.length() * (fontSize / 4)), this.getHeight() / 3);
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        center.setSize(width / 2, height / 2);
    }

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }
    
    private void executeListeners(){
        for(ChangeListener c : changeListeners){
            c.stateChanged(new ChangeEvent(this));
        }
    }
    
    private void mouseEntered() {
        inside = new Color(180, 180, 180);
        repaint();
    }

    private void mouseExited() {
        inside = new Color(210, 210, 210);
        repaint();
    }
    
    public int  getValue(){
        return (int)(((this.maxValue - this.minValue) * (Math.toDegrees(this.angle) / 360)) + this.minValue);
    }
    
    public void setShowValue(boolean show){
        this.showValue = show;
    }
    
    public void setPointerColor(Color pointerColor){
        this.pointer = pointerColor;
    }

    @Override
    public void setBackground(Color bg) {
        this.inside = bg;
    }
    
    public void setFontSize(int size){
        this.fontSize = size;
    }
    
    public void setMinValue(int min){
        this.minValue = min;
    }
    
    public void setMaxValue(int max){
        this.maxValue = max;
    }
    
    private void mouseDragged(MouseEvent e) {
        int oldX = this.x;
        int oldY = this.y;
        int newX = e.getX();
        int newY = e.getY();
        
        int originX = getWidth()/2;
        int originY = getHeight();
        
        double a = Math.sqrt(Math.pow((oldX - newX), 2) + Math.pow((oldY - newY), 2));
        double b = Math.sqrt(Math.pow((oldX - center.getWidth()), 2) + Math.pow((oldY - center.getHeight()), 2));
        double c = Math.sqrt(Math.pow((newX - center.getWidth()), 2) + Math.pow((newY - center.getHeight()), 2));
        double angle = Math.acos(((b * b) + (c * c) - (a * a)) / (2 * b * c));
        
        double oldAngle = this.angle;
        
        if(newX < center.getWidth()){
            if(oldX > center.getWidth() && newY > center.getHeight()){
                this.angle += angle;
            }else if(oldX > center.getWidth() && newY < center.getHeight()){
                this.angle -= angle;
            }
            else{
                double a1 = Math.sqrt(Math.pow((oldX - originX), 2) + Math.pow((oldY - originY), 2));
                double b1 = Math.sqrt(Math.pow((oldX - center.getWidth()), 2) + Math.pow((oldY - center.getHeight()), 2));
                double c1 = Math.sqrt(Math.pow((originX - center.getWidth()), 2) + Math.pow((originY - center.getHeight()), 2));
                double angle1 = Math.acos(((b1 * b1) + (c1 * c1) - (a1 * a1)) / (2 * b1 * c1));
                
                double a2 = Math.sqrt(Math.pow((newX - originX), 2) + Math.pow((newY - originY), 2));
                double b2 = Math.sqrt(Math.pow((newX - center.getWidth()), 2) + Math.pow((newY - center.getHeight()), 2));
                double c2 = Math.sqrt(Math.pow((originX - center.getWidth()), 2) + Math.pow((originY - center.getHeight()), 2));
                double angle2 = Math.acos(((b2 * b2) + (c2 * c2) - (a2 * a2)) / (2 * b2 * c2));
                
                if(angle1 > angle2){
                    this.angle -= angle;
                }else{
                    this.angle += angle;
                }
            }
        }else{
            if(oldX < center.getWidth() && newY > center.getHeight()){
                this.angle -= angle;
            }else if(oldX < center.getWidth() && newY < center.getHeight()){
                this.angle += angle;
            }else{
                double a1 = Math.sqrt(Math.pow((oldX - originX), 2) + Math.pow((oldY - originY), 2));
                double b1 = Math.sqrt(Math.pow((oldX - center.getWidth()), 2) + Math.pow((oldY - center.getHeight()), 2));
                double c1 = Math.sqrt(Math.pow((originX - center.getWidth()), 2) + Math.pow((originY - center.getHeight()), 2));
                double angle1 = Math.acos(((b1 * b1) + (c1 * c1) - (a1 * a1)) / (2 * b1 * c1));
                
                double a2 = Math.sqrt(Math.pow((newX - originX), 2) + Math.pow((newY - originY), 2));
                double b2 = Math.sqrt(Math.pow((newX - center.getWidth()), 2) + Math.pow((newY - center.getHeight()), 2));
                double c2 = Math.sqrt(Math.pow((originX - center.getWidth()), 2) + Math.pow((originY - center.getHeight()), 2));
                double angle2 = Math.acos(((b2 * b2) + (c2 * c2) - (a2 * a2)) / (2 * b2 * c2));
                
                if(angle1 > angle2){
                    this.angle += angle;
                }else{
                    this.angle -= angle;
                }
            }
        }
        
        if(this.angle >= 2 * Math.PI){
            this.angle = 2 * Math.PI;
        }else if(this.angle <= 0){
            this.angle = 0;
        }
        if(oldAngle != this.angle){
            executeListeners();
        }
        
        repaint();
        
        
        this.x = newX;
        this.y = newY;
    }
}
