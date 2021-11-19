/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.energyMemGUI;

/**
 *
 * @author ruaro
 */
public class ControlPanel {
    
    //********************** PUBLIC DEFINES **************************
    //------------------------- Energy for 32 nm -----------------------------------------
    //Computation energy - instructions //Paper: The Cost of Application-Class Processing: Energy and Performance Analysis of a Linux-Ready 1.7-GHz 64-Bit RISC-V Core in 22-nm FDSOI Technology (table IV)
    //NOTE: These values were extrapolated to 32nm (+10% in energy consumption) according to Paper: Near-Threshold Computing: Reclaiming Moore’s Law Through Energy Efficient Integrated Circuits
    public static final float ENERGY_NJ_PER_INTRUCTION_ALU    = 0.023738f; //23.738 pJ
    public static final float ENERGY_NJ_PER_INTRUCTION_LSU    = 0.027225f; //27.225 pJ
    public static final float ENERGY_NJ_PER_INTRUCTION_OTHER  = 0.05698f; // 56.98 pJ
    
    //Memory energy: //Paper: Power and Energy Characterization of an Open Source 25-Core Manycore Processor (Table VII)
    public static final float ENERGY_NJ_PER_L1_HIT            = 0.28646f;
    public static final float ENERGY_NJ_PER_L1_MISS_L2_HIT    = 1.54f;
    public static final float ENERGY_NJ_PER_L1_MISS_L2_MISS    = 308.7f;
    
    //Memory access latency
    public static final float L1_MAX_ACCESS_RATE_CYCLES    = 0.333333333f; //3 cycles of latency
    public static final float L2_MAX_ACCESS_RATE_CYCLES    = 0.029411765f; //L1 miss L1 hit = 34 cycles of latency
    public static final float DRAM_MAX_ACCESS_RATE_CYCLES  = 0.002358491f; //L1 Miss, Local L2 Miss = 424 cycles of latency
    
    //Flit energy 1hop: //Paper: Power and Energy Characterization of an Open Source 25-Core Manycore Processor (2nd paragraph of page 8)
    public static float ENERGY_NJ_PER_FLIT               = 0.01116f; //11.16 pJ

    
    public enum TilePlotType{
        PLOT_ENERGY,
        PLOT_MEMORY
    }
    
    public enum InstructionClass {
      ALU,
      LSU,
      OTHERS
    }
    //********************** PUBLIC DEFINES **************************
    
    private boolean update;
    private int freq_MHz;
    private float voltagem_V;
    private int windowSize_Kcyles;
    private float maxEnergyTile_nJ;
    private float maxMemoryNumber;
    private int simultime_cycles;
    private boolean normalize_window;
    
    private String debugPath;
    
    private int min_window_time;
    private int max_window_time;
    private int num_windows;
    
    private int XDIM;
    private int YDIM;
    
    private boolean tab_energy_enabled;
    private boolean tab_memory_enabled;
    

    public ControlPanel(int XDIM, int YDIM, String debugPath) {
        this.XDIM = XDIM;
        this.YDIM = YDIM;
        this.debugPath = debugPath;
        update = false;
        freq_MHz = 500;
        voltagem_V = 1.0f;
        maxEnergyTile_nJ = 0;
        maxMemoryNumber = 0;
        simultime_cycles = 0;
        tab_energy_enabled = false;
        tab_memory_enabled = false;
        normalize_window = true;
        windowSize_Kcyles = 1;
        
        resetWindow();
    }
    
    public void recalculateWindow(int windowSize_Kcyles){
        this.windowSize_Kcyles = windowSize_Kcyles;
        resetWindow();
        int window_size_cycles = windowSize_Kcyles * 1000;
        num_windows = simultime_cycles/window_size_cycles;
        min_window_time = num_windows*window_size_cycles;
        max_window_time = window_size_cycles + min_window_time;
    }
    
    public void resetWindow(){
        num_windows = 0;
        min_window_time = 0;
        max_window_time = (windowSize_Kcyles*1000) + min_window_time;
    }
    
    
    public void advanceWindow(){
        num_windows++;
        min_window_time = max_window_time;
        max_window_time = (windowSize_Kcyles*1000) + min_window_time;
    }
    
    public boolean isNormalize_window() {
        return normalize_window;
    }

    public void setNormalize_window(boolean normalize_window) {
        this.normalize_window = normalize_window;
    }

    public String getDebugPath() {
        return debugPath;
    }
    
    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getFreq_MHz() {
        return freq_MHz;
    }

    public void setFreq_MHz(int freq_MHz) {
        this.freq_MHz = freq_MHz;
    }

    public float getVoltagem_V() {
        return voltagem_V;
    }

    public void setVoltagem_V(float voltagem_V) {
        this.voltagem_V = voltagem_V;
    }

    public int getWindowSize_Kcyles() {
        return windowSize_Kcyles;
    }

    public float getMaxValueEnergyTile_nJ() {
        return maxEnergyTile_nJ;
    }

    public void setMaxValueEnergyTile_nJ(float maxEnergyTile_nJ) {
        //nJ for Energy and # access for memory
        this.maxEnergyTile_nJ = maxEnergyTile_nJ;
    }
    
     public float getMaxMemoryNumber() {
        return maxMemoryNumber;
    }

    public void setMaxMemoryNumber(float maxAccessNumberTile) {
        this.maxMemoryNumber = maxAccessNumberTile;
    }
    
    public int getSimultime_cycles() {
        return simultime_cycles;
    }

    public void setSimultime_cycles(int simultime_cycles) {
        this.simultime_cycles = simultime_cycles;
    }

    public int getMin_window_time() {
        return min_window_time;
    }

    public int getMax_window_time() {
        return max_window_time;
    }

    public int getNum_windows() {
        return num_windows;
    }

    public int getXDIM() {
        return XDIM;
    }

    public int getYDIM() {
        return YDIM;
    }

    public boolean isTab_energy_enabled() {
        return tab_energy_enabled;
    }

    public void setTab_energy_enabled() {
        this.tab_energy_enabled = true;
        this.tab_memory_enabled = false;
    }

    public boolean isTab_memory_enabled() {
        return tab_memory_enabled;
    }

    public void setTab_memory_enabled() {
        this.tab_memory_enabled = true;
        this.tab_energy_enabled = false;
    }

}
