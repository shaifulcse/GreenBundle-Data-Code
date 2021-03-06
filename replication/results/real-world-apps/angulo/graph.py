
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
order='anguloOriginal','anguloBundling', 'anguloDropping', 'anguloOriginal-GM2','anguloBundling-GM2', 'anguloDropping-GM2'

time=[10,30,100,200,500,1000,2000]


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

	plt.xlabel("Bundling/Dropping Time (ms)",fontsize=14)
	plt.ylabel("Energy (J)",fontsize=18)

	handles, labels = ax.get_legend_handles_labels()
	# remove the errorbars
	handles = [h[0] for h in handles]
	# use them in the legend
	ax.legend(handles, labels, loc='upper left',numpoints=1)

	leg = plt.gca().get_legend()
	ltext  = leg.get_texts()
	plt.setp(ltext, fontsize=15)
	plt.grid(True)

	for label in ax.get_xticklabels():
    		label.set_fontsize(15)
	for label in ax.get_yticklabels():
    		label.set_fontsize(20)
	#ax.set_xscale('log')
	plt.xticks(range(len(time)), time)
	plt.margins(0.1)
	plt.title("Angulo App", fontsize=22)
	ax.set_ylim(None,55)
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
	
	plt.errorbar(range(len(time)), mean_energy[i], yerr=yerr_range, label=order[i], capsize=5, capthick=1, marker=marks[i], ls=lnst[i],  linewidth=4, markersize=marks_size[i],markerfacecolor=marker_color[i], color=marker_color[i])	

draw_graph()


