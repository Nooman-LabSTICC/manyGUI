import matplotlib.pyplot as plt
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as cm           # import colormap stuff!
import numpy as np
import math
import matplotlib as mpl
import sys

NORMALIZED = False

noc = sys.argv[1]
mem = sys.argv[2]
cpu = sys.argv[3]
NORMALIZED = sys.argv[4]
PLOT_NAME = sys.argv[5]
WINDOW_SIZE_KCYCLES = int(sys.argv[6])
MAX_Y = int(float(sys.argv[7]))
SHOWPLT = sys.argv[8]

PLOT_NAME = PLOT_NAME.replace("_", " ")


#Convert String to Float values
noc = map(float, noc.strip('[]').split(','))
mem = map(float, mem.strip('[]').split(','))
cpu = map(float, cpu.strip('[]').split(','))

print "NoC serie:",noc
print "Mem serie:",mem
print "CPU serie:",cpu
print "Show",NORMALIZED,"values"
print "Plot name:",PLOT_NAME
print "Window size Kcycles:",WINDOW_SIZE_KCYCLES
print "Max_y_axis:",MAX_Y


fig, ax = plt.subplots()
fig.canvas.set_window_title(PLOT_NAME)

windows = np.arange(len(noc)) #Xticks
width = 1 #Space between bars

noc_set = np.array(noc)
mem_set = np.array(mem)
cpu_set = np.array(cpu)


plt.bar(windows, noc_set, width, color='g', edgecolor = "white", label='Energy NoC')
plt.bar(windows, mem_set, width, bottom=noc_set, color='b', edgecolor = "white", label='Energy Memory')
plt.bar(windows, cpu_set, width, bottom=noc_set+mem_set, color='r', edgecolor = "white", label='Energy CPU')

plt.legend(loc=(0.1,1.02), frameon=True, edgecolor="black", framealpha=1, ncol=3) # framealpha=1 transparencia da porra da legenda

plt.xlabel("Monitoring window (x "+str(WINDOW_SIZE_KCYCLES*1000)+" Kcycles)")

plt.ylabel(PLOT_NAME+" (nJ)")
if NORMALIZED == "Norm.":
    plt.ylabel(PLOT_NAME+" Normalized")


plt.xlim(0, len(noc))

MAX_X_NUMBERS = (len(noc)/16)
if MAX_X_NUMBERS == 0:
    MAX_X_NUMBERS = 1

plt.xticks(np.arange(0, len(noc), step=(MAX_X_NUMBERS))) #Change step to increase the windows
plt.ylim(0, 1)
if NORMALIZED != "Norm":
    plt.ylim(0, MAX_Y)

    LEGEND_Y_STEP = MAX_Y / 8
    if LEGEND_Y_STEP == 0:
        LEGEND_Y_STEP = 1
    plt.yticks(np.arange(0, MAX_Y+1, step=LEGEND_Y_STEP)) #Change step to increase the windows



plt.tight_layout()
#plt.savefig(PLOT_NAME+".pdf", dpi=240)
#plt.savefig(PLOT_NAME+"jpeg", dpi=300)

if SHOWPLT == "True":
    plt.show()

