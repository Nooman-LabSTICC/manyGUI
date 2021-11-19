/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import energyMemory.monitoredData.CouterMemory;
import energyMemory.monitoredData.MemoryEvent.MemoryEventType;

/**
 *
 * @author ruaro
 */
public class TileMemDetailsPanel extends JPanel implements KeyListener, MouseWheelListener, MouseListener{
    
    private boolean crtl_pressed;
    private int tileAddr;
    private float maxSimulTime;
    private int mouse_x;
    private float min_x_time, max_x_time;
    private CouterMemory energyMemory;
    private ControlPanel controlPanel;

    //Timelines
    private ArrayList<ArrayList<Integer>> timelines;
    
    private static final float BOX_GAP = 40f;
    private static final float BOX_INITIAL_POSITION = 10f;
    private static final float BOX_WIDTH = 40f;
    private static final float BOX_HEIGHT = 30f;
    private static final int BOX_FONT_SIZE = 10;
    private static final int VERTICAL_TIME_MARKER = 10;
    private static String[] elements = {"L1-D:Access", "L1-D:Miss", "L1-I:Access", "L1-I:Miss", "L2:Access", "L2:Miss", "AMO:access", "LOAD:MEM" };
    private static Integer[] colors = {0xFF9999, 0xFF9999, 0xFFCC9, 0xFFCC9, 0xFFFF99, 0xFFFF99, 0xCCFF99, 0x99FFFF};
    
    
    public TileMemDetailsPanel(int tileAddr, CouterMemory energyMemory, ControlPanel controlPanel){
        this.tileAddr = tileAddr;
        this.energyMemory = energyMemory;
        this.controlPanel = controlPanel;
        this.maxSimulTime = controlPanel.getSimultime_cycles();
        this.crtl_pressed = false;
        this.mouse_x = -1;
        
        initComponents();
        computeTimelines();
    }
    
    private void computeTimelines(){
        timelines = new ArrayList<>();
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L1_D_ACCESS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L1_D_MISS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L1_I_ACCESS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L1_I_MISS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L2_ACCESS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L2_MISS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.L1_AMO_ACCESS));
        timelines.add(energyMemory.computeTileEventTimeline(tileAddr, MemoryEventType.LOAD_MEM));
    }
    
    private void initComponents(){
        //this.setMinimumSize(new Dimension(100, 700));

        this.setBackground(Color.WHITE);

        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        max_x_time = this.getSize().width - 10;//-10 eh o recuo, experimente remover pra ver como fica
        float box_width = BOX_WIDTH;
        float box_height = BOX_HEIGHT;
        float box_x;
        float box_y;
        float midle_box_height = 0f;
        box_x = BOX_INITIAL_POSITION;
        box_y = BOX_INITIAL_POSITION*2;
        
        min_x_time = box_x+box_width;
        
        for (int i = 0; i < elements.length; i++) {
            String el_name = elements[i];
            
            midle_box_height = box_y+box_height/2f;
            //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Box
            g2.setColor(Color.black);
            g2.setColor(new Color(colors[i]));
            g2.fill(new RoundRectangle2D.Float(box_x, box_y, box_width, box_height, 10, 10));
            g2.setColor(Color.black);
            g2.draw(new RoundRectangle2D.Float(box_x, box_y, box_width, box_height, 10, 10));
            //String
            g2.setFont(new Font("Arial", Font.PLAIN, BOX_FONT_SIZE)); 
            g2.setColor(Color.BLACK);
            g2.drawString(el_name.split(":")[0], box_x+4, box_y+15f);
            g2.drawString(el_name.split(":")[1], box_x+4, box_y+25f);
            //Horizontal Line
            g2.setColor(Color.GRAY);
            g2.draw(new Line2D.Float(min_x_time, midle_box_height, max_x_time, midle_box_height));
            box_y = box_y+BOX_GAP;
        }
            
        //Draw the values
        float usefull_width = max_x_time - (min_x_time);
        box_y = BOX_INITIAL_POSITION*2;
        
        for (int i = 0; i < elements.length; i++) {
            
            midle_box_height = box_y+box_height/2f;
            //g2.setColor(new Color(colors[i]));
            g2.setColor(Color.blue);
            
            ArrayList<Integer> timeline = timelines.get(i);
            float x_time_proportion;
            for (Integer timeInt : timeline) {
                x_time_proportion = ((float) timeInt) / maxSimulTime; //Gives the rate according to the maximum simulated time
                x_time_proportion = (min_x_time) + (x_time_proportion * usefull_width); //Put this time in perspective to the available window size in order to get the x position
                g2.draw(new Line2D.Float(x_time_proportion, box_y, x_time_proportion, box_y+box_height));
            }
            box_y = box_y+BOX_GAP;
        }
        
        //Draw time scale
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, BOX_FONT_SIZE+2)); 
        String x_axis_name;
        //Draw the fixed horizontal line
        midle_box_height = box_y+box_height/2f - 10f;
        g2.draw(new Line2D.Float(min_x_time, midle_box_height, max_x_time, midle_box_height));
        
        //Draw the vertical lines
        
        //Draw the first marker
        x_axis_name = "0";
        g2.draw(new Line2D.Float(min_x_time, midle_box_height, min_x_time, midle_box_height-VERTICAL_TIME_MARKER));
        g2.drawString(x_axis_name, min_x_time, midle_box_height+15);
        
        //Draw the last marker
         x_axis_name = String.valueOf((int)(maxSimulTime/1000f))+"K";
        g2.drawString(x_axis_name, (max_x_time)-(x_axis_name.length()*8), midle_box_height+15);
        g2.draw(new Line2D.Float(max_x_time, midle_box_height, max_x_time, midle_box_height-VERTICAL_TIME_MARKER));
        
        //Write the x-axys name
        x_axis_name = "time (each mark is a window of "+controlPanel.getWindowSize_Kcyles()+"Kcycles)";
        g2.drawString(x_axis_name, (max_x_time/2)-x_axis_name.length()-50, midle_box_height+15);
        
        float pixel_per_window = (usefull_width / maxSimulTime) * ((float) (controlPanel.getWindowSize_Kcyles() * 1000));
        float next_marker_x = min_x_time;
        for (int i = 0; i < controlPanel.getNum_windows(); i++) {
            //current_window = current_window + controlPanel.getWindowSize_Kcyles();
            next_marker_x += pixel_per_window;
            g2.draw(new Line2D.Float(next_marker_x, midle_box_height, next_marker_x, midle_box_height-VERTICAL_TIME_MARKER));
        }
        
        
        //Draw the red line
        if (mouse_x > -1){
            
            g2.setFont(new Font("Arial", Font.PLAIN, 12)); 
            //int tick_x = (int)((float)(mouse_x-MAX_NAME_LENGHT) / proportion);
            g2.setColor(Color.RED);
            g2.drawLine((int)mouse_x, (int)midle_box_height, (int)mouse_x, 0);
            
            
            g2.setColor(Color.BLACK);
            pixel_per_window = maxSimulTime / usefull_width;
            String mouse_time = "t: "+String.valueOf((int)(pixel_per_window * (mouse_x - min_x_time)));
            
            int max_string_lenght = mouse_time.length() * 7;
            
            if (mouse_x >= (max_x_time - max_string_lenght)) {
                g2.drawString(mouse_time, mouse_x - max_string_lenght, 10);
            } else { 
                g2.drawString(mouse_time, mouse_x + 5, 10);
            }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){
            crtl_pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (crtl_pressed){
            if (e.getPreciseWheelRotation() > 0){
                if (this.getSize().getWidth() > this.getMinimumSize().width){
                    this.setPreferredSize(new Dimension((int) (this.getSize().getWidth()/1.1d), this.getSize().height));
                }
            } else {
                this.setPreferredSize(new Dimension((int) (this.getSize().getWidth()*1.1d), this.getSize().height));
            }
            this.revalidate();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= ((int)min_x_time) && e.getX() <= ((int)max_x_time)){
            mouse_x = e.getX();
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
