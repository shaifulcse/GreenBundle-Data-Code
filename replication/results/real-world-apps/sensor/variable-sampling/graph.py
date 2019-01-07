
import seaborn as sb
import  re
import matplotlib.pyplot as plt
from scipy.stats import sem, t
from scipy import mean
from scipy import stats
import numpy as np
fig = plt.figure()
ax = fig.add_subplot(111)


#### Global variables
order=['sensorOriginal', 'sensorBundling-0.1s', 'sensorDropping-0.1s']
time=[5,10,20,30]


lnst=["-", "--","-.", "-", ":", "--","-.", ":"]
marks=["^", "d", "o", "v", "p", "s", "<", ">"]
#marks_size=[15, 17, 10, 15, 17, 10, 12,15]
marks_size=[18, 24, 15, 20, 23, 13, 18,14]
marker_color=['#0F52BA','#ff7518','#6CA939','#e34234','#756bb1','brown','#c994c7', '#636363']
##################


#### calculate confidence interval with seaborn bootstrap

def  conf(a):

	return sb.utils.ci(sb.algorithms.bootstrap(np.asarray(a)), which=99)

## draw graph
def draw_graph():

	

	plt.legend(order,loc=0)

	plt.xlabel("# of Measurements/second",fontsize=16)
	plt.ylabel("Energy (J)",fontsize=16)

	handles, labels = ax.get_legend_handles_labels()
	# remove the errorbars
	handles = [h[0] for h in handles]
	# use them in the legend
	ax.legend(handles, labels, loc=0,numpoints=1)

	leg = plt.gca().get_legend()
	ltext  = leg.get_texts()
	plt.setp(ltext, fontsize=20)
	plt.grid(True)

	for label in ax.get_xticklabels():
    		label.set_fontsize(15)
	for label in ax.get_yticklabels():
    		label.set_fontsize(15)
	#ax.set_xscale('log')
	plt.xticks(range(len(time)), time)
	plt.margins(0.1)
	plt.title("SensorReadout App", fontsize=22)
	ax.set_ylim(None,100)
	#ax.set_aspect(0.6)
	#plt.arrow(1, 50, .2,0, head_width=.5)
	#plt.text(2, 49.5, "Number of emitters", fontsize=16)

	plt.show()


fr=open("measurements.csv","r")

fr.readline()## ignore  header

lines=fr.readlines()

dict={}
for line in lines:
	parsed=re.findall("[^,]+",line)
	version = parsed[0]+"_"+parsed[1]
	if version not in dict:
		dict[version]=[]
	dict[version].append(float(parsed[2]))



mean_energy=[]
mean_upper_error=[]*len(order)
mean_lower_error=[]*len(order)

for i in range(len(order)):
	
	mean_energy.append([])
	mean_upper_error.append([])
	mean_lower_error.append([])

index=0

for v in order:

	for  tm in time:
		version=v+"_"+str(tm)
		avg=np.mean(np.asarray(dict[version]))
		mean_energy[index].append(avg)		
					
		mean_lower_error[index].append(abs(conf(dict[version])[0]-avg))
		mean_upper_error[index].append(abs(conf(dict[version])[1]-avg))

	index=index+1


for i in range(len(order)):
	### convert confidence interval as error bar
	yerr_range=[]
	yerr_range.append(mean_lower_error[i])
	yerr_range.append(mean_upper_error[i])
	############
	
	plt.errorbar(range(len(time)), mean_energy[i], yerr=yerr_range, label=order[i], capsize=10, capthick=3, marker=marks[i], ls=lnst[i],  linewidth=4, markersize=marks_size[i],markerfacecolor=marker_color[i], color=marker_color[i])	

draw_graph()


