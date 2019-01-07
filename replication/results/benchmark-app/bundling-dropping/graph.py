
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
order=['Nobundling', "Bundling-0.1s", "Bundling-0.5s", "Bundling-1s", "Dropping-0.1s", "Dropping-0.5s",  "Dropping-1s"]

time=[1, 2, 4,8, 16, 32, 64, 128]

lnst=["-", "--","-.", "-", ":", "--","-.", ":"]
marks=["^", "d", "o", "v", "p", "s", "<", ">"]
#marks_size=[15, 17, 10, 15, 17, 10, 12,15]
marks_size=[18, 18, 24, 23, 18, 18, 18,14]
marker_color=['#0F52BA','#ff7518','#6CA939','#e34234','#756bb1','brown','#c994c7', '#636363']
##################


#### calculate confidence interval with seaborn bootstrap

def  conf(a):

	return sb.utils.ci(sb.algorithms.bootstrap(np.asarray(a)), which=99)

## draw graph
def draw_graph():

	

	plt.xlabel("Rate (events/second)",fontsize=15)
	plt.ylabel("Energy (J)",fontsize=20)

	handles, labels = ax.get_legend_handles_labels()
	handles = [h[0] for h in handles]
	# use them in the legend
	ax.legend(handles, labels, loc='upper left',numpoints=1)

	leg = plt.gca().get_legend()
	ltext  = leg.get_texts()
	plt.setp(ltext, fontsize=20)
	plt.grid(True)

	for label in ax.get_xticklabels():
    		label.set_fontsize(14)
	for label in ax.get_yticklabels():
    		label.set_fontsize(20)
	#ax.set_xscale('log')
	plt.xticks(range(len(time)), time)
	plt.margins(0.1)
	plt.title("Number of emitters = 1", fontsize=22)
	#ax.set_ylim(20,33)

	plt.show()


fr=open("emitter_1.csv","r")

fr.readline()## ignore  header

lines=fr.readlines()

dict={}

for line in lines:
	parsed=re.findall("[^,]+",line)
	version = parsed[0]+"_"+parsed[1]
	if version not in dict:
		dict[version]=[]
	dict[version].append(float(parsed[3]))




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
		version=str(v)+"_"+str(tm)
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
	
	plt.errorbar(range(len(time)), mean_energy[i], yerr=yerr_range, capsize=5, capthick=2, marker=marks[i], ls=lnst[i], label=order[i], linewidth=4, markersize=marks_size[i],markerfacecolor=marker_color[i], color=marker_color[i])	

draw_graph()


