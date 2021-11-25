/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energyMemory.inputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import energyMemory.monitoredData.NoCEvent;

/**
 *
 * @author ruaro
 */
public class ReadMonitoredData {

    private String cpuAndMemLogPath;
    private String nocPath;

    private RandomAccessFile nocReader;
    private RandomAccessFile cpuAndMemReader;

    public ReadMonitoredData(String cpuLogPath, String nocPath) throws IOException {
        this.cpuAndMemLogPath = cpuLogPath;
        this.nocPath = nocPath;
        resetReader();
    }

    public void resetReader() throws IOException {
        //Cpu and Mem - sim.log
        File f = new File(this.cpuAndMemLogPath);
        if (f.exists()) {
            try {
                cpuAndMemReader = new RandomAccessFile(f, "r");
            } catch (FileNotFoundException ex) {
            }
        } else {
            throw new FileNotFoundException("CPU and Mem path not exists");
        }

        //NoC file
        f = new File(this.nocPath);
        if (f.exists()) {
            try {
                nocReader = new RandomAccessFile(f, "r");
                nocReader.readLine(); //Discard the first line of trrafic router.txt
            } catch (FileNotFoundException ex) {
            }
        } else {
            throw new FileNotFoundException();
        }
    }

    public EventInfo readNextCPUandMemValidLine() {

        while (true) {
            try {
                String line = cpuAndMemReader.readLine();
                if (line == null) {
                    return null;
                } else {
                    
                    if (line.contains(";")){

                        try {

                            line = line.substring(0, line.length()-1);
                            String[] split_data = line.split(":");

                            String line_event = split_data[0];
                            int time = Integer.parseInt(split_data[1]);

                            int addr = Integer.parseInt(split_data[2]);

                            EventInfo eventInfo = new EventInfo(line_event, time, addr);

                            return eventInfo;

                        } catch (Exception e) {
                        }
                    }
                }

            } catch (Exception e) {
                break;
            }
        }
        return null;

    }

    public NoCEvent readNoC_UntilTime(int timeLimit) {

        int router_address = 0;
        int time = 0;
        int size = 0;

        while (true) {
            try {
                long current_postion = nocReader.getFilePointer();
                String line = nocReader.readLine();
                if (line == null) {
                    return null;
                } else {
                    

                    String[] splitedLine = line.split("\t");

                    time = Integer.parseInt(splitedLine[0]);
                    router_address = Integer.parseInt(splitedLine[1]);
                    size = Integer.parseInt(splitedLine[3]);
                    //System.out.println(nocReader.getFilePointer());
                    
                    if (time > timeLimit){
                        nocReader.seek(current_postion);
                        return null;
                    }

                    NoCEvent nocEvent = new NoCEvent(time, size, router_address);

                    return nocEvent;

                }

            } catch (Exception e) {
                break;
            }
        }
        return null;
    }

}
