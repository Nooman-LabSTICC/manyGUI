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
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import energyMemory.monitoredData.EnergyInfo;

/**
 *
 * @author ruaro
 */
public class TileEnergyPlot extends JPanel implements MouseListener{
    
    public enum TileEnergyPlotType{
        TOTAL_ENERGY,
        WINDOW_ENERGY
    }
    
    private static final float PLOT_GAP = 15f;
    private static final float PLOT_INNER_VALUE_MARKER_LENGHT = 4f;
    private static final float PLOT_X_AXIS_MARKER_MIN_INTERVAL = 15f;
    private static final int   PLOT_FONT_SIZE = 10;
    private static final float   X_LEGEND = PLOT_GAP+5;
    private static final float   Y_LEGEND = PLOT_GAP+10;
    
    private int mouse_x;
    private float total_energy;
    private int tileAddr;
    private ControlPanel controlPanel;
    private EnergyInfo energyInfo;
    private boolean normalized;
    private TileEnergyPlotType plotType;
    
    private ArrayList<Float> serieNoC, serieCPU, serieMem;
    
    public TileEnergyPlot(int tileAddr, ControlPanel controlPanel, EnergyInfo energyInfo, boolean normalized, TileEnergyPlotType plotType){
        this.tileAddr = tileAddr;
        this.controlPanel = controlPanel;
        this.energyInfo = energyInfo;
        this.normalized = normalized;
        this.plotType = plotType;
        this.mouse_x = -1;
        initComponents();
        buildData();
    }
    
    private void initComponents(){
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(300, 250));
        this.setLayout(null);
        
        //this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    private void buildData(){
        serieNoC = new ArrayList<>();
        serieCPU = new ArrayList<>();
        serieMem = new ArrayList<>();
        
        int window_start, window_size;
        float current_sum, value = 0f;
        window_size = controlPanel.getWindowSize_Kcyles()*1000;
        window_start = 0;
        
        total_energy = 0f;
            
        for (int i = 0; i < controlPanel.getNum_windows(); i++) {
             
            current_sum = 0f;
            //System.out.println("\n------\nW:"+i);
            //NoC
            value = energyInfo.computeTileWindowNoCEnergy(tileAddr, window_start, window_start + window_size);
            serieNoC.add(value);
            current_sum += value;
            //System.out.println("NoC:"+value);
            
            value = energyInfo.computeTileWindowCPUEnergy(tileAddr, window_start, window_start + window_size);
            serieCPU.add(value);
            current_sum += value;
            //System.out.println("CPU:"+value);
            
            value = energyInfo.computeTileWindowMemoryEnergy(tileAddr, window_start, window_start + window_size);
            serieMem.add(value);
            current_sum += value;
            //System.out.println("Memory:"+value);
            
            switch(plotType){
                case WINDOW_ENERGY:
                    if (current_sum > total_energy){
                        total_energy = current_sum;
                    }
                    break;
                case TOTAL_ENERGY:
                    total_energy += current_sum;
                    break;
            }
            
            //System.out.println(window_start+" - "+(window_start + window_size)+" = "+value);
            window_start = window_start + window_size;
        }
        //System.out.println("Total energie: "+total_energy);
       
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        int max_windows = controlPanel.getNum_windows();
        if (max_windows == 0)
            return;
        
        Graphics2D g2 = (Graphics2D) g;
        
        float plot_width, plot_height, lower_y, bigger_x;
        
        plot_width = (int) this.getSize().getWidth() - PLOT_GAP*2; //times 2 because the space is of two sides
        plot_height = (int) this.getSize().getHeight() - PLOT_GAP*2;
        lower_y = plot_height + PLOT_GAP;
        bigger_x = plot_width + PLOT_GAP;
        
        
        //Draw square
        g2.setColor(Color.black);
        g2.draw(new Rectangle2D.Float(PLOT_GAP, PLOT_GAP, plot_width, plot_height));
        
        
        //X initial and final marker
        g2.draw(new Line2D.Float(PLOT_GAP, lower_y, PLOT_GAP, lower_y+PLOT_INNER_VALUE_MARKER_LENGHT));
        g2.draw(new Line2D.Float(bigger_x, lower_y, bigger_x, lower_y+PLOT_INNER_VALUE_MARKER_LENGHT));
        
        //Y initial and final marker
        g2.draw(new Line2D.Float(PLOT_GAP, PLOT_GAP, PLOT_GAP-PLOT_INNER_VALUE_MARKER_LENGHT, PLOT_GAP));
        g2.draw(new Line2D.Float(PLOT_GAP, lower_y, PLOT_GAP-PLOT_INNER_VALUE_MARKER_LENGHT, lower_y));

        //Plot title
        g2.setFont(new Font("Arial", Font.BOLD, PLOT_FONT_SIZE)); 
        String title_text = "";
        switch(plotType){
            case WINDOW_ENERGY:
                title_text = "Window Energy";
                break;
            case TOTAL_ENERGY:
                title_text = "Total Energy";
                break;
        }
        if(normalized){
            title_text += " - Norm.";
        }
        g2.drawString(title_text, PLOT_GAP-5, PLOT_GAP-2);
        
        //X initial value - always 0
        g2.setFont(new Font("Arial", Font.PLAIN, PLOT_FONT_SIZE)); 
        String text_string = "0";
        g2.drawString(text_string, PLOT_GAP, lower_y+13);
        
        //X final value - always the simulated time
        text_string = String.valueOf(((int)controlPanel.getSimultime_cycles()/1000));
        g2.drawString(text_string, bigger_x-(text_string.length() * 7), lower_y+13);
        
        //X axis legend
        text_string = "time (Kcycles)";
        g2.drawString(text_string, PLOT_GAP + (plot_width/2)-30, lower_y+10);
        
        //Y initial value - always 0
        text_string = "0";
        g2.drawString(text_string, PLOT_GAP-12, lower_y);
        
        
        //Y axis legend
        if (normalized){
            text_string = "%";
            //Y final value - always 1 (is a ration)
            g2.drawString("1", PLOT_GAP-12, PLOT_GAP+10);
        } else {
            text_string = "nJ";
        }
        g2.drawString(text_string, PLOT_GAP-13, PLOT_GAP + (plot_height/2));
        
        //Draw the vertical dashed lines
        /*if (max_windows*2f < plot_width){
            Graphics2D g2d = (Graphics2D) g.create();
            float dashWidth[] = {5.0f};
            BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashWidth, 0.0f);
            g2d.setStroke(dashed);
            g2d.setColor(Color.LIGHT_GRAY);
            
            float pixel_per_window = (plot_width / ((float)controlPanel.getSimultime_cycles())) * ((float) (controlPanel.getWindowSize_Kcyles() * 1000));
            float next_marker_x = PLOT_GAP;
            
            for (int i = 0; i < max_windows-1; i++) {
                next_marker_x += pixel_per_window;
                g2d.draw(new Line2D.Float(next_marker_x, PLOT_GAP+plot_height, next_marker_x, PLOT_GAP));
            }
            g2d.dispose();
        }*/
       
        
        //Draw the bars
        float sumNoC, sumCPU, sumMem, y_pos, bar_height, rec_height_sum, x_pos;
        float total_energy_final;
        
        g2.setColor(new Color(0, 0, 0xff, 80));
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        sumNoC = 0f;
        sumCPU = 0f;
        sumMem = 0f;
        
        //Decrements a litle bit to not invade the plot square
        plot_height-=2;
        plot_width--;
        lower_y--;
        
        float line_offset = plot_width/((float)max_windows);
        
        total_energy_final = total_energy;
        
        for (int window = 0; window < max_windows; window++) {
            
            x_pos = line_offset*((float)window) + PLOT_GAP;
            
            switch(plotType){
            case WINDOW_ENERGY:
                sumNoC = serieNoC.get(window);
                sumCPU = serieCPU.get(window);
                sumMem = serieMem.get(window);
                break;
            case TOTAL_ENERGY:
                sumNoC += serieNoC.get(window);
                sumCPU += serieCPU.get(window);
                sumMem += serieMem.get(window);
                break;
            }
            
            /*if (window == 45 && plotType == TileEnergyPlotType.TOTAL_ENERGY && normalized == false){
                System.out.println("Aqui");
            }*/
            
            if (normalized){
                total_energy_final = sumNoC + sumCPU + sumMem;
            } 
            
            rec_height_sum = 0f;
            //NoC
            if (sumNoC > 0){
                g2d.setColor(Color.GREEN);
                bar_height = (sumNoC / total_energy_final) * plot_height;
                y_pos = lower_y - bar_height - rec_height_sum;
                rec_height_sum += bar_height;
                g2d.fill(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
                g2d.setColor(Color.WHITE);
                g2d.draw(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
            } 
            
            //Memory
            if (sumMem > 0){
                g2d.setColor(Color.BLUE);
                bar_height = (sumMem / total_energy_final) * plot_height;
                y_pos = lower_y - bar_height - rec_height_sum;
                rec_height_sum += bar_height;
                g2d.fill(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
                g2d.setColor(Color.WHITE);
                g2d.draw(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
            }
            
            //CPU
            if (sumCPU > 0){
                g2d.setColor(Color.RED);
                bar_height = (sumCPU / total_energy_final) * plot_height;
                y_pos = lower_y - bar_height - rec_height_sum;
                rec_height_sum += bar_height;
                g2d.fill(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
                g2d.setColor(Color.WHITE);
                g2d.draw(new Rectangle2D.Float(x_pos, y_pos, line_offset, bar_height));
            }
        }
        g2d.dispose();
        
        //Decrements a litle bit to not invade the plot square
        plot_height+=2;
        plot_width++;
        lower_y++;
        
        
        //Draw the red line
        if (mouse_x > -1){
            
            
            //int tick_x = (int)((float)(mouse_x-MAX_NAME_LENGHT) / proportion);
            g2.setColor(Color.RED);
            g2.drawLine((int)mouse_x, (int)(plot_height+PLOT_GAP), (int)mouse_x, (int)PLOT_GAP);
            
            g2.setColor(Color.BLACK);
            float pixel_per_window = (plot_width / controlPanel.getSimultime_cycles()) * ((float) (controlPanel.getWindowSize_Kcyles() * 1000));
            
            int window_index = (int) Math.floor( ( ((float)mouse_x) - PLOT_GAP) / pixel_per_window);
            if (window_index >= max_windows){
                window_index = max_windows-1;
            }
            
            String mouse_time = "Win.="+String.valueOf(window_index+1)+":";
            String textNoC = "NoC=", textCPU= "CPU=", textMem= "Mem=";
            
            switch(plotType){
            case TOTAL_ENERGY:
                sumNoC = getValueSumAtWindow(window_index, serieNoC);
                sumCPU = getValueSumAtWindow(window_index, serieCPU); 
                sumMem = getValueSumAtWindow(window_index, serieMem); 
                break;
            case WINDOW_ENERGY:
                sumNoC = serieNoC.get(window_index);
                sumCPU = serieCPU.get(window_index);
                sumMem = serieMem.get(window_index);
                break;
            }
            
            if (normalized){
                total_energy_final = sumNoC + sumCPU + sumMem;
                if (total_energy_final == 0)//Avoid division by zero
                    total_energy_final = 1;
                textNoC += String.format("%.2f", (sumNoC / total_energy_final * 100f)) + "%";
                textCPU += String.format("%.2f", (sumCPU / total_energy_final * 100f)) + "%";
                textMem += String.format("%.2f", (sumMem / total_energy_final * 100f)) + "%";
            } else {
                textNoC += String.format("%.2f", sumNoC);
                textCPU += String.format("%.2f", sumCPU);
                textMem += String.format("%.2f", sumMem);
            }
            
            int max_string_lenght = (int)(Math.max(mouse_time.length(), Math.max(textNoC.length(), Math.max(textCPU.length(), textMem.length()))) * 7f);
            
            x_pos = mouse_x;
            if (mouse_x >= ((int)(PLOT_GAP+plot_width) - max_string_lenght)) {
                x_pos = x_pos - max_string_lenght;
            } 
            
            //Draw the box
            g2.setColor(new Color(0xEBEBEB));
            g2.fill(new RoundRectangle2D.Float(x_pos, 10+PLOT_GAP, max_string_lenght, 42, 10, 10));
            g2.setColor(Color.black);
            g2.draw(new RoundRectangle2D.Float(x_pos, 10+PLOT_GAP, max_string_lenght, 42, 10, 10));

            y_pos = 20+PLOT_GAP;
            g2.setFont(new Font("Arial", Font.BOLD, PLOT_FONT_SIZE)); 
            g2.drawString(mouse_time, x_pos, y_pos);
            g2.setFont(new Font("Arial", Font.PLAIN, PLOT_FONT_SIZE)); 
            g2.drawString(textNoC, x_pos, y_pos+=10);
            g2.drawString(textCPU, x_pos, y_pos+=10);
            g2.drawString(textMem, x_pos, y_pos+=10);

        }
        
    }
    
    private float getValueSumAtWindow(int window, ArrayList<Float> input){
        float sum = 0f;
        for (int i = 0; (i < window && i < input.size()) ; i++) {
            sum += input.get(i);
        }
        
        return sum;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= PLOT_GAP && e.getX() <= this.getSize().width-PLOT_GAP){
            mouse_x = e.getX();
        } else {
            mouse_x = -1;
        }
        this.repaint();
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
