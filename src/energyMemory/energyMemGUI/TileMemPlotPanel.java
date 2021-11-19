/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import energyMemory.monitoredData.CouterMemory;

/**
 *
 * @author ruaro
 */
public class TileMemPlotPanel extends JPanel implements MouseListener{

    private static final float PLOT_GAP = 15f;
    private static final float PLOT_INNER_VALUE_MARKER_LENGHT = 4f;
    private static final float PLOT_X_AXIS_MARKER_MIN_INTERVAL = 15f;
    private static final int   PLOT_FONT_SIZE = 10;
    private static final float   X_LEGEND = PLOT_GAP+5;
    private static final float   Y_LEGEND = PLOT_GAP+5;

    
    public enum TileMemPlotType{
        PLOT_L1_D_MISS_RATE,
        PLOT_L1_I_MISS_RATE,
        PLOT_L2_MISS_RATE,
        PLOT_DRAM_ACCESS
    }
    
    private int mouse_x;
    private int tileAddr;
    private CouterMemory energyMemory;
    private ControlPanel controlPanel;
    private TileMemPlotType plotType;
    private String title;
    
    //Data
    private ArrayList<Float> y_values; //X values are the windows number
    private float total_y_value;
    
    
    public TileMemPlotPanel(int tileAddr, TileMemPlotType plotType, CouterMemory energyMemory, ControlPanel controlPanel){
        this.tileAddr = tileAddr;
        this.energyMemory = energyMemory;
        this.controlPanel = controlPanel;
        this.plotType = plotType;
        this.mouse_x = -1;
        initComponents();
        buildData();
    }
    
    private void buildData(){
        
        y_values = new ArrayList<>();
        
        //System.out.println("\n\nPlot Type: "+plotType+" addr: "+tileAddr);
        
        int window_start, window_size;
        float value = 0f;
        window_size = controlPanel.getWindowSize_Kcyles()*1000;
        window_start = 0;
        for (int i = 0; i < controlPanel.getNum_windows(); i++) {
            switch(plotType){
                case PLOT_L1_D_MISS_RATE:
                    value = energyMemory.computeTileWindow_L1_D_miss_rate(tileAddr, window_start, window_start + window_size);
                    break;
                case PLOT_L1_I_MISS_RATE:
                    value = energyMemory.computeTileWindow_L1_I_miss_rate(tileAddr, window_start, window_start + window_size);
                    break;
                case PLOT_L2_MISS_RATE:
                    value = energyMemory.computeTileWindow_L2_miss_rate(tileAddr, window_start, window_start + window_size);
                    break;
                case PLOT_DRAM_ACCESS:
                    //This is a problematic point, what is a DRAM access of point of view of tile, a LOAD_MEM or a L2 miss (they are not the same)
                    value = energyMemory.getTileWindow_LOAD_MEM(tileAddr, window_start, window_start + window_size);
                    break;
            }
            y_values.add(value);
            //System.out.println(window_start+" - "+(window_start + window_size)+" = "+value);
            window_start = window_start + window_size;
        }
        
        switch(plotType){
            case PLOT_L1_D_MISS_RATE:
                total_y_value = energyMemory.computeTile_L1_D_miss_rate(tileAddr);
                title = "L1-D Miss Rate. Total="+String.format("%.2f", total_y_value*100)+"%";
                break;
            case PLOT_L1_I_MISS_RATE:
                total_y_value = energyMemory.computeTile_L1_I_miss_rate(tileAddr);
                title = "L1-I Miss Rate. Total="+String.format("%.2f", total_y_value*100)+"%";
                break;
            case PLOT_L2_MISS_RATE:
                total_y_value = energyMemory.computeTile_L2_miss_rate(tileAddr);
                title = "L2 Miss Rate. Total="+String.format("%.2f", total_y_value*100)+"%";
                break;
            case PLOT_DRAM_ACCESS:
                total_y_value = energyMemory.getTile_LOAD_MEM(tileAddr);
                title = "LOAD_MEM Access. Total="+String.format("%.2f", total_y_value*100)+"%";
                break;
        }
        
        //System.out.println("total_y = "+total_y_value);
        
    }
    
    private void initComponents(){
        this.setLayout(null);
        this.setPreferredSize(new Dimension(300, 300));
        this.setMinimumSize(new Dimension(300, 300));
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        //this.addMouseMotionListener(this);
        this.setFocusable(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        float width, plot_width, height, plot_height;
        
        width = (float) this.getSize().getWidth();
        height = (float) this.getSize().getHeight();
        plot_width = width - PLOT_GAP*2; //times 2 because the space is of two sides
        plot_height = height - PLOT_GAP*2;
        
        //Draw square
        g2.setColor(Color.black);
        g2.draw(new Rectangle2D.Float(PLOT_GAP, PLOT_GAP, plot_width, plot_height));
        
        /*g2.setColor(Color.GRAY);
        float pixel_per_window, next_marker_x;
        pixel_per_window = (plot_width / ((float)controlPanel.getSimultime_cycles())) * ((float) (controlPanel.getWindowSize_Kcyles() * 1000));
        next_marker_x = PLOT_GAP;
        for (int i = 0; i < controlPanel.getNum_windows()-1; i++) {
            next_marker_x += pixel_per_window;
            g2.draw(new Line2D.Float(next_marker_x, PLOT_GAP+plot_height, next_marker_x, PLOT_GAP+plot_height-PLOT_INNER_VALUE_MARKER_LENGHT));
        }*/
        
        //X initial and final marker
        g2.draw(new Line2D.Float(PLOT_GAP, PLOT_GAP+plot_height, PLOT_GAP, PLOT_GAP+plot_height+PLOT_INNER_VALUE_MARKER_LENGHT));
        g2.draw(new Line2D.Float(PLOT_GAP+plot_width, PLOT_GAP+plot_height, PLOT_GAP+plot_width, PLOT_GAP+plot_height+PLOT_INNER_VALUE_MARKER_LENGHT));
        
        //Y initial and final marker
        g2.draw(new Line2D.Float(PLOT_GAP, PLOT_GAP, PLOT_GAP-PLOT_INNER_VALUE_MARKER_LENGHT, PLOT_GAP));
        g2.draw(new Line2D.Float(PLOT_GAP, PLOT_GAP+plot_height, PLOT_GAP-PLOT_INNER_VALUE_MARKER_LENGHT, PLOT_GAP+plot_height));

        //Plot title
        g2.setFont(new Font("Arial", Font.BOLD, PLOT_FONT_SIZE)); 
        g2.drawString(title, PLOT_GAP-5, PLOT_GAP-2);
        
        //X initial value - always 0
        g2.setFont(new Font("Arial", Font.PLAIN, PLOT_FONT_SIZE)); 
        String text_string = "0";
        g2.drawString(text_string, PLOT_GAP, plot_height+PLOT_GAP+13);
        
        //X final value - always the simulated time
        text_string = String.valueOf(((int)controlPanel.getSimultime_cycles()/1000));
        g2.drawString(text_string, (plot_width+PLOT_GAP)-(text_string.length() * 7), plot_height+PLOT_GAP+13);
        
        //X axis legend
        text_string = "time (Kcycles)";
        g2.drawString(text_string, PLOT_GAP + (plot_width/2)-30, plot_height+PLOT_GAP+10);
        
        //Y initial value - always 0
        text_string = "0";
        g2.drawString(text_string, PLOT_GAP-12, plot_height+PLOT_GAP);
        
        //Y initial value - always 1 (is a ration)
        text_string = "1";
        g2.drawString(text_string, PLOT_GAP-12, PLOT_GAP+10);
        
        //Y axis legend
        text_string = "%";
        g2.drawString(text_string, PLOT_GAP-12, PLOT_GAP + (plot_height/2));
        
        //Draw the legend rectangle
        g2.setColor(Color.GRAY);
        g2.draw(new Rectangle2D.Float(X_LEGEND, Y_LEGEND, 60, 24));
        g2.setColor(Color.BLACK);
        
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0x009900));
        //Increase line size
        float dashWidth[] = {3.0f};
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashWidth, 0.0f);
        g2d.setStroke(dashed);
        
        //Legend - total tile value
        g2d.draw(new Line2D.Float(X_LEGEND+3, Y_LEGEND+5, X_LEGEND+15, Y_LEGEND+5));
        g2.drawString("Total", X_LEGEND+20, Y_LEGEND+9);
        
        
        float x_pos, y_pos, pixel_per_window;
        
        //Total y value added over the green line at right
        y_pos = plot_height - (total_y_value * plot_height) + PLOT_GAP;
        g2d.draw(new Line2D.Float(PLOT_GAP, y_pos, PLOT_GAP+plot_width, y_pos));
        text_string = String.format("%.2f", total_y_value*100)+"%";
        g2.drawString(text_string, PLOT_GAP+3, y_pos-3);
        
        
        //Draw the value per window
        g2d.setColor(Color.blue);
        dashed = new BasicStroke(1.5f);
        g2d.setStroke(dashed);
        
        //Legend - window  value
        g2d.draw(new Line2D.Float(X_LEGEND+3, Y_LEGEND+15, X_LEGEND+15, Y_LEGEND+15));
        g2.drawString("Window", X_LEGEND+20, Y_LEGEND+20);
        
        //Finally, the data
        x_pos = PLOT_GAP+1;
        pixel_per_window = (plot_width / ((float)controlPanel.getSimultime_cycles())) * ((float) (controlPanel.getWindowSize_Kcyles() * 1000));
        Path2D p = new Path2D.Float();
        p.moveTo(x_pos,plot_height+PLOT_GAP);
        for (Float y_value : y_values) {
            x_pos += pixel_per_window;
            y_pos = plot_height - (y_value * plot_height) + PLOT_GAP;
            p.lineTo(x_pos, y_pos);
        }
        g2d.draw(p);
        g2d.dispose();
        
        
        //Draw the red line
        if (mouse_x > -1){
            
            //int tick_x = (int)((float)(mouse_x-MAX_NAME_LENGHT) / proportion);
            g2.setColor(Color.RED);
            g2.drawLine((int)mouse_x, (int)(plot_height+PLOT_GAP), (int)mouse_x, (int)PLOT_GAP);
            
            g2.setColor(Color.BLACK);
            int window_index = (int) Math.floor( ( ((float)mouse_x) - PLOT_GAP) / pixel_per_window);
            if (window_index >= y_values.size()){
                window_index = y_values.size()-1;
            }
            
            String mouse_time = " Win. "+String.valueOf(window_index+1)+"="+String.format("%.2f", y_values.get(window_index));
            int max_string_lenght = mouse_time.length() * 7;
            
            x_pos = mouse_x;
            if (mouse_x >= ((int)(PLOT_GAP+plot_width) - max_string_lenght)) {
                x_pos = mouse_x - max_string_lenght;
            } 
            
            g2.setColor(new Color(0xEBEBEB));
            g2.fill(new RoundRectangle2D.Float(x_pos, 10+PLOT_GAP, max_string_lenght-8, 12, 10, 10));
            g2.setColor(Color.black);
            g2.draw(new RoundRectangle2D.Float(x_pos, 10+PLOT_GAP, max_string_lenght-8, 12, 10, 10));
            
            g2.drawString(mouse_time, x_pos, 20+PLOT_GAP);
        }
        
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if ((e.getX() >= ((int)PLOT_GAP) && e.getX() <= (this.getSize().width-(int)PLOT_GAP)) && !y_values.isEmpty()){
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
