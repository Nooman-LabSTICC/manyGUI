/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import energyMemory.energyMemGUI.TileEnergyPlot.TileEnergyPlotType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import energyMemory.monitoredData.EnergyInfo;

/**
 *
 * @author ruaro
 */
public class TileEnergyDetailsFrame extends JFrame{
    
        private int tileAddr;
        private ControlPanel controlPanel;
        private EnergyInfo energyInfo;
    
    public TileEnergyDetailsFrame(int tileAddr, EnergyInfo energyInfo, ControlPanel controlPanel){
        this.tileAddr = tileAddr;
        this.energyInfo = energyInfo;
        this.controlPanel = controlPanel;
        initComponents();
        buidGrid();
    }
    
    private void initComponents(){
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(600, 600));
        this.setLocationRelativeTo(null);
        this.setPreferredSize(new Dimension(600, 600));
        this.setTitle("Energy statistics for Tile "+(tileAddr >> 8)+"x"+(tileAddr & 0xFF));
        this.setVisible(true);
    }
    
    private void buidGrid(){
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        this.setLayout(gbl);
        
        //Set components to become as width the Jframe
        cons.weightx = this.getSize().width;
        
        //Set components to fill all cell space
        cons.fill = GridBagConstraints.BOTH;
        
        
        
        
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 2;
        this.add(new LegendPlot(), cons);
        
        cons.gridwidth = 1;

        cons.gridx = 0;
        cons.gridy = 1;
        this.add(new TileEnergyPlot(tileAddr, controlPanel, energyInfo, false, TileEnergyPlotType.TOTAL_ENERGY), cons);
        
        //cons.ipady = 0; 
        
        cons.gridx = 1;
        cons.gridy = 1;
        cons.weighty = this.getSize().height;
        this.add(new TileEnergyPlot(tileAddr, controlPanel, energyInfo, true, TileEnergyPlotType.TOTAL_ENERGY), cons);

        
        cons.gridx = 0;
        cons.gridy = 2;
        cons.weighty = this.getSize().height;
        this.add(new TileEnergyPlot(tileAddr, controlPanel, energyInfo, false, TileEnergyPlotType.WINDOW_ENERGY), cons);
        
        
        cons.gridx = 1;
        cons.gridy = 2;
        cons.weighty = this.getSize().height;
        this.add(new TileEnergyPlot(tileAddr, controlPanel, energyInfo, true, TileEnergyPlotType.WINDOW_ENERGY), cons);
        
        this.pack();
    }

    public class LegendPlot extends JPanel{
        
        
        public LegendPlot(){
            this.setPreferredSize(new Dimension(200, 30));
            this.setMinimumSize(new Dimension(200, 30));
            this.setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            
            g2.setFont(new Font("Arial", Font.PLAIN, 12)); 
            
            float y_text_pos = 18f;
            
            //Draw the legend rectangle
            g2.setColor(Color.GRAY);
            //g2.draw(new Rectangle2D.Float(width/2-(60f/2), y_pos, 60f, 22));
            //g2.setColor(Color.BLACK);
            float square_size  = 15f;
            int x_pos = 50;
            g2.setColor(Color.GREEN);
            g2.fill(new Rectangle2D.Float(x_pos, 6f, square_size, square_size));
            x_pos += 20;
            g2.setColor(Color.BLACK);
            g2.drawString("NoC Energy", x_pos, y_text_pos);
            
            x_pos += 120;
            g2.setColor(Color.BLUE);
            g2.fill(new Rectangle2D.Float(x_pos, 6f, square_size, square_size));
            x_pos += 20;
            g2.setColor(Color.BLACK);
            g2.drawString("Memory Energy", x_pos, y_text_pos);
            
            x_pos += 120;
            g2.setColor(Color.RED);
            g2.fill(new Rectangle2D.Float(x_pos, 6f, square_size, square_size));
            x_pos += 20;
            g2.setColor(Color.BLACK);
            g2.drawString("CPU Energy", x_pos, y_text_pos);
        }
    }
}
