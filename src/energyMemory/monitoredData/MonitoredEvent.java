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
public abstract class MonitoredEvent{
    
    private int time;
    private int addr;

    public MonitoredEvent(int time, int addr) {
        this.time = time;
        this.addr = addr;
    }

    public int getTime() {
        return time;
    }

    public int getAddr() {
        return addr;
    }
    
    
}
