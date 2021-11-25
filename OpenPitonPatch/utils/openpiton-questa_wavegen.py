#!/usr/bin/env python
import os
import sys


ENABLE_LSU_CTRL = 0
ENABLE_MMU = 0

dcache_interfaces = 3

#This script generates wave to modelsim


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

    gtkw_string = gtkw_string + """quietly WaveActivateNextPane {} 0
add wave -noupdate /cmp_top/system/chipset/chipset_clk
add wave -noupdate -radix decimal -radixshowbase 0 /cmp_top/network_mon/counter
add wave -noupdate /cmp_top/sys_rst_n
"""

    for n in range(1,4):
        noc_group = "-group NoC"+str(n)+"_In "
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_valid\n"
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_data\n"
        gtkw_string = gtkw_string + base_string + "-group ChipInput " + noc_group + radix_cfg + "/cmp_top/system/chip/offchip_processor_noc"+str(n)+"_yummy\n"

    for n in range(1,4):
        noc_group = "-group NoC"+str(n)+"_Out "
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_valid\n"
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_data\n"
        gtkw_string = gtkw_string + base_string + "-group ChipOutput " + noc_group + radix_cfg + "/cmp_top/system/chip/processor_offchip_noc"+str(n)+"_yummy\n"

    tile_counter = 0
    for x_val in range(0,X_DIMM):
        x = str(x_val)
        for y_val in range(0,Y_DIMM):
            y = str(y_val)
            tile = str(tile_counter)
            tile_name = x+"x"+y

            pe_group = "-group PE"+x+"x"+y+" "

            cache_group = "-group L1_"+tile_name+" "

#// Note:
#  // Ports 0/1 for PTW and LD unit are read only.
#  // they have equal prio and are RR arbited
#  // Port 2 is write only and goes into the merging write buffer
            #Input Cache
            dcache_req_signals =  ["address_index", "address_tag", "data_wdata", "data_req", "data_we", "data_be", "data_size", "kill_req", "tag_valid"]
            for n in range(0, dcache_interfaces):
                
                index = (dcache_interfaces-1) - n
                iface_name = str(index)
                
                if index == 0: #Unused Interface - index [0] in verilog
                    continue #Ignored because I didn't detected switching
                    cache_subgroup = "-group UNUSED-In("+iface_name+")-"+tile_name+" "
                elif index == 1: #Load Interface - index [1] in verilog
                    cache_subgroup = "-group Load-In("+iface_name+")-"+tile_name+" "
                else: #Store WriteBuffer Interface - index [2] in verilog
                    cache_subgroup = "-group Store-In("+iface_name+")-"+tile_name+" "

                for signal_name in dcache_req_signals:
                    gtkw_string = gtkw_string + base_string + pe_group + cache_group + cache_subgroup + radix_cfg + " {/cmp_top/system/chip/tile" + tile + "/g_ariane_core/core/ariane/i_cache_subsystem/dcache_req_ports_i["+iface_name+"]."+signal_name+"}\n"

            
            #Output Cache
            dcache_req_signals =  ["data_gnt", "data_rvalid", "data_rdata"]
            for n in range(0, dcache_interfaces):
                
                index = (dcache_interfaces-1) - n
                iface_name = str(index)
                
                if index == 0: #Unused Interface - index [0] in verilog
                    continue #Ignored because I didn't detected switching
                    cache_subgroup = "-group Load-UNUSED_In("+iface_name+")-"+tile_name+" "
                elif index == 1: #Load Interface - index [1] in verilog
                    cache_subgroup = "-group Load-Out("+iface_name+")-"+tile_name+" "
                else: #Store WriteBuffer Interface - index [2] in verilog
                    continue
                    cache_subgroup = "-group Store-Out("+iface_name+")-"+tile_name+" "

                for signal_name in dcache_req_signals:
                    gtkw_string = gtkw_string + base_string + pe_group + cache_group + cache_subgroup + radix_cfg + " {/cmp_top/system/chip/tile" + tile + "/g_ariane_core/core/ariane/i_cache_subsystem/dcache_req_ports_o["+iface_name+"]."+signal_name+"}\n"



            cpu_group = "-group CPU_"+tile_name+" "

            if ENABLE_MMU:
                module_group = "-group MMU_"+tile_name+" "
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-divider Input\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_req_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_vaddr_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_is_store_i\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-divider Output\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_valid_o\n"
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_mmu/lsu_paddr_o\n"

            module_group = "-group Store_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/valid_i\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/vaddr_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/lsu_ctrl.data\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_store_unit/valid_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/lsu_ctrl.be\n"

            module_group = "-group Load_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/valid_i\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/vaddr_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/valid_o\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/ex_stage_i/lsu_i/i_load_unit/result_o\n"

            if ENABLE_LSU_CTRL:
                module_group = "-group LSU_Ctrl"+tile_name+" "
                gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "{/cmp_top/system/chip/tile/g_ariane_core/core/ariane/ex_stage_i/lsu_i/lsu_ctrl}\n"

            module_group = "-group ICacheInstru_"+tile_name+" "
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_valid_q\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "-label addr /cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_vaddr_q\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/i_bht/index\n"
            gtkw_string = gtkw_string + base_string + pe_group + cpu_group + module_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/g_ariane_core/core/ariane/i_frontend/icache_data\n"

            #add wave -noupdate -expand -group ola /cmp_top/teta

            local_input_group = "-group Local_In "

            for n in range(1,4):
                noc = str(n)
                noc_group = "-group NoC"+str(n)+" "
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/router_buffer_data_val_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/router_buffer_data_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_input_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/buffer_router_yummy_noc"+noc+"\n"
            
            local_out_group = "-group Local_Out "

            for n in range(1,4):
                noc = str(n)
                noc_group = "-group NoC"+str(n)+" "
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/buffer_router_valid_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/buffer_router_data_noc"+noc+"\n"
                gtkw_string = gtkw_string + base_string + pe_group + local_out_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/router_buffer_consumed_noc"+noc+"\n"

            router_group = "-group Routers "

            port_list = extract_port_list(x_val, y_val, X_DIMM, Y_DIMM)

            for input in port_list:

                port_group = "-group Input-"+input+" "

                for n in range(1,4):
                    noc = str(n-1)
                    noc_group = "-group NoC"+str(n)+" "
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_validIn_"+input[0]+"\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_dataIn_"+input[0]+"\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_yummyOut_"+input[0]+"\n"

            for input in port_list:

                port_group = "-group Output-"+input+" "

                for n in range(1,4):
                    noc = str(n-1)
                    noc_group = "-group NoC"+str(n)+" "
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o_valid\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o\n"
                    gtkw_string = gtkw_string + base_string + pe_group + router_group + port_group + noc_group + radix_cfg + "/cmp_top/system/chip/tile"+tile+"/dyn"+noc+"_d"+input[0]+"o_yummy\n"
                
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