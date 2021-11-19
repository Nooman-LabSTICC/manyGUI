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
public class NoCEvent extends MonitoredEvent{
    
    private final int size;

    public NoCEvent(int time, int size, int addr) {
        super(time, addr);
        this.size = size;
    }

    public int getSize() {
        return size;
    }
    
}
