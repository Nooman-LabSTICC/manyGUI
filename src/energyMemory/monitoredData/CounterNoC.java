/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

import static energyMemory.energyMemGUI.ControlPanel.ENERGY_NJ_PER_FLIT;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ruaro
 */
public class CounterNoC implements EnergyComponentInterface{
    
    private HashMap<Integer, ArrayList<NoCEvent>> total_NoC_events;
    
    private float total_num_flits;
    
    public CounterNoC(){
        total_NoC_events = new HashMap<>();
        total_num_flits = 0f;
    }
    
    public void addEnergyNoC(NoCEvent noCEvent){
        
        ArrayList<NoCEvent> listNoCEvents = total_NoC_events.get(noCEvent.getAddr());
        
        if (listNoCEvents == null){
            listNoCEvents = new ArrayList<>();
            total_NoC_events.put(noCEvent.getAddr(), listNoCEvents);
        }
        
        listNoCEvents.add(noCEvent);
        
        total_num_flits += noCEvent.getSize();
    }


    @Override
    public float getWindowEnergy(int start_time, int end_time) {
        
        float total_energy = 0f;
        
        for (Integer tileAddr : total_NoC_events.keySet()) {
            total_energy = total_energy + getTileWindowEnergy(tileAddr, start_time, end_time);
        }
        
        return total_energy;
    }

    @Override
    public float getTotalEnergy() {
        return total_num_flits * ENERGY_NJ_PER_FLIT;
    }

    @Override
    public float getTileTotalEnergy(int tileAddr) {
        float flits_counter = 0;
        //For each Tile
        ArrayList<NoCEvent> tileEvents = total_NoC_events.get(tileAddr); 
        
        if (tileEvents == null){
            return 0f;
        }
        
        //For Each List 
        for (NoCEvent nocEvent : tileEvents) {
            flits_counter += nocEvent.getSize();
        }
        
        return flits_counter * ENERGY_NJ_PER_FLIT;
    }

    @Override
    public float getTileWindowEnergy(int tileAddr, int start_time, int end_time) {
            
        float flits_counter = 0;
        //For each Tile
        ArrayList<NoCEvent> tileEvents = total_NoC_events.get(tileAddr); 
        
        if (tileEvents == null){
            return 0f;
        }
        
        //For Each List 
        for (NoCEvent nocEvent : tileEvents) {

            //For each event within the specified window
            if (nocEvent.getTime() >= start_time && nocEvent.getTime() < end_time){
                
                flits_counter += nocEvent.getSize();
            }
        }
        
        return flits_counter * ENERGY_NJ_PER_FLIT;
    }
    
}
