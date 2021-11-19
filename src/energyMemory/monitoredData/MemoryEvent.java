package energyMemory.monitoredData;

/**
 *
 * @author ruaro
 */
public class MemoryEvent extends MonitoredEvent{
    
    public enum MemoryEventType {
        L1_D_MISS,
        L1_I_MISS,
        L1_D_ACCESS,
        L1_I_ACCESS,
        L1_AMO_ACCESS,
        L2_ACCESS,
        L2_MISS,
        DRAM_ACCESS,
        LOAD_MEM
      }
    
    private MemoryEventType event;
    
    
    public MemoryEvent(int time, MemoryEventType event, int addr) {
        super(time, addr);
        this.event = event;
    }

    public MemoryEventType getEvent() {
        return event;
    }
    
}
