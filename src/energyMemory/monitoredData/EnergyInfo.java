/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

import energyMemory.energyMemGUI.ControlPanel;
import energyMemory.inputReader.EventInfo;
import energyMemory.inputReader.ReadMonitoredData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import energyMemory.monitoredData.MemoryEvent.MemoryEventType;

/**
 *
 * @author ruaro
 */
public class EnergyInfo {
    
    private CounterCPU energyCPU;
    private CouterMemory energyMemory;
    private CounterNoC energyNoC;
    private boolean errorReading;
    
    
    private ReadMonitoredData readMonitoredData;
    
    
    public EnergyInfo(String cpuAndMemLogPath, String nocPath) throws IOException{
        energyCPU = new CounterCPU();
        energyMemory = new CouterMemory();
        energyNoC = new CounterNoC();
        errorReading  = false;
        
        
        this.readMonitoredData = new ReadMonitoredData(cpuAndMemLogPath, nocPath);
        
    }
    
    public void reset(){
        try {
            energyCPU = new CounterCPU();
            energyMemory = new CouterMemory();
            energyNoC = new CounterNoC();
            readMonitoredData.resetReader();
            errorReading  = false;
        } catch (IOException ex) {
            Logger.getLogger(EnergyInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getNextEvent(){
        
        //If getNextData
        EventInfo ei = readMonitoredData.readNextCPUandMemValidLine();
        
        if (ei == null){
            return 0;
        }
        
        //System.out.println(ei.getName());
        
        int addr = ei.getTileAddr();
        int time = ei.getTime();

        switch(ei.getName()){
            case "L1-Daccess":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L1_D_ACCESS, addr));
                break;
            case "L1-Iaccess":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L1_I_ACCESS, addr));
                break;
            case "L1-Dmiss":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L1_D_MISS, addr));
                break;
            case "L1-Imiss":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L1_I_MISS, addr));
                break;
            case "L1-Damo access":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L1_AMO_ACCESS, addr));
                break;
            case "L2access":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L2_ACCESS, addr));
                break;
            case "L2miss":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.L2_MISS, addr));
                break;
            case "L2LOAD_MEM":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.LOAD_MEM, addr));
                break;
            case "DRAMaccess":
                energyMemory.addEnergyMemory(new MemoryEvent(time, MemoryEventType.DRAM_ACCESS, addr));
                System.out.println(addr);
                break;

            case "ALUinstr":
                energyCPU.addEnergyCore(new InstructionEvent(time, ControlPanel.InstructionClass.ALU, addr));
                break;
            case "LSUinstr":
                energyCPU.addEnergyCore(new InstructionEvent(time, ControlPanel.InstructionClass.LSU, addr));
                break;
            case "Othersinstr":
                energyCPU.addEnergyCore(new InstructionEvent(time, ControlPanel.InstructionClass.OTHERS, addr));
                break;
            default:
                if (errorReading == false){
                //System.out.println("ERROR: no valid event found at line: "+ei.getName() + " time: "+time);
                    System.out.println("ManyGui: Some errors were found reading log file. Reload the tool (Ctrl+R) and read again after the simulation end.");
                    errorReading = true;
                }
                break;
        }
        
        //Read NoC file untile time X
        NoCEvent noCEvent;
        while( (noCEvent = readMonitoredData.readNoC_UntilTime(time)) != null){
            energyNoC.addEnergyNoC(noCEvent);
        }
            
        return time;
        
    }
    
    //Ok, this is not Object oriented but I don't have time now to implement all methods
    //TODO latter
    public CouterMemory getEnergyMemory(){
        return this.energyMemory;
    }
    
    
    public float computeTotalEnery(){
        float total_energy = 0f;
        
        total_energy += energyCPU.getTotalEnergy();
        total_energy += energyMemory.getTotalEnergy();
        total_energy += energyNoC.getTotalEnergy();
        
        return total_energy;
    }
    
    public float computeTotalEnergyWindow(int start_time, int end_time){
        float total_energy = 0f;
        
        total_energy += energyCPU.getWindowEnergy(start_time, end_time);
        total_energy += energyMemory.getWindowEnergy(start_time, end_time);
        total_energy += energyNoC.getWindowEnergy(start_time, end_time);
        
        return total_energy;
    }
    
    public float computeTotalNoCEnergyWindow(int start_time, int end_time){
        return energyNoC.getWindowEnergy(start_time, end_time);
    }
    
    public float computeTotalCPUEnergyWindow(int start_time, int end_time){
        return energyCPU.getWindowEnergy(start_time, end_time);
    }
    
    public float computeTotalMemoryEnergyWindow(int start_time, int end_time){
        return energyMemory.getWindowEnergy(start_time, end_time);
    }
    
    public float computeTotalTileEnergy(int tileAddr){
        float total_energy = 0f;
        
        total_energy += energyCPU.getTileTotalEnergy(tileAddr);
        total_energy += energyMemory.getTileTotalEnergy(tileAddr);
        total_energy += energyNoC.getTileTotalEnergy(tileAddr);
        
        return total_energy;
        
    }
    
    public float computeTileWindowCPUEnergy(int tileAddr, int start_time, int end_time){
        return energyCPU.getTileWindowEnergy(tileAddr, start_time, end_time);
    }
    
    public float computeTileWindowNoCEnergy(int tileAddr, int start_time, int end_time){
        return energyNoC.getTileWindowEnergy(tileAddr, start_time, end_time);
    }
    
    public float computeTileWindowMemoryEnergy(int tileAddr, int start_time, int end_time){
        return energyMemory.getTileWindowEnergy(tileAddr, start_time, end_time);
    }
    
    public float computeTotalTileWindowEnergy(int tileAddr, int start_time, int end_time){
        float total_energy = 0f;
        
        total_energy += energyCPU.getTileWindowEnergy(tileAddr, start_time, end_time);
        //System.out.println("Energy CPU: "+energyCPU.getTileWindowEnergy(tileAddr, start_time, end_time));
        
        total_energy += energyMemory.getTileWindowEnergy(tileAddr, start_time, end_time);
        //System.out.println("Energy Memo: "+energyMemory.getTileWindowEnergy(tileAddr, start_time, end_time));
        
        total_energy += energyNoC.getTileWindowEnergy(tileAddr, start_time, end_time);
        //System.out.println("Energy NoC: "+energyNoC.getTileWindowEnergy(tileAddr, start_time, end_time));
        
        return total_energy;
    }
    
    public float computeTotalCPUEnergy(){
        return energyCPU.getTotalEnergy();
    }
    
    public float computeTileTotalCPUEnergy(int tileAddr){
        return energyCPU.getTileTotalEnergy(tileAddr);
    }
    
    public float computeTotalMemoryEnergy(){
        return energyMemory.getTotalEnergy();
    }
    
    public float computeTileTotalMemoryEnergy(int tileAddr){
        return energyMemory.getTileTotalEnergy(tileAddr);
    }
    
    public float computeTotalNoCEnergy(){
        return energyNoC.getTotalEnergy();
    }
    
    public float computeTileTotalNoCEnergy(int tileAddr){
        return energyNoC.getTileTotalEnergy(tileAddr);
    }
    
}
