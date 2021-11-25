import matplotlib.pyplot as plt
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as cm           # import colormap stuff!
import numpy as np
import math
import matplotlib as mpl
import sys

NORMALIZED = False

x_series            = sys.argv[1]
NORMALIZED          = sys.argv[2]
PLOT_NAME           = sys.argv[3]
WINDOW_SIZE_KCYCLES = int(sys.argv[4])
MAX_Y               = int(float(sys.argv[5]))
SHOWPLT             = sys.argv[6]
RATE_AVERAGE        = float(sys.argv[7])

PLOT_NAME = PLOT_NAME.replace("_", " ")


#Convert String to Float values
x_series = map(float, x_series.strip('[]').split(','))


print "X serie:",x_series
print "Show",NORMALIZED,"values"
print "Plot name:",PLOT_NAME
print "Window size Kcycles:",WINDOW_SIZE_KCYCLES
print "Max_y_axis:",MAX_Y


fig, ax = plt.subplots()
fig.canvas.set_window_title(PLOT_NAME)

windows = np.arange(len(x_series)) #Xticks
width = 1 #Space between bars

x_series_set = np.array(x_series)


lw=1
fs='none'
lstile = '-'

plt.plot(windows, x_series_set, marker='o', ls=lstile, linewidth=lw, color='b', fillstyle=fs, markersize='4',  label=PLOT_NAME)



#plt.legend(loc=(0.1,1.02), frameon=True, edgecolor="black", framealpha=1, ncol=1) # framealpha=1 transparencia da porra da legenda
plt.legend(loc="upper left", frameon=True, edgecolor="black", framealpha=1, ncol=1) # framealpha=1 transparencia da porra da legenda

plt.xlabel("Monitoring window (x "+str(WINDOW_SIZE_KCYCLES*1000)+" Kcycles)")

plt.ylabel(PLOT_NAME)
if NORMALIZED == "Norm.":
    plt.ylabel(PLOT_NAME+" Normalized")

plt.xlim(0, len(x_series_set))

MAX_X_NUMBERS = (len(x_series_set)/16)
if MAX_X_NUMBERS == 0:
    MAX_X_NUMBERS = 1

plt.xticks(np.arange(0, len(x_series_set), step=(MAX_X_NUMBERS))) #Change step to increase the windows
plt.ylim(0, 1)

if NORMALIZED != "Norm" and "rate" not in PLOT_NAME:
    plt.ylim(0, MAX_Y)

    LEGEND_Y_STEP = MAX_Y / 8
    if LEGEND_Y_STEP == 0:
        LEGEND_Y_STEP = 1
    plt.yticks(np.arange(0, MAX_Y+1, step=LEGEND_Y_STEP)) #Change step to increase the windows


#Draw a line to indicate the average miss rate
if "rate" in PLOT_NAME:
    
    #SIMPLE MOVING AVERAGE -- NOT DESIRED
    #y_pos = sum(x_series_set) / len(x_series_set)
    
    #CUMULATIVE MOVING AVERAGE
    y_pos = RATE_AVERAGE

    x1, y1 = [0, len(x_series_set)], [y_pos, y_pos]
    plt.plot(x1, y1, ls='--')
    
    text = format((y_pos*100), ".2f")+"%"
    plt.text(0, y_pos+0.01, text , fontsize=8)


plt.tight_layout()
#plt.savefig(PLOT_NAME+".pdf", dpi=240)
#plt.savefig(PLOT_NAME+"jpeg", dpi=300)

if SHOWPLT == "True":
    plt.show()

