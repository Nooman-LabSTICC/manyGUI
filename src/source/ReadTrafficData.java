/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.MPSoCConfig;

/**
 *
 * @author Marcelo
 */
public final class ReadTrafficData {

    private RandomAccessFile traffic;
    private int packetReadControl;
    private ArrayList<PacketInformation> allPackets;
    private MPSoCConfig mPSoCConfig;
    private String currentHeader;

    public ReadTrafficData(MPSoCConfig mPSoCConfig) throws IOException {
        this.mPSoCConfig = mPSoCConfig;
        resetPacketCounter();
        allPackets = new ArrayList<PacketInformation>();
        currentHeader = "";
        
        File f = new File(this.mPSoCConfig.getDebugDirPath()+"/traffic_router.txt");
        if (f.exists()) {
            try {
                traffic = new RandomAccessFile(f, "r");
                traffic.readLine();//In case of OpenPiton it discard the fist line since it just textual description of each field
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReadTrafficData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new IOException();
        }
    }
    
    public void resetPacketCounter(){
        packetReadControl = 0;
    }
    
    public int getPacketCounterByTime(int time){
        int counter = 0;
        
        if (allPackets.get(allPackets.size()-1).getTime() < time || time < 0)
            return -1;
        
        counter = searchPacketTime(time, 0, allPackets.size()-1);
        
        
        while(allPackets.get(counter).getTime() <= time){
            counter++;
        }
        
        return counter--;
    }
    
    private int searchPacketTime(int time, int min, int max){
       if (max - min < 2)
           return min;
       
       if (allPackets.get(min+((max-min)/2)).getTime() < time)
           return searchPacketTime(time, min+((max-min)/2), max);
       
       return searchPacketTime(time, min, min+((max-min)/2));
    }
    
    public String getCurrentHeader(){
        return this.currentHeader;
    }
    
    
    public PacketInformation getNextPacket(){
        
        PacketInformation packet = null;
        
        if (packetReadControl == allPackets.size()){
            try {
                packet = getPacket(traffic.readLine());
                if (packet != null){
                    allPackets.add(packet);
                    packetReadControl++;
                }
            } catch (IOException ex) {}
        } else {
            packet = allPackets.get(packetReadControl);
            packetReadControl++;
        }
        
        
        
        return packet;
        
    }

    private PacketInformation getPacket(String line) {
        

        //System.out.println(line);
        
        if (line == null) {
            return null;
        }

        int router_address = 0;
        int time = 0;
        int service = 0;
        int size = 0;
        int bandwidthCycles = 0;
        int input_port = 0;
        int target_router = 0;
        int task_source = -1;
        int task_target = -1;
        int noc = -1;

        String[] splitedLine = line.split("\t");
        
        try{

            time = Integer.parseInt(splitedLine[0]);
            router_address = extractRouterAddress(splitedLine[1]);
            service = Integer.parseInt(splitedLine[2]);
            size = Integer.parseInt(splitedLine[3]);
            bandwidthCycles = Integer.parseInt(splitedLine[4]);
            noc = extractNoC(splitedLine[5]);
            input_port = extractInputPort(splitedLine[6], noc);
            target_router = extractRouterAddress(splitedLine[7]);
            //this.currentHeader = splitedLine[8]; //Uncomment this line to read the fullheader information

            /*if (splitedLine.length > 7){ //Maybe do this latter
                task_source = Integer.parseInt(splitedLine[7]);
                if (splitedLine.length > 8){
                    task_target = Integer.parseInt(splitedLine[8]);
                }
            }*/

            return new PacketInformation(router_address, time, service, size, bandwidthCycles, noc, input_port, target_router, task_source, task_target);

        } catch (Exception e){
            System.out.println("WARNING: Wrong packet format at time "+time+" of traffic_router.txt");
        }
        
        return null;

    }
    
    private int extractNoC(String noc){
        int nocRet = -1;
        switch(noc){
            case "1":
                nocRet = MPSoCConfig.NOC1;
                break;
            case "2":
                nocRet = MPSoCConfig.NOC2;
                break;
            case "3":
                nocRet = MPSoCConfig.NOC3;
                break;
            default:
                System.out.println("Error: wrong NoC from traffic_router.txt");
        }
        return nocRet;
    }
    
    
    /*
#define EAST 	0
#define WEST 	1
#define NORTH 	2
#define SOUTH 	3
#define LOCAL 	4
#define NPORT 	5
    */
    private int extractInputPort(String inport, int noc){
        int port = -1;
        switch(inport){
            case "E":
                port = 0;
                break;
            case "W":
                port = 1;
                break;
            case "N":
                port = 2;
                break;
            case "S":
                port = 3;
                break;
            case "L":
                port = 4;
                break;
            default:
                System.out.println("Error: wrong input port");
        }
        
        port = port + ((noc-1)*MPSoCConfig.NPORT_PER_ROUTER);
        
        return port;
    }
    
    private int extractRouterAddress(String value){
        
         /*Trecho para representacao hexadecimal
                int a = packet_readed.getRouter_address();
                a = Integer.parseInt(Integer.toString(a), 16);
                packet_readed.setRouter_address(a);
                
                a = packet_readed.getTarget_router();
                a = Integer.parseInt(Integer.toString(a), 16);
                packet_readed.setTarget_router(a);
                 */
        
        int address = Integer.parseInt(value);
        
        //This IF will limitate the Debugger because it cannot support many-core sizes higher than
        //mPSoCConfig.getChipset_id(). To support this, change this code and also the way how chipset id
        //is generated. Today, the hw monitor test if the chipset ID is != 0, if true, it returns the id
        //instead the target router address.
        //The implementation inside hw is in: /home/ruaro/openpiton/piton/verif/env/manycore/manycore_network_mon.v.pyv line 123
        if (address == mPSoCConfig.getChipset_id()){
            return address;
        }
        
        return Integer.parseInt(value);
    }

    public ArrayList<PacketInformation> getAllPackets() {
        return allPackets;
    }
}
