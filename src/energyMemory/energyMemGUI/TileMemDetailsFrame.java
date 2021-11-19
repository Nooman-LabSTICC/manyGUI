/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import energyMemory.energyMemGUI.TileMemPlotPanel.TileMemPlotType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import energyMemory.monitoredData.EnergyInfo;

/**
 *
 * @author ruaro
 */
public class TileMemDetailsFrame extends JFrame{
    
    
    private ControlPanel controlPanel;
    private EnergyInfo energyInfo;
    private int tileAddr;
    
    
    public TileMemDetailsFrame(int tileAddr, EnergyInfo energyInfo, ControlPanel controlPanel) {
        this.tileAddr = tileAddr;
        this.energyInfo = energyInfo;
        this.controlPanel = controlPanel;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600, 600));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Memory statistics for Tile "+(tileAddr >> 8)+"x"+(tileAddr & 0xFF));
        buildLayout();
        
    }
    
    private JScrollPane buildUpperPanel(){
        
        JScrollPane jp = new JScrollPane(new TileMemDetailsPanel(tileAddr, energyInfo.getEnergyMemory(), controlPanel), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jp.getHorizontalScrollBar().setUnitIncrement(300);
        
        jp.setMinimumSize(new Dimension(100, 380));
        jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));
        jp.setPreferredSize(new Dimension(100, 380));
        
        return jp;
        
        
/*      String title = "Scheduling Graph " + PE;
        title = "Scheduling Graph " + (PE >> 8) + "x" + (PE & 0xFF);

        f.setTitle(title);
        URL url = this.getClass().getResource("/icon/scheduling_icon.png");
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        */
    }
    
    private JPanel buildLowerPanel(){
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        JPanel mainPanel = new JPanel(gbl);
        mainPanel.setBackground(Color.white);
        
        cons.fill = GridBagConstraints.BOTH;
        
        cons.weightx = this.getSize().width;
        cons.weighty = 200;
        
        cons.insets = new Insets(3,3,3,3); //Space between cells
        
        //L1-D miss rate
        cons.gridx = 0; cons.gridy = 0;
        mainPanel.add(new TileMemPlotPanel(tileAddr, TileMemPlotType.PLOT_L1_D_MISS_RATE, energyInfo.getEnergyMemory(), controlPanel), cons);
        
        //L1-D miss rate
        cons.gridx = 1; cons.gridy = 0;
        mainPanel.add(new TileMemPlotPanel(tileAddr, TileMemPlotType.PLOT_L1_I_MISS_RATE, energyInfo.getEnergyMemory(), controlPanel), cons);
        
        //L2 miss rate
        cons.gridx = 2; cons.gridy = 0;
        //cons.gridx = 1; cons.gridy = 1;
        mainPanel.add(new TileMemPlotPanel(tileAddr, TileMemPlotType.PLOT_L2_MISS_RATE, energyInfo.getEnergyMemory(), controlPanel), cons);
        
        //Decided to comment DRAM access since it was already computed in the main plot
        //DRAM access
        //cons.gridx = 1; cons.gridy = 1;
        //mainPanel.add(new TileMemPlotPanel(tileAddr, TileMemPlotType.PLOT_DRAM_ACCESS, energyInfo.getEnergyMemory(), controlPanel), cons);
        
        
        //mainPanel.setPreferredSize(new Dimension(50, 600));
        //mainPanel.setMinimumSize(new Dimension(50, 600));
        
        return mainPanel;
    }
    
    
    
    private void buildLayout(){
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        this.setLayout(gbl);
        
        //Set components to become as width the Jframe
        cons.weightx = this.getSize().width;
        
        //Set components to fill all cell space
        cons.fill = GridBagConstraints.BOTH;
        
        //cons.weighty = 200;
        //cons.ipady = 180; 
        cons.gridx = 0;
        cons.gridy = 0;
        
        this.add(buildUpperPanel(), cons);
        
        //cons.ipady = 0; 
        
        cons.gridx = 0;
        cons.gridy = 1;
        cons.weighty = this.getSize().height;
        
        this.add(buildLowerPanel(), cons);
        //this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        
        this.pack();
        
    }
    
}
