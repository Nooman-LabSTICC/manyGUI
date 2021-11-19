/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

import energyMemory.energyMemGUI.ControlPanel;
import energyMemory.energyMemGUI.ControlPanel.InstructionClass;
import static energyMemory.energyMemGUI.ControlPanel.InstructionClass.ALU;
import static energyMemory.energyMemGUI.ControlPanel.InstructionClass.LSU;
import static energyMemory.energyMemGUI.ControlPanel.InstructionClass.OTHERS;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ruaro
 */
public class CounterCPU implements EnergyComponentInterface{
    
    private HashMap<Integer, ArrayList<InstructionEvent>> total_CPU_events;
    
    //CPU counters
    private CPUStatistics cPUStatistics;
    
    public CounterCPU(){
        
        total_CPU_events = new HashMap<>();
        //CPU counters
        cPUStatistics = new CPUStatistics();
    }
    
    public void addEnergyCore(InstructionEvent cpuEvent){
        
        ArrayList<InstructionEvent> listInstructions = total_CPU_events.get(cpuEvent.getAddr());
        
        if (listInstructions == null){
            listInstructions = new ArrayList<>();
            total_CPU_events.put(cpuEvent.getAddr(), listInstructions);
        }
        
        listInstructions.add(cpuEvent);
        
        cPUStatistics.updateStatistics(cpuEvent.getIntrClass());
    }
    
    private CPUStatistics getTileStatistics(int tileAddr){
        
        CPUStatistics cPUStatistics = new CPUStatistics();
        //For each Tile
        ArrayList<InstructionEvent> tileEvents = total_CPU_events.get(tileAddr); 
        
        if (tileEvents == null){
            return null;
        }
            
        //For Each List 
        for (InstructionEvent instructionEvent : tileEvents) {

           cPUStatistics.updateStatistics(instructionEvent.getIntrClass());
        }
        
        return cPUStatistics;
    }
    
    private CPUStatistics getTileWindowStatistics(int tileAddr, int start_time, int end_time){
        CPUStatistics cPUStatistics = new CPUStatistics();
            
        //For each Tile
        ArrayList<InstructionEvent> tileEvents = total_CPU_events.get(tileAddr); 
        
        if (tileEvents == null){
            return null;
        }
        
        //For Each List 
        for (InstructionEvent instructionEvent : tileEvents) {

            //For each event within the specified window
            if (instructionEvent.getTime() >= start_time && instructionEvent.getTime() < end_time){
                
                cPUStatistics.updateStatistics(instructionEvent.getIntrClass());
            }
        }
        
        return cPUStatistics;
    }

    @Override
    public float getWindowEnergy(int start_time, int end_time) {

        float total_energy = 0f;
            
        for (Integer tileAddr : total_CPU_events.keySet()) {
            total_energy = total_energy + getTileWindowEnergy(tileAddr, start_time, end_time);
        }
        
        return total_energy;
    }

    @Override
    public float getTotalEnergy() {
        return cPUStatistics.computeEnergy();
    }

    @Override
    public float getTileTotalEnergy(int tileAddr) {
        
        CPUStatistics cpuS = getTileStatistics(tileAddr);
        
        if (cpuS == null){
            return 0f;
        }
        
        return cpuS.computeEnergy();
    }
    

    @Override
    public float getTileWindowEnergy(int tileAddr, int start_time, int end_time) {
        
        CPUStatistics cpuS = getTileWindowStatistics(tileAddr, start_time, end_time);
            
        if (cpuS == null){
            return 0f;
        }
        
        return cpuS.computeEnergy();
    }
    
    
    
    private class CPUStatistics{
        private float sum_ALU;
        private float sum_LSU;
        private float sum_OTHERS;
        
        private CPUStatistics(){
            sum_ALU = 0f;
            sum_LSU = 0f;
            sum_OTHERS = 0f;
        }
        
        private void updateStatistics(InstructionClass ic){
            switch(ic){
            case ALU:
                sum_ALU++;
                break;
            case LSU:
                sum_LSU++;
                break;
            case OTHERS:
                sum_OTHERS++;
                break;
            }
        }
        
        private float computeEnergy(){
            float total_energy = sum_ALU * ControlPanel.ENERGY_NJ_PER_INTRUCTION_ALU;
            total_energy += sum_LSU * ControlPanel.ENERGY_NJ_PER_INTRUCTION_LSU;
            total_energy += sum_OTHERS * ControlPanel.ENERGY_NJ_PER_INTRUCTION_OTHER;

            return total_energy;
        }
    }

}
