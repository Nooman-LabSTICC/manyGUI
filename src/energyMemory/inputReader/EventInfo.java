/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.inputReader;

/**
 *
 * @author ruaro
 */
public class EventInfo {
    
    private String name;
    private int time;
    private int tileAddr;

    public EventInfo(String name, int time, int tileAddr) {
        this.name = name;
        this.time = time;
        this.tileAddr = tileAddr;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getTileAddr() {
        return tileAddr;
    }
    
    
    
}
