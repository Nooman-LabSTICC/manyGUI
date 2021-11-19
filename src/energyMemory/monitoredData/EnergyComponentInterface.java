/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

/**
 *
 * @author ruaro
 */
public interface EnergyComponentInterface {
    
    public float getWindowEnergy(int start_time, int end_time);
    
    public float getTotalEnergy();
    
    public float getTileTotalEnergy(int tileAddr);
    
    public float getTileWindowEnergy(int tileAddr, int start_time, int end_time);
}
