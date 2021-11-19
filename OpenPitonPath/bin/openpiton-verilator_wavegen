#!/usr/bin/env python
import os
import sys


ENABLE_LSU_CTRL = 0
ENABLE_MMU = 0

#This script generates wave to modelsim

#Cache Data Signals - Input
dcache_req_i_base_path = "/g_ariane_core/core/ariane/i_cache_subsystem"
dcache_req_i_tgt_signal = "dcache_req_ports_i"
dcache_req_i_t_signals =  ["address_i_index", "address_tag", "data_wdata", "data_req", "data_we", "data_be", "data_size", "kill_req", "tag_valid"]
dcache_req_i_t_bit_size = [     11,              45,            64,         1,          1,          8,          2,           1,          1     ]
dcache_req_i_total_size = 134
dcache_req_i_interfaces = 3


#Cache Data Signals - Output
dcache_req_o_base_path = "/g_ariane_core/core/ariane/i_cache_subsystem"
dcache_req_o_tgt_signal = "dcache_req_ports_o"
dcache_req_o_t_signals =  ["address_o_index", "data_rvalid", "data_rdata"]
dcache_req_o_t_bit_size = [     1,              1,            64]
dcache_req_o_total_size = 66
dcache_req_o_interfaces = 3

#LSU_Ctrl
lsu_ctrl_base_path = "/g_ariane_core/core/ariane/ex_stage_i/lsu_i"
lsu_ctrl_signal = "lsu_ctrl"
lsu_ctrl_signals =  ["valid", "vaddr", "overflow", "data", "be", "fu", "operator", "trans_id"]
lsu_ctrl_bit_size = [     1,    64,       1,         64,    8,    4,       7,           3]
lsu_ctrl_total_size = 152
lsu_ctrl_interfaces = 1


def create_cache_interface(interface_num, tile, base_path, comple_path, signal_names, signal_sizes, package_size):
    iface = ""

    name_list = []

    for t in range(tile):

        name_list.append([])

        start_bit = (package_size*interface_num) 
        end_bit = (package_size*interface_num)

        for i in range(0, interface_num):
            if interface_num == 1:
                iface_num = ""
            else:
                iface_num = str(interface_num-(i+1))

            name_list[t].append([])

            for sig_i in range(len(signal_names)):
                signal_name = signal_names[sig_i]+"_"+iface_num+"_t"+str(t)
                signal_size = signal_sizes[sig_i]

                start_bit = end_bit - 1
                end_bit = end_bit - signal_size

                iface = iface + "quietly virtual signal -install /TOP/cmp_top/system/chip/tile" +str(t) + base_path + " { /TOP/cmp_top/system/chip/tile"+str(t) + base_path +"/"+ comple_path +"["+str(start_bit)+":"+str(end_bit)+"]} "+signal_name+"\n"

                name_list[t][i].append(signal_name)
            #print name_list[t][i]

    return iface, name_list


def extract_port_list(x_pos, y_pos, x_max, y_max):
    port_list = []
    if x_pos > 0:
        port_list.append("WEST")
    
    if x_pos < (x_max-1):
        port_list.append("EAST")

    if y_pos < (y_max-1):  
        port_list.append("NORTH")

    if y_pos > 0:  
        port_list.append("SOUTH")

    #print x_pos, y_pos, port_list
    return port_list

    #Debug all ports
    #return ["EAST", "WEST", "NORTH", "SOUTH"]


def main():

    try:
        X_DIMM = int(sys.argv[1]) #-ntm: apply ntm, -cow: apply cow to run in local machine, -cowsniper: apply cow to run in sniper simulator 
    except:
        sys.exit("\nYou must inform as 1st argument a integer positive number representing the X dimmension, ex 2 \n")
    try:
        Y_DIMM = int(sys.argv[2]) #-ntm: apply ntm, -cow: apply cow to run in local machine, -cowsniper: apply cow to run in sniper simulator 
    except:
        sys.exit("\nYou must inform as 2nd argument a integer positive number representing the Y dimmension, ex 2 \n")


    base_string = "add wave -noupdate "

    radix_cfg = "-radix hexadecimal -radixshowbase 0 "

    gtkw_string = "onerror {resume}\n"

    #Data cache input
    quietly_string, cache_input_list = create_cache_interface(dcache_req_i_interfaces, X_DIMM*Y_DIMM, dcache_req_i_base_path, dcache_req_i_tgt_signal, dcache_req_i_t_signals, dcache_req_i_t_bit_size, dcache_req_i_total_size)
    gtkw_string = gtkw_string + quietly_string

    #Data cache output
    quietly_string, cache_output_list = create_cache_interface(dcache_req_o_interfaces, X_DIMM*Y_DIMM, dcache_req_o_base_path, dcache_req_o_tgt_signal, dcache_req_o_t_signals, dcache_req_o_t_bit_size, dcache_req_o_total_size)
    gtkw_string = gtkw_string + quietly_string

    #LSU_ctrl
    quietly_string, lsu_list = create_cache_interface(lsu_ctrl_interfaces, X_DIMM*Y_DIMM, lsu_ctrl_base_path, lsu_ctrl_signal, lsu_ctrl_signals, lsu_ctrl_bit_size, lsu_ctrl_total_size)
    gtkw_string = gtkw_string + quietly_string

    gtkw_string = gtkw_string + """quietly WaveActivateNextPane {} 0
add wave -noupdate /TOP/cmp_top/system/chipset/chipset_clk
add wave -noupdate -radix decimal -radixshowbase 0 /TOP/cmp_top/network_mon/counter
add wave -noupdate /TOP/cmp_top/sys_rst_n
"""

    for n in range(1,4):
        noc_group = "-group NoC"+str(n)+"_In "
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_valid\n"
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_data\n"
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_yummy\n"

    for n in range(1,4):
        noc_group = "-group NoC"+str(n)+"_Out "
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_valid\n"
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_data\n"
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_yummy\n"

    tile_counter = 0
    for x_val in range(0,X_DIMM):
        x = str(x_val)
        for y_val in range(0,Y_DIMM):
            y = str(y_val)
            tile = str(tile_counter)
            tile_name = x+"x"+y

            pe_group = "-group PE"+x+"x"+y+" "

#add wave -noupdate -expand -group CPU_to_L1 -expand -group Normal -expand -group L1-Input /TOP/cmp_top/system/chip/tile0/g_ariane_core/core/ariane/i_cache_subsystem/kill_req002
            cache_group = "-group L1_"+tile_name+" "

#// Note:
#  // Ports 0/1 for PTW and LD unit are read only.
#  // they have equal prio and are RR arbited
#  // Port 2 is write only and goes into the merging write buffer
            #Input Cache
            cache_signal_list = cache_input_list[tile_counter]
            for n in range(0, dcache_req_i_interfaces):

                #Real port is because the port are inverted, real hw ports are inverted in relation to n
                real_port = dcache_req_i_interfaces - (n+1)

                if n == 2: #Unused Interface - index [0] in verilog
                    continue #Ignored because I didn't detected switching
                    input_cache_group = "-group UNUSED_In______("+str(real_port)+")_"+tile_name+" "
                elif n == 1: #Load Interface - index [1] in verilog
                    input_cache_group = "-group Load_In______("+str(real_port)+")_"+tile_name+" "
                else: #Store WriteBuffer Interface - index [2] in verilog
                    input_cache_group = "-group Store_In______("+str(real_port)+")_"+tile_name+" "

                
                for signal_name in cache_signal_list[n]:
                    gtkw_string = gtkw_string + base_string + pe_group + cache_group + input_cache_group + radix_cfg + "/TOP/cmp_top/system/chip/tile" + tile + dcache_req_i_base_path + "/" + signal_name +"\n"


            #Output Cache
            cache_signal_list = cache_output_list[tile_counter]
            for n in range(0, dcache_req_o_interfaces):

                real_port = dcache_req_i_interfaces - (n+1)

                if n == 2: #Unused Interface - index [0] in verilog
                    continue #Ignored because I didn't detected switching
                    output_cache_group = "-group UNUSED_Out______("+str(real_port)+")_"+tile_name+" "
                elif n == 1: #Load Interface - index [1] in verilog
                    output_cache_group = "-group Load_Out______("+str(real_port)+")_"+tile_name+" "
                else: #Store WriteBuffer Interface - index [2] in verilog
                    continue #Ignored because I didn't detected switching - maybe because store do not need to return anything
                    output_cache_group = "-group Store_Out______("+str(real_port)+")_"+tile_name+" "

                
                for signal_name in cache_signal_list[n]:
                    gtkw_string = gtkw_string + base_string + pe_group + cache_group + output_cache_group + radix_cfg + "/TOP/cmp_top/system/chip/tile" + tile + dcache_req_i_base_path + "/" + signal_name +"\n"

            cpu_group = "-group CPU_"+tile_name+" "

            if ENABLE_MMU:
                module_group = "-group MMU_"+tile_name+" "
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-divider Input\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_req_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_vaddr_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_is_store_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-divider Output\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_valid_o\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_paddr_o\n"

            module_group = "-group Store_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/valid_i\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/vaddr_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/data__t"+tile+"\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/valid_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/be__t"+tile+"\n"

            module_group = "-group Load_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/valid_i\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/vaddr_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/valid_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/result_o\n"

            if ENABLE_LSU_CTRL:
                module_group = "-group LSU_Ctrl"+tile_name+" "
                lsu_ctrl_signal_list = lsu_list[tile_counter]
                for signal_name in lsu_ctrl_signal_list[0]:
                    gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile" + tile + lsu_ctrl_base_path + "/" + signal_name +"\n"

            module_group = "-group ICacheInstru_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_valid_q\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-label addr /TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_vaddr_q\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/i_bht/index\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_data\n"

            #add wave -noupdate -expand -group ola /TOP/cmp_top/teta

            local_input_group = "-group Local_In "

            for n in range(1,4):
                noc = str(n)
                noc_group = "-group NoC"+str(n)+" "
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/router_buffer_data_val_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/router_buffer_data_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/buffer_router_yummy_noc"+noc+"\n"
            
            local_out_group = "-group Local_Out "

            for n in range(1,4):
                noc = str(n)
                noc_group = "-group NoC"+str(n)+" "
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/buffer_router_valid_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/buffer_router_data_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/router_buffer_consumed_noc"+noc+"\n"

            router_group = "-group Routers "

            port_list = extract_port_list(x_val, y_val, X_DIMM, Y_DIMM)

            for input in port_list:

                port_group = "-group Input-"+input+" "

                for n in range(1,4):
                    noc = str(n-1)
                    noc_group = "-group NoC"+str(n)+" "
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_validIn_"+input[0]+"\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_dataIn_"+input[0]+"\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_yummyOut_"+input[0]+"\n"

            for input in port_list:

                port_group = "-group Output-"+input+" "

                for n in range(1,4):
                    noc = str(n-1)
                    noc_group = "-group NoC"+str(n)+" "
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o_valid\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/TOP/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o_yummy\n"
                
            tile_counter = tile_counter + 1

    gtkw_string = gtkw_string + """
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {263 ns} 0}
quietly wave cursor active 1
configure wave -namecolwidth 172
configure wave -valuecolwidth 132
configure wave -justifyvalue left
configure wave -signalnamewidth 1
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {0 ns} {33209400 ns}
"""

    #print gtkw_string
    file = open("wave.do", 'w')
    file.write(gtkw_string)
    file.close() 


#Call of function main, this approaches enables to call a function before it declaration
main()