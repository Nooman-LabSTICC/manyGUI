/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import energyMemory.energyMemGUI.ControlPanel.TilePlotType;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author ruaro
 */
public class OverviewTilePlotPanel extends JPanel implements MouseMotionListener, MouseListener{
    
    private int xPos; //Graphic position on the upper panel
    private int yPos; //Graphic position on the upper pannel
    private int width; //Plot with in pixes
    private int height; //Plot heigt in pixes
    
    private int mouse_x;
    
    private OverviewTilePanel mainTilePanel;
    private ControlPanel controlPanel;
    private ArrayList<Float> window_values_list;
    private TilePlotType tilePlotType;
    
    public OverviewTilePlotPanel(OverviewTilePanel mainTilePanel, ControlPanel controlPanel, ArrayList<Float> window_values_list, TilePlotType tilePlotType){
        this.mainTilePanel = mainTilePanel;
        this.tilePlotType = tilePlotType;
        this.controlPanel = controlPanel;
        this.window_values_list = window_values_list;
        this.mouse_x = -1;
        
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        this.setLayout(null);
        
        xPos = 20;
        yPos = 20;
        width = 130;
        height = 130;
        
        this.setBounds(xPos, yPos, width, height);
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //if (!(xAddr == 0 && yAddr == 0))
           // return;
        
        int max_windows = window_values_list.size();
        
        if (max_windows == 0)
            return;
        
        //System.out.println("------");
        //System.out.println(energies);
        
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        g2.setColor(new Color(0xE8E8E8));//Light gray
        
        //int line_offset = width/max_windows;
        float line_offset = ((float)width)/((float)max_windows);
        float x1, y1, x2, y2;
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Draw the vertical dashed lines
        if (max_windows*3 < width){
            //Draw the dashed lines
            float dashWidth[] = {5.0f};
            BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashWidth, 0.0f);
            g2d.setStroke(dashed);
            for (int window = 0; window < max_windows; window++) {
                x1 = line_offset* ((float)window);
                y1 = 0f;
                y2 = height;
                
                Shape l = new Line2D.Float(x1, 0, x1, y2);
                
                g2d.draw(l);
            }
        }
        g2d.dispose();
        
        
        //Draw the bars
        float energy, y_pos, max_value = 0f;
        float final_y_pos;
        
        String unit = "";
        switch(tilePlotType){
            case PLOT_ENERGY:
                g2.setColor(new Color(0, 0, 0xff, 80));
                max_value = controlPanel.getMaxValueEnergyTile_nJ();
                unit = "nJ";
                break;
            case PLOT_MEMORY:
                g2.setColor(new Color(108, 152, 3, 100));
                max_value = controlPanel.getMaxMemoryNumber();
                unit = "#";
                break;
        }
        
        g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Path2D p = new Path2D.Float();

        p.moveTo(0,height);
        for (int window = 0; window < max_windows; window++) {
            energy = window_values_list.get(window);
            
            y_pos = energy / max_value;
            //System.out.println("y_pos: "+y_pos);
            y_pos = y_pos * ((float)height);
            final_y_pos = ((float)height) - y_pos;
            
            if (window == 0){
                p.lineTo(0, final_y_pos);
                
            }else{
                p.lineTo(line_offset*((float)window), final_y_pos);
            }
            p.lineTo(line_offset*((float)window+1), final_y_pos);
        }
        
        p.lineTo(width,height);
        p.closePath();
        g2d.fill(p);
        g2d.dispose();
        
        
        //Draw the red line
        if (mouse_x > -1){
            
            g2.setFont(new Font("Diago", Font.PLAIN, 12)); 
            //int tick_x = (int)((float)(mouse_x-MAX_NAME_LENGHT) / proportion);
            g2.setColor(Color.RED);
            g2.drawLine(mouse_x, 0, mouse_x, height);
            
            int window_mouse = (int)(((float)mouse_x) / line_offset + 1f);
            if (window_mouse > (max_windows))
                window_mouse = max_windows;
            
            g2.setColor(Color.BLACK);
            //String value1 = "win: "+Integer.toString(window_mouse);
            
            String value1 = unit+": "+String.format("%.2f", window_values_list.get(window_mouse-1));
            String value2 = "win: "+window_mouse;
            int max_string_lenght = Math.max(value1.length(), value2.length()) * 7;
            
            if (mouse_x >= (width - max_string_lenght)) {
                g2.drawString(value1, mouse_x - max_string_lenght, 15);
                g2.drawString(value2, mouse_x - max_string_lenght, 30);
            } else { 
                g2.drawString(value1, mouse_x + 5, 15);
                g2.drawString(value2, mouse_x + 5, 30);
            }
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Open Plot
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mainTilePanel.openTileDetailsFrame();
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
        if (mouse_x > -1){
            mouse_x = -1;
            this.repaint();
        }
    }
    
}
