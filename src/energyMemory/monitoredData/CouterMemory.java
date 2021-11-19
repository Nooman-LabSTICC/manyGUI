/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

import energyMemory.energyMemGUI.ControlPanel;
import java.util.ArrayList;
import java.util.HashMap;
import energyMemory.monitoredData.MemoryEvent.MemoryEventType;

/**
 *
 * @author ruaro
 */
public class CouterMemory implements EnergyComponentInterface{
    
    private HashMap<Integer, ArrayList<MemoryEvent>> total_mem_events;
    
    //Memory counters
    private MemoryStatistics memoryTotalStatistics;
    
    public CouterMemory(){
        total_mem_events = new HashMap<>();
        
        //Global values, used to improve performance
        memoryTotalStatistics = new MemoryStatistics();
        
    }
    
    public void addEnergyMemory(MemoryEvent memoryEvent){
        
        ArrayList<MemoryEvent> listMemEvent = total_mem_events.get(memoryEvent.getAddr());
        
        if (listMemEvent == null){
            listMemEvent = new ArrayList<>();
            total_mem_events.put(memoryEvent.getAddr(), listMemEvent);
        }
        
        listMemEvent.add(memoryEvent);
        
        memoryTotalStatistics.updateStatistics(memoryEvent.getEvent());
        
    }
    
    public ArrayList<Integer> computeTileEventTimeline(int tileAddr, MemoryEventType event){
        
        ArrayList<Integer> l1_d_miss_timeline = new ArrayList<>();

        ArrayList<MemoryEvent> tileEvents = total_mem_events.get(tileAddr); 
        
        if (tileEvents != null){
            for (MemoryEvent tileEvent : tileEvents) {
                if (tileEvent.getEvent() == event){
                    l1_d_miss_timeline.add(tileEvent.getTime());
                }
            }
        } 
        
        return l1_d_miss_timeline;
    }
    
    //**************** Total L1 access *************************
    public int getTotal_L1_D_access(){
        return memoryTotalStatistics.l1_d_access;
    }
    
    public int getTotal_L1_I_access(){
        return memoryTotalStatistics.l1_i_access;
    }
    
    public int getTotal_L1_D_miss(){
        return memoryTotalStatistics.l1_d_miss;
    }
    
    public int getTotal_L1_I_miss(){
        return memoryTotalStatistics.l1_i_miss;
    }
    
    public int getTotal_AMO_access(){
        return memoryTotalStatistics.amo_access;
    }
    
    public int getTotal_L2_access(){
        return memoryTotalStatistics.l2_access;
    }
    
    public int getTotal_L2_miss(){
        return memoryTotalStatistics.l2_miss;
    }
    
    public int getTotal_LOAD_MEM(){
        return memoryTotalStatistics.load_mem;
    }
    
    public int getTotal_DRAM_access(){
        return memoryTotalStatistics.dram_access;
    }
    
    public float computeTotal_L1_D_miss_rate(){
        return memoryTotalStatistics.compute_l1_d_miss_rate();
    }
    
    public float computeTotal_L1_I_miss_rate(){
        return memoryTotalStatistics.compute_l1_i_miss_rate();
    }
    
    public float computeTotal_L2_miss_rate(){
        return memoryTotalStatistics.compute_l2_miss_rate();
    }
       
    //**************** L1-D *************************
    public int getTile_L1_D_access(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_d_access;
    }
    
    public int getTileWindow_L1_D_access(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_d_access;
    }
    
    public int getTile_L1_D_miss(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_d_miss;
    }
    
    public int getTileWindow_L1_D_miss(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_d_miss;
    }
    
    public float computeTile_L1_D_miss_rate(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l1_d_miss_rate();
    }
    
        public float computeTileWindow_L1_D_miss_rate(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l1_d_miss_rate();
    }
    
        
        
    //**************** L1-I *************************
    public int getTile_L1_I_access(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_i_access;
    }
    
    public int getTileWindow_L1_I_access(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_i_access;
    }
    
    
    public int getTile_L1_I_miss(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_i_miss;
    }
    
    public int getTileWindow_L1_I_miss(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l1_i_miss;
    }
    
    public float computeTile_L1_I_miss_rate(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l1_i_miss_rate();
    }
    
    public float computeTileWindow_L1_I_miss_rate(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l1_i_miss_rate();
    }
    
    
    //**************** AMO *************************
    public int getTile_AMO_access(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.amo_access;
    }
    
    public int getTileWindow_AMO_access(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.amo_access;
    }
    
    
    
    //**************** L2 *************************
    public int getTile_L2_access(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_access;
    }
    
    public int getTileWindow_L2_access(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_access;
    }
    
    public int getTile_L2_miss(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_miss;
    }
    
    public int getTileWindow_L2_miss(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_miss;
    }
    
    public float computeTile_L2_miss_rate(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l2_miss_rate();
    }
    
    public float computeTileWindow_L2_miss_rate(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l2_miss_rate();
    }
    
    /*public float computeTileWindow_LOAD_MEM_access_rate(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.compute_l2_miss_rate();
    }*/
    
    
    
    //**************** L2 *************************
    public int getTile_LOAD_MEM(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.load_mem;
    }
    
    public int getTileWindow_LOAD_MEM(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.load_mem; 
    }
    
    public int getTile_DRAM_access(int tileAddr){
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_miss;//Assuming that each l2 miss of tile represents a DRAM access
    }
    
    public int getTileWindow_DRAM_access(int tileAddr, int start_time, int end_time){
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        if (memoryStatistics == null)
            return 0;
        
        return memoryStatistics.l2_miss;//Assuming that each l2 miss of tile represents a DRAM access
    }
    
    public int getSystemWindow_L1_D_access(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l1_d_access;
    }
    
    public int getSystemWindow_L1_D_miss(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l1_d_miss;
    }
    
    public float getSystemWindow_L1_D_miss_rate(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).compute_l1_d_miss_rate();
    }
    
    public int getSystemWindow_L1_I_access(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l1_i_access;
    }
    
    public int getSystemWindow_L1_I_miss(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l1_i_miss;
    }
    
    public float getSystemWindow_LI_I_miss_rate(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).compute_l1_i_miss_rate();
    }
    
    public int getSystemWindow_L2_access(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l2_access;
    }
    
    public int getSystemWindow_L2_miss(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).l2_miss;
    }
    
    public float getSystemWindow_L2_miss_rate(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).compute_l2_miss_rate();
    }
    
    public int getSystemWindow_AMO_access(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).amo_access;
    }
    
    public int getSystemWindow_LOAD_MEM(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).load_mem;
    }
    
    public int getSystemWindow_DRAM_acess(int start_time, int end_time){
        return getSystemWindowStatistics(start_time, end_time).dram_access;
    }
    
    
    
    /*********************** BASE FUNCTIONS ************************************/
    private MemoryStatistics getSystemWindowStatistics(int start_time, int end_time){
        MemoryStatistics memoryStatistics = new MemoryStatistics();
            
        //For each Tile
        for (Integer tileAddr : total_mem_events.keySet()) {
            
            ArrayList<MemoryEvent> tileEvents = total_mem_events.get(tileAddr); 
            
            if (tileEvents == null){
                continue;
            }
        
            //For Each List 
            for (MemoryEvent memoryEvent : tileEvents) {

                //For each event within the specified window
                if (memoryEvent.getTime() >= start_time && memoryEvent.getTime() < end_time){

                    memoryStatistics.updateStatistics(memoryEvent.getEvent());
                }
            }
        }
        
        return memoryStatistics;
    }
    
        private MemoryStatistics getTileStatistics(int tileAddr){
        
        MemoryStatistics memoryStatistics = new MemoryStatistics();
        
        //For each Tile
        ArrayList<MemoryEvent> tileEvents = total_mem_events.get(tileAddr); 
        
        if (tileEvents == null){
            return null;
        }
        
        //For Each List 
        for (MemoryEvent memoryEvent : tileEvents) {
            memoryStatistics.updateStatistics(memoryEvent.getEvent());
        }
        
        return memoryStatistics;
    }
    
    private MemoryStatistics getTileWindowStatistics(int tileAddr, int start_time, int end_time){
        
        MemoryStatistics memoryStatistics = new MemoryStatistics();
            
        //For each Tile
        ArrayList<MemoryEvent> tileEvents = total_mem_events.get(tileAddr); 
        
        if (tileEvents == null){
            return null;
        }
        
        //For Each List 
        for (MemoryEvent memoryEvent : tileEvents) {

            //For each event within the specified window
            if (memoryEvent.getTime() >= start_time && memoryEvent.getTime() < end_time){
                
                memoryStatistics.updateStatistics(memoryEvent.getEvent());
            }
        }
        
        return memoryStatistics;
    }
    
    
    @Override
    public float getWindowEnergy(int start_time, int end_time) {
        float total_energy = 0f;
            
        for (Integer tileAddr : total_mem_events.keySet()) {
            total_energy = total_energy + getTileWindowEnergy(tileAddr, start_time, end_time);
        }
        
        return total_energy;
    }

    @Override
    public float getTotalEnergy() {
        return memoryTotalStatistics.computeEnergy();
    }

    @Override
    public float getTileTotalEnergy(int tileAddr) {
        
        MemoryStatistics memoryStatistics = getTileStatistics(tileAddr);
        
        if (memoryStatistics == null)
            return 0f;
        
        return memoryStatistics.computeEnergy();
    }

    @Override
    public float getTileWindowEnergy(int tileAddr, int start_time, int end_time) {
        
        MemoryStatistics memoryStatistics = getTileWindowStatistics(tileAddr, start_time, end_time);
        
        if (memoryStatistics == null)
            return 0f;
        
        return memoryStatistics.computeEnergy();
    }
    
    
    private class MemoryStatistics{
        //Memory counters
        private int l1_d_access;
        private int l1_d_miss;
        private int l1_i_access;
        private int l1_i_miss;
        private int amo_access;
        private int l2_access;
        private int l2_miss;
        private int load_mem;
        private int dram_access;
        
        private MemoryStatistics(){
            l1_d_access = 0;
            l1_d_miss = 0;
            l1_i_access = 0;
            l1_i_miss = 0;
            amo_access = 0;
            l2_access = 0;
            l2_miss = 0;
            load_mem = 0;
        }
        
        private void updateStatistics(MemoryEventType memoryEventType){
                switch(memoryEventType){
                case L1_D_ACCESS:
                    l1_d_access++;
                    break;
                case L1_I_ACCESS:
                    l1_i_access++;
                    break;
                case L1_D_MISS:
                    l1_d_miss++;
                    break;
                case L1_I_MISS:
                    l1_i_miss++;
                    break;
                case L1_AMO_ACCESS:
                    amo_access++;
                    break;    
                case L2_ACCESS:
                    l2_access++;
                    break;  
                case L2_MISS:
                    l2_miss++;
                    break;
                case DRAM_ACCESS:
                    dram_access++;
                    break;
                case LOAD_MEM:
                    load_mem++;
                    break;
            }
        }
        
        private float compute_total_l1_d_access(){
            return (l1_d_access + amo_access);
        }
        
        private float compute_l1_d_miss_rate(){
            float access = l1_d_access + amo_access;
            float miss = l1_d_miss;
            
            if (access == 0f)
                return 0f;
            
            return (miss/access);
        }
        
        private float compute_l1_i_miss_rate(){
            float access = l1_i_access;
            float miss = l1_i_miss;
            
            if (access == 0f)
                return 0f;
            
            return (miss/access);
        }
        
        private float compute_total_l1_miss_rate(){
            float access = l1_i_access + l1_i_access + amo_access;
            float miss = l1_d_miss - l1_i_miss;
            
            if (access == 0f)
                return 0f;
            
            return (miss/access);
        }
        
        private float compute_l2_miss_rate(){
            float access = l2_access;
            float miss = l2_miss;
            
            if (access == 0f)
                return 0f;
            
            return (miss/access);
        }
        
        private float compute_L1_Hit(){
            int aux = l1_d_access + l1_i_access + amo_access - l1_d_miss - l1_i_miss;
            return (float) aux;
        }

        private float compute_L1_Miss_L2_Hit(){
            int aux = l2_access - l2_miss;
            return (float) aux;
        }

        private float compute_L1_Miss_L2_Miss(){
            return (float) l2_miss;//The number of miss is the number of DRAM memory access
        }
        
        
        private float computeEnergy(){
            
            float total_energy = 0f;
        
            total_energy += compute_L1_Hit() * ControlPanel.ENERGY_NJ_PER_L1_HIT;
            total_energy += compute_L1_Miss_L2_Hit() * ControlPanel.ENERGY_NJ_PER_L1_MISS_L2_HIT;
            total_energy += compute_L1_Miss_L2_Miss() * ControlPanel.ENERGY_NJ_PER_L1_MISS_L2_MISS;

            return total_energy;
        }
    }


    
}
