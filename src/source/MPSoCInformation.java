/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.IOException;
import java.util.HashMap;
import util.MPSoCConfig;

/**
 *
 * @author Marcelo
 */
public final class MPSoCInformation {

    private ReadTrafficData readTraffic;
    private HashMap<Integer, RouterInformation> PE_Information;
    private MPSoCConfig mPSoCConfig;

    public MPSoCInformation(MPSoCConfig noCConfig) throws IOException {
        this.mPSoCConfig = noCConfig;
        initalizePEInformation();
        readTraffic = new ReadTrafficData(this.mPSoCConfig);
    }

    public void initalizePEInformation() {
        PE_Information = new HashMap<Integer, RouterInformation>();

        for (int x = 0; x < mPSoCConfig.getX_dimension(); x++) {
            for (int y = 0; y < mPSoCConfig.getY_dimension(); y++) {
                int xyAddr = x << 8 | y;
                PE_Information.put(xyAddr, new RouterInformation(mPSoCConfig, xyAddr));
            }
        }
        
        //Adds the Chipset information
        //PE_Information.put(mPSoCConfig.getChipset_id(), new RouterInformation(mPSoCConfig, mPSoCConfig.getChipset_id()));
        
    }
    
    public String getCurrentHeader(){
        return this.readTraffic.getCurrentHeader();
    }

    public int getPacketCounterByTime(int time) { //retorna o numero de pacotes ate chegar no tempo desejado
        int counter = readTraffic.getPacketCounterByTime(time);

        if (counter != -1) {
            readTraffic.resetPacketCounter();
        }

        return counter;
    }

    public RouterInformation getRouterInformation(int router_address) {
        return PE_Information.get(router_address);
    }
    
    public int getTotalNoCVolume(){
        int maxPE = mPSoCConfig.getPENumber();
        int totalVolume = 0;
        for (int router = 0; router < maxPE; router++) {
            totalVolume += getRouterInformation(router).getRouterTotalVolumeInFlits();
        }
        
        return totalVolume;
    }
    
    public int getTotalNoCBandwidth(){
       int maxPE = mPSoCConfig.getPENumber();
        int totalVolume = 0;
        for (int router = 0; router < maxPE; router++) {
            totalVolume += getRouterInformation(router).getRouterTotalBandwidthInCycles();
        }
        
        return totalVolume;
    }
    
    public int getTotalNoCServiceVolume(int[] services){
        int maxPE = mPSoCConfig.getPENumber();
        int totalVolume = 0;
        for (int router = 0; router < maxPE; router++) {
            totalVolume += getRouterInformation(router).getRouterTotalServicesVolumeInFlits(services);
        }
        
        return totalVolume;
    }
    
    public int getTotalNoCServiceBandwidth(int[] services){
        int maxPE = mPSoCConfig.getPENumber();
        int totalVolume = 0;
        for (int router = 0; router < maxPE; router++) {
            totalVolume += getRouterInformation(router).getRouterTotalServicesBandwidthInCycles(services);
        }
        
        return totalVolume;
    }

    public PacketInformation getNextPacket(FilterForm filter, int limitTime) {

        while (true) {

            PacketInformation packet_readed = readTraffic.getNextPacket();

            if (packet_readed == null) {
                return null;
            }
            
            if (!mPSoCConfig.getServicesHash().containsKey(packet_readed.getService())) {
                return packet_readed;
            }
             
            if (!filter.filter(packet_readed)){
                if (limitTime == 0 || limitTime > packet_readed.getTime()){
                    continue;
                }
            }

            //Add the packet to the respective router
            RouterInformation r = PE_Information.get(packet_readed.getRouter_address());
            r.addPacket(packet_readed);
            //Update the router Information List
            PE_Information.put(packet_readed.getRouter_address(), r);

            return packet_readed;
        }
    }

    public ReadTrafficData getReadTraffic() {
        return readTraffic;
    }
}
