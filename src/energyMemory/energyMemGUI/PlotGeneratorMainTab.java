/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

import energyMemory.energyMemGUI.PlotGeneratorPanel.EnergyStatisticType;
import energyMemory.energyMemGUI.PlotGeneratorPanel.MemoryStatisticType;
import energyMemory.energyMemGUI.PlotGeneratorPanel.PlotGeneratorType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import energyMemory.monitoredData.EnergyInfo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author ruaro
 */
public class PlotGeneratorMainTab extends JPanel{
    
    private ControlPanel controlPanel;
    private EnergyInfo energyInfo;
    private JTextArea showTextArea;
    
    public PlotGeneratorMainTab(ControlPanel controlPanel, EnergyInfo energyInfo){
        this.controlPanel = controlPanel;
        this.energyInfo = energyInfo;
        initComponents();
    }
    
    private void initComponents(){
        
        this.setSize(new Dimension(500, 600));

        this.removeAll();
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        
        this.setLayout(gbl);
        
        //Set components to become as width the Jframe
        cons.weightx = 200;
        cons.weighty = 60;
        
        //Set components to fill all cell space
        cons.fill = GridBagConstraints.BOTH;
        
        //cons.weighty = 200;
        //cons.ipady = 180; 
        cons.gridx = 0;
        cons.gridy = 0;
        
        this.add(new PlotGeneratorPanel(this, PlotGeneratorType.ENERGY, controlPanel.getXDIM(), controlPanel.getYDIM()), cons);
        
        //cons.ipady = 0; 
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weighty = this.getSize().height;
        
        this.add(new PlotGeneratorPanel(this, PlotGeneratorType.MEMORY, controlPanel.getXDIM(), controlPanel.getYDIM()), cons);
        
        
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 2;
        //cons.weighty = 300;//this.getSize().height;
        
                
        
        
        
        showTextArea = new JTextArea();
        showTextArea.setBackground(Color.WHITE);
        //showTextArea.setPreferredSize(new Dimension(400, this.getHeight()-300));
        
        
        JScrollPane jp = new JScrollPane(showTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jp.getHorizontalScrollBar().setUnitIncrement(300);
        
        jp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Output Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11))); // NOI18N
        
        //jp.setMinimumSize(new Dimension(100, 380));
        //jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));
        jp.setPreferredSize(new Dimension(400, this.getHeight()-300));
        
        this.add(jp, cons);
        
        this.revalidate();
        
    }
    
    
    public void generateEnergy(int tileAddr, EnergyStatisticType energyStatistic, boolean normalized, boolean perWindow, boolean newWindow){
        //ArrayList<Float> x_series = new ArrayList<>();
        ArrayList<Float> x_series_NoC = new ArrayList<>();
        ArrayList<Float> x_series_Mem = new ArrayList<>();
        ArrayList<Float> x_series_CPU = new ArrayList<>();
        
        float max_y = 0f;
        
        int window_size = controlPanel.getWindowSize_Kcyles()*1000;
        float sumNoC = 0f;
        float sumCPU = 0f;
        float sumMem = 0f;
        float noc_value = 0f;
        float cpu_value = 0f;
        float mem_value = 0f;
        float value;
        
        for (int w = 0; w < (controlPanel.getNum_windows()*window_size); w+=window_size) {
            switch (energyStatistic) {
                case TOTAL_ENERGY:
                    if (tileAddr == -1){
                        noc_value = (float)energyInfo.computeTotalNoCEnergyWindow(w, w+window_size);
                        cpu_value = (float)energyInfo.computeTotalCPUEnergyWindow(w, w+window_size);
                        mem_value = (float)energyInfo.computeTotalMemoryEnergyWindow(w, w+window_size);
                    } else {
                        noc_value = (float)energyInfo.computeTileWindowNoCEnergy(tileAddr, w, w+window_size);
                        cpu_value = (float)energyInfo.computeTileWindowCPUEnergy(tileAddr, w, w+window_size);
                        mem_value = (float)energyInfo.computeTileWindowMemoryEnergy(tileAddr, w, w+window_size);
                    }
                    break;
            }
            
            value = noc_value + cpu_value + mem_value;
                    
            if (perWindow){
                x_series_NoC.add(noc_value);
                x_series_CPU.add(cpu_value);
                x_series_Mem.add(mem_value);
                
                if (max_y < value) max_y = value;
            }  else {
                sumNoC += noc_value;
                sumCPU += cpu_value;
                sumMem += mem_value;
                
                x_series_NoC.add(sumNoC);
                x_series_CPU.add(sumCPU);
                x_series_Mem.add(sumMem);
                
                max_y = sumNoC+sumCPU+sumMem;
            }
        }
        
        /*System.out.println(x_series_NoC);
        System.out.println(x_series_Mem);
        System.out.println(x_series_CPU);*/
        String norm_cmd = "Abs";
        //Normalizes
        if (normalized){
            norm_cmd = "Norm";
            for (int i = 0; i < x_series_NoC.size(); i++) {
                noc_value = x_series_NoC.get(i);
                cpu_value = x_series_CPU.get(i);
                mem_value = x_series_Mem.get(i);
                
                max_y = noc_value + cpu_value + mem_value;
                
                if (max_y > 0){
                    value = noc_value / max_y;
                    x_series_NoC.set(i, value);

                    value = cpu_value / max_y;
                    x_series_CPU.set(i, value);

                    value = mem_value / max_y;
                    x_series_Mem.set(i, value);
                }
            }
        }
        
        //These are the final values
        //System.out.println(x_series_NoC);
        //System.out.println(x_series_Mem);
        //System.out.println(x_series_CPU);
        //System.out.println(convertArrayToStringListPython(x_series_NoC));
        
        
        
        String showPlot = "False";
        if (newWindow){
            showPlot = "True";
        }
        
        if (!controlPanel.isNormalize_window()){
            if (controlPanel.isTab_energy_enabled()){
                max_y = controlPanel.getMaxValueEnergyTile_nJ();
            } else {
                max_y = controlPanel.getMaxMemoryNumber();
            }
        }
        
        String plotName = "";
        if (tileAddr != -1){
            plotName = "Tile_"+(tileAddr >> 8)+"x"+(tileAddr & 0xFF)+"_Energy";
        } else {
            plotName = "System_Energy";
        }
        
        
        String command = "python "+controlPanel.getDebugPath()+"/../../utils/matplotlib/bar_energy.py "+
                convertArrayToStringListPython(x_series_NoC)+" "+
                convertArrayToStringListPython(x_series_Mem)+" "+
                convertArrayToStringListPython(x_series_CPU)+" "+
                norm_cmd+" "+
                plotName+" "+
                controlPanel.getWindowSize_Kcyles()+
                " "+max_y+" "+
                showPlot;
        
        
        runCommand(command);
    }

    public void generateMemory(int tileAddr, MemoryStatisticType memStatistic, boolean normalized, boolean perWindow, boolean newWindow){
        
        ArrayList<Float> x_series = new ArrayList<>();
        float max_y = 0f;
        
        int window_size = controlPanel.getWindowSize_Kcyles()*1000;
        float sum = 0f;
        float value = 0f;
        String plotName = memStatistic.text;
        plotName = plotName.replaceAll(" ", "_");//Put underline in the spaces to avoid error in Matplotlib command line arguments
        
        for (int w = 0; w < (controlPanel.getNum_windows()*window_size); w+=window_size) {
            switch (memStatistic) {
                case L1_D_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L1_D_access(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L1_D_access(tileAddr, w, w+window_size);
                    }
                    break;
                case L1_D_MISS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L1_D_miss(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L1_D_miss(tileAddr, w, w+window_size);
                    }
                    break;
                case L1_D_MISS_RATE:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L1_D_miss_rate(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().computeTileWindow_L1_D_miss_rate(tileAddr, w, w+window_size);
                    }
                    perWindow = true; //Avoid erros in matplotlib since non per window always increment
                    break;
                case L1_I_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L1_I_access(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L1_I_access(tileAddr, w, w+window_size);
                    }
                    break;
                case L1_I_MISS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L1_I_miss(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L1_I_miss(tileAddr, w, w+window_size);
                    }
                    break;
                case L1_I_MISS_RATE:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_LI_I_miss_rate(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().computeTileWindow_L1_I_miss_rate(tileAddr, w, w+window_size);
                    }
                    perWindow = true; //Avoid erros in matplotlib since non per window always increment
                    break;
                    
                case L2_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L2_access(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L2_access(tileAddr, w, w+window_size);
                    }
                    break;
                case L2_MISS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L2_miss(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_L2_miss(tileAddr, w, w+window_size);
                    }
                    break;
                case L2_MISS_RATE:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_L2_miss_rate(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().computeTileWindow_L2_miss_rate(tileAddr, w, w+window_size);
                    }
                    perWindow = true; //Avoid erros in matplotlib since non per window always increment
                    break;
                case AMO_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_AMO_access(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_AMO_access(tileAddr, w, w+window_size);
                    }
                    break;
                case LOAD_MEM_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_LOAD_MEM(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_LOAD_MEM(tileAddr, w, w+window_size);
                    }
                    break;
                case DRAM_ACCESS:
                    if (tileAddr == -1){
                        value = (float)energyInfo.getEnergyMemory().getSystemWindow_DRAM_acess(w, w+window_size);
                    } else {
                        value = (float)energyInfo.getEnergyMemory().getTileWindow_DRAM_access(tileAddr, w, w+window_size);
                    }
                    break;
            }
            
            if (perWindow){
                x_series.add(value);
                if (max_y < value) max_y = value;
            }  else {
                sum += value;
                x_series.add(sum);
                max_y = sum;
            }
        }
        
        float rate_average = 0f;
        if (tileAddr != -1){
            switch (memStatistic) {
                case L1_D_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTile_L1_D_miss_rate(tileAddr);
                    break;
                case L1_I_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTile_L1_I_miss_rate(tileAddr);
                    break;
                case L2_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTile_L2_miss_rate(tileAddr);
                    break;
            }
        } else {
            switch (memStatistic) {
                case L1_D_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTotal_L1_D_miss_rate();
                    break;
                case L1_I_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTotal_L1_I_miss_rate();
                    break;
                case L2_MISS_RATE:
                    rate_average = energyInfo.getEnergyMemory().computeTotal_L2_miss_rate();
                    break;
            }
        }
        
        
        
        String showPlot = "False";
        if (newWindow){
            showPlot = "True";
        }
        
        if (!controlPanel.isNormalize_window()){
            if (controlPanel.isTab_energy_enabled()){
                max_y = controlPanel.getMaxValueEnergyTile_nJ();
            } else {
                max_y = controlPanel.getMaxMemoryNumber();
            }
        }
        
        if (tileAddr != -1){
            plotName = "Tile_"+(tileAddr >> 8)+"x"+(tileAddr & 0xFF)+"_"+plotName;
        } else {
            plotName = "System_"+plotName;
        }
        
        String command = "python "+controlPanel.getDebugPath()+"/../../utils/matplotlib/line_mem.py "+
                convertArrayToStringListPython(x_series)+" "+
                "Abs"+" "+
                plotName+" "+
                controlPanel.getWindowSize_Kcyles()+
                " "+max_y+" "+
                showPlot+" "+
                rate_average;
        
        runCommand(command);
        
        
    }
    
    
        
    
    private void runCommand(String command){
        //System.out.println("Command: "+command);
        
        if (controlPanel.getSimultime_cycles() == 0){
            JOptionPane.showMessageDialog(this, "No data to show. Please first simulate the system or click in <Update>", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String message = "Executed Command: "+command;
        
        message += "\n\nOutput Mathplotlib Log:\n\n";
        
        try {
            
            String line = "";
           
            Process process = Runtime.getRuntime().exec(command);
            
            if (process.waitFor() != 0) {

                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
               
                String error_log = "";
                while ((line = errorReader.readLine()) != null){
                    error_log += line+"\n";
                }
                
                JOptionPane.showMessageDialog(this, "Error during plot generation - Python error:\n\n"+error_log, "Error", JOptionPane.ERROR_MESSAGE);
                errorReader.close();
                
            } else {
                
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

                while ((line = stdInput.readLine()) != null) {
                    message += line+"\n";
                }
                stdInput.close();
            }
            
            process.destroy();

            //Update the text area
            showTextArea.setText(message);
            /*if (process.exitValue() != 0) {
                System.out.println("Abnormal process termination222");
            }*/

        } catch (Exception e) {
        }
    }
    
    
    private String convertArrayToStringListPython(ArrayList<Float> inArray){
        String outRet= "[";
        for (int i = 0; i < inArray.size(); i++) {
            Float value = inArray.get(i);
            if (i+1 == inArray.size())
                outRet+=Float.toString(value)+"]";
            else
                outRet+=Float.toString(value)+",";
        }
        return outRet;
    }
    
    
}
