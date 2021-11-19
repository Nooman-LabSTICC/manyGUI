/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.monitoredData;

import energyMemory.energyMemGUI.ControlPanel.InstructionClass;

/**
 *
 * @author ruaro
 */
public class InstructionEvent extends MonitoredEvent{

    private InstructionClass intrClass;

    public InstructionEvent(int time, InstructionClass intrClass, int addr) {
        super(time, addr);
        this.intrClass = intrClass;
    }
    
    public InstructionClass getIntrClass() {
        return intrClass;
    }
    
}
