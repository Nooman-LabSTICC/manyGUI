/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo
 */
public class MPSoCConfig {

    private TreeMap<Integer, String> servicesHash;
    private TreeMap<Integer, String> taskNameHash;
    private int[] serviceReference;
    
    private int mpsoc_x = 3;
    private int mpsoc_y = 3;
    private int cluster_x = 3;
    private int cluster_y = 3;
    private int managerPosition_x;
    private int managerPosition_y;
    private int global_position_x;
    private int global_position_y;
    private int globalManagerCluster;
    private String debugDirPath = null;
    private int flitSize;
    private int clockPeriodInNs;
    private float througphputMonWindow;
    private int channel_number;
    private int chipset_position;
    private int chipset_id;
    
    //This order cannot be changed since it is multiple of 5
    public static final int EAST1   = 0;
    public static final int WEST1   = 1;
    public static final int NORTH1  = 2;
    public static final int SOUTH1  = 3;
    public static final int LOCAL1  = 4;
    public static final int EAST2   = 5;
    public static final int WEST2   = 6;
    public static final int NORTH2  = 7;
    public static final int SOUTH2  = 8;
    public static final int LOCAL2  = 9;
    public static final int EAST3   = 10;
    public static final int WEST3   = 11;
    public static final int NORTH3  = 12;
    public static final int SOUTH3  = 13;
    public static final int LOCAL3  = 14;
    
    public static final int NPORT = 15;
    public static final int NPORT_PER_ROUTER = 5;
    
    public static final int NOC1 = 1;
    public static final int NOC2 = 2;
    public static final int NOC3 = 3;
    
    public static final int SLAVE           = 2;
    public static final int CLUSTER_MASTER  = 1;
    public static final int GLOBAL_MASTER   = 0;
    
    //****************************************************************************
    public static final int EAST_IN_NOC1    = 0;
    public static final int EAST_OUT_NOC1   = 1;
    public static final int WEAST_IN_NOC1   = 2;
    public static final int WEAST_OUT_NOC1  = 3;
    public static final int NORTH_IN_NOC1   = 4;
    public static final int NORTH_OUT_NOC1  = 5;
    public static final int SOUTH_IN_NOC1   = 6;
    public static final int SOUTH_OUT_NOC1  = 7;
    public static final int LOCAL_IN_NOC1   = 8;
    public static final int LOCAL_OUT_NOC1  = 9;
    
    public static final int EAST_IN_NOC2    = 10;
    public static final int EAST_OUT_NOC2   = 11;
    public static final int WEAST_IN_NOC2   = 12;
    public static final int WEAST_OUT_NOC2  = 13;
    public static final int NORTH_IN_NOC2   = 14;
    public static final int NORTH_OUT_NOC2  = 15;
    public static final int SOUTH_IN_NOC2   = 16;
    public static final int SOUTH_OUT_NOC2  = 17;
    public static final int LOCAL_IN_NOC2   = 18;
    public static final int LOCAL_OUT_NOC2  = 19;
    
    public static final int EAST_IN_NOC3    = 20;
    public static final int EAST_OUT_NOC3   = 21;
    public static final int WEAST_IN_NOC3   = 22;
    public static final int WEAST_OUT_NOC3  = 23;
    public static final int NORTH_IN_NOC3   = 24;
    public static final int NORTH_OUT_NOC3  = 25;
    public static final int SOUTH_IN_NOC3   = 26;
    public static final int SOUTH_OUT_NOC3  = 27;
    public static final int LOCAL_IN_NOC3   = 28;
    public static final int LOCAL_OUT_NOC3  = 29;
    
    public static final int NOC_ID_MULTIPLIER  = 10; //10 because each NoC (index above) is composed of 10 ports
    //****************************************************************************

    public static final int PERIPH_POS_WEST = 0;
    public static final int PERIPH_POS_EAST = 2;
    public static final int PERIPH_POS_NORTH = 3;
    public static final int PERIPH_POS_SOUTH = 4;


    
    public static final int XY = 1;
    
    public static ArrayList<Integer> TASK_ALLOCATION_SERVICES;
    public static ArrayList<Integer> TASK_TERMINATED_SERVICES;

    public MPSoCConfig(String debugDirPath) throws Exception {
       
        TASK_ALLOCATION_SERVICES = new ArrayList<>();
        TASK_TERMINATED_SERVICES = new ArrayList<>();
        this.channel_number = 3;
        this.globalManagerCluster = 0;
        this.througphputMonWindow = 0.500f;//ms
        try {
            
            RandomAccessFile platformFile = new RandomAccessFile(debugDirPath+"/platform.cfg", "r");
            
            this.debugDirPath = debugDirPath;
            
            String line = null;
            while((line = platformFile.readLine()) != null){
                
                String[] configInfo = line.split(" ");
                
                switch(configInfo[0]){
                    case "mpsoc_x":
                        mpsoc_x = Integer.parseInt(configInfo[1]);
                        break;
                    case "mpsoc_y":
                        mpsoc_y = Integer.parseInt(configInfo[1]);
                        break;
                    case "cluster_x":
                        cluster_x = Integer.parseInt(configInfo[1]);
                        break;
                    case "cluster_y":
                        cluster_y = Integer.parseInt(configInfo[1]);
                        break;
                    case "manager_position_x":
                        managerPosition_x = Integer.parseInt(configInfo[1]);
                        break;
                    case "manager_position_y":
                        managerPosition_y = Integer.parseInt(configInfo[1]);
                        break;
                    case "global_manager_cluster":
                        globalManagerCluster = Integer.parseInt(configInfo[1]);
                        break;
                    case "chipset_position":
                        String position = configInfo[1];
                        switch(position){
                            case "NORTH":
                                chipset_position = MPSoCConfig.PERIPH_POS_NORTH;
                                break;
                            case "SOUTH":
                                chipset_position = MPSoCConfig.PERIPH_POS_SOUTH;
                                break;
                            case "WEST":
                                chipset_position = MPSoCConfig.PERIPH_POS_WEST;
                                break;
                            case "EAST":
                                chipset_position = MPSoCConfig.PERIPH_POS_EAST;
                                break;
                            default:
                                throw new Exception("Value stated in chipset_position of platform.cfg is invalid: "+position);
                        }
                        break;
                    case "chipset_id":
                        chipset_id = Integer.parseInt(configInfo[1]);
                        break;
                    case "flit_size":
                        flitSize = Integer.parseInt(configInfo[1]);
                        break;
                    case "clock_period_ns":
                        clockPeriodInNs = Integer.parseInt(configInfo[1]);
                        break;
                    case "throughput_mon_window_ms":
                        througphputMonWindow = Float.parseFloat(configInfo[1]);
                        break;
                    case "BEGIN_task_name_relation":
                        initializeTaskNaming(platformFile);
                        break;
                    case "noc_number":
                        channel_number = Integer.parseInt(configInfo[1]);
                        break;
                    default:
                        break;
                        
                }
                
                
            }
            
            cluster_x = mpsoc_x;
            cluster_y = mpsoc_y;
                    
            
            discoveryGlobalXYPostion();
            
            platformFile.close();
            
            initializeServices();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MPSoCConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MPSoCConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeTaskNaming(RandomAccessFile platformFile) throws IOException{
     
        taskNameHash = new TreeMap<>();
        
        String line = null;
        while(!(line = platformFile.readLine()).equals("END_task_name_relation")){
            
            if (line.equals("")) continue;
            
            String[] taskNameID = line.split(" ");
            
            String taskName = taskNameID[0];
            Integer taskID = Integer.parseInt(taskNameID[1]);

            if (taskName != null && taskID != null){
                taskNameHash.put(taskID, taskName);
            }
            
        }
    }
    
    private void initializeServices() {
        
        servicesHash = new TreeMap<>();
        
        File servicesFile = new File(debugDirPath+"/services.cfg");
        try {
            RandomAccessFile reader = new RandomAccessFile(servicesFile, "r");
            
            String line = "";
            
            while((line = reader.readLine()) != null){
                
                if (line.equals("")) continue;
                
                String[] serviceInfo = line.split(" ");
                
                if (serviceInfo[0].equals("$TASK_ALLOCATION_SERVICE")){
                    
                    for (int i = 1; i < serviceInfo.length; i++) {
                        TASK_ALLOCATION_SERVICES.add(Integer.parseInt(serviceInfo[i]));
                    }
                    
                    continue;
                }
                if (serviceInfo[0].equals("$TASK_TERMINATED_SERVICE")){
                   
                    for (int i = 1; i < serviceInfo.length; i++) {
                        TASK_TERMINATED_SERVICES.add(Integer.parseInt(serviceInfo[i]));
                    }
                    continue;
                }
                
                servicesHash.put(Integer.parseInt(serviceInfo[1]), serviceInfo[0]);
            }
            
            reader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MPSoCConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MPSoCConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Set<Integer> keys = servicesHash.keySet();
        
        serviceReference = new int[keys.size()];
        
        int index = 0;
        for (Integer serviceValue : keys) {
            serviceReference[index] = serviceValue;
            index++;
        }
        
        
    }
    
    

    public static int getChannel(int port) {
        if (port == MPSoCConfig.LOCAL1 || port == MPSoCConfig.EAST1 || port == MPSoCConfig.NORTH1 || port == MPSoCConfig.EAST1 || port == MPSoCConfig.SOUTH1) {
            return MPSoCConfig.NOC2;
        }

        return MPSoCConfig.NOC1;
    }

    public String getStringServiceName(int service) {
        
        String serviceName = servicesHash.get(service);
        if (serviceName == null)
            return "---";
        return serviceName;
    }

    public int getServiceValue(String in) {
        
        Set<Integer> keys = servicesHash.keySet();
                
        for (Integer serviceValue : keys) {
            
            if (servicesHash.get(serviceValue).equals(in))
                return serviceValue;
            
        }
        
        return -1;
        
    }
    
    public int[] getServiceReference(){
        return serviceReference;
    }
    
    public String XYAdressToXYLabel(int xyAddr){
        int x = xyAddr;
        int y;
        x = x & 0xFFFF;//limpar o header
        y = x & 0xFF;//elimina o endereco x
        x = x >> 8;//elimina o endereco y
        
        return x+"x"+y;
    }
    
       public int xy_to_index(int xy_addr){
        int x, y;
        x = xy_addr & 0xFFFF;//limpar o header
        y = x & 0xFF;//elimina o endereco x
        x = x >> 8;//elimina o endereco y
        
        return ((y*mpsoc_x) + x);
    }
    
    
    public int index_to_xy(int index_addr){
        int x, y;
        
        y = index_addr / mpsoc_x;
        x = index_addr - (y * mpsoc_x);
        
        return (x << 8 | y);
    }
    
    public int XYLabelToIndexAddress(String xyLabel){
        String [] splited_line = xyLabel.split("x");
        
        int x = Integer.parseInt(splited_line[0]);
        int y = Integer.parseInt(splited_line[1]);
        
        return xy_to_index((x << 8 | y));
    }
    
    public String IndexAddressToXYLabel(int indexAddr){
        int addr = index_to_xy(indexAddr);
        return XYAdressToXYLabel(addr);
    }
    
    
    private void discoveryGlobalXYPostion(){
        int cluster_master = globalManagerCluster+1;
        int y_master = 0;
        int x_master = 0;

        while(cluster_master > 0){

            if (mpsoc_x%(cluster_master*cluster_x) == 0){
                x_master = cluster_master-1;
                cluster_master-= mpsoc_x/cluster_x;
            } else {
                y_master++;
                cluster_master -= mpsoc_x/cluster_x;
            }
            
            
        }
        
        global_position_x = x_master*cluster_x + managerPosition_x;
        global_position_y = y_master*cluster_y + managerPosition_y;
    }
    
    public int getPEType(int x, int y){
        
        if (x < managerPosition_x || y < managerPosition_y)
            return SLAVE;
        
        if (x == global_position_x && y == global_position_y)
            return GLOBAL_MASTER;
        
        if ((x-managerPosition_x)%cluster_x == 0 && (y-managerPosition_y)%cluster_y == 0)
            return CLUSTER_MASTER;
        
        return SLAVE;
        
    }
    
    
    public static int getNoCfromPort(int port){
        if (port < 5)
            return NOC1;
        if (port < 10)
            return NOC2;
        return NOC3;
    }

    public static String getPortString(int port) {

        switch (port) {
            case MPSoCConfig.EAST1:
                return "EAST 0";
            case MPSoCConfig.EAST2:
                return "EAST 1";
            case MPSoCConfig.EAST3:
                return "EAST 2";
            case MPSoCConfig.WEST1:
                return "WEST 0";
            case MPSoCConfig.WEST2:
                return "WEST 1";
            case MPSoCConfig.WEST3:
                return "WEST 2";
            case MPSoCConfig.NORTH1:
                return "NORTH 0";
            case MPSoCConfig.NORTH2:
                return "NORTH 1";
            case MPSoCConfig.NORTH3:
                return "NORTH 2";
            case MPSoCConfig.SOUTH1:
                return "SOUTH 0";
            case MPSoCConfig.SOUTH2:
                return "SOUTH 1";
            case MPSoCConfig.SOUTH3:
                return "SOUTH 2";
            case MPSoCConfig.LOCAL1:
                return "LOCAL 0";
            case MPSoCConfig.LOCAL2:
                return "LOCAL 1";
            case MPSoCConfig.LOCAL3:
                return "LOCAL 2";
        }
        return null;
    }
    
    
    public boolean isMasterAddress(int xyAddress){
        int xyMasterAddress = global_position_x << 8 | global_position_y;
        
        return (xyAddress == xyMasterAddress);
    }
    

    public int getVizinho_baixo(int router_address) {
        
        int y = router_address & 0xFF;
        int x = router_address >> 8;
        
        y++;
        
        if (y < mpsoc_y)
            return ((x << 8) | y);
        return -1;
    }

    public int getVizinho_cima(int router_address) {
        int y = router_address & 0xFF;
        int x = router_address >> 8;
        
        y--;
        
        if (y >= 0)
            return ((x << 8) | y);
        return -1;
       
    }

    public int getVizinho_esquerda(int router_address) {
        int y = router_address & 0xFF;
        int x = router_address >> 8;
        
        x--;
        
        if (x >= 0)
            return ((x << 8) | y);
        return -1;
        
    }

    public int getVizinho_direita(int router_address) {
        int y = router_address & 0xFF;
        int x = router_address >> 8;
        
        x++;
        
        if (x < mpsoc_x)
            return ((x << 8) | y);
        return -1;
        
    }
    
    public int getX_dimension() {
        return mpsoc_x;
    }

        public void setX_dimension(int x_dimension) {
        this.mpsoc_x = x_dimension;
    }

    public int getY_dimension() {
        return mpsoc_y;
    }
    
    public float getThrougphputMonWindow() {
        return througphputMonWindow;
    }

    public void setThrougphputMonWindow(float througphputMonWindow) {
        this.througphputMonWindow = througphputMonWindow;
    }

    public void setY_dimension(int y_dimension) {
        this.mpsoc_y = y_dimension;
    }
    
    public int getPENumber(){
        return mpsoc_x*mpsoc_y;
    }

    public int getX_cluster() {
        return cluster_x;
    }

    public void setX_cluster(int x_cluster) {
        this.cluster_x = x_cluster;
    }

    public int getY_cluster() {
        return cluster_y;
    }

    public void setY_cluster(int y_cluster) {
        this.cluster_y = y_cluster;
    }

    public String getDebugDirPath() {
        return debugDirPath;
    }
    
    public String getTestcasePath(){
        if (debugDirPath.indexOf("debug")-1 == -1)
            return null;
        return debugDirPath.substring(0, debugDirPath.indexOf("debug")-1);
    }

    public void setDebugDirPath(String debugDirPath) {
        this.debugDirPath = debugDirPath;
        initializeServices();
    }
    
    public int getFrequencyInHz(){
        return (1000000000 / clockPeriodInNs);
    }

    public int getFrequencyInKHz(){
        return getFrequencyInHz()/1000;
    }
    
    public int getFrequencyInMHz(){
        return getFrequencyInHz()/1000/1000;
    }

    public int getFlitSize() {
        return flitSize;
    }

    public void setFlitSize(int flitSize) {
        this.flitSize = flitSize;
    }

    public int getClockPeriodInNs() {
        return clockPeriodInNs;
    }

    public void setClockPeriodInNs(int clockPeriodInNs) {
        this.clockPeriodInNs = clockPeriodInNs;
    }
    
    public float getLinkBandwitdhIn_Mbps(){
        int aux = getFrequencyInHz() * flitSize;
        return (aux / 1000 / 1000);
    }

    public TreeMap<Integer, String> getServicesHash() {
        return servicesHash;
    }

    public TreeMap<Integer, String> getTaskNameHash() {
        return taskNameHash;
    }

    public int getManagerPosition_x() {
        return managerPosition_x;
    }

    public int getManagerPosition_y() {
        return managerPosition_y;
    }

    public int getGlobalManagerCluster() {
        return globalManagerCluster;
    }
    
    public int getChannel_number() {
        return channel_number;
    }

    public int getChipset_position() {
        return chipset_position;
    }

    public int getChipset_id() {
        return chipset_id;
    }

}