# GreenBundle-Data-Code

Artifacts of ICSE 2019 research paper (technical track)

GreenBundle: An Empirical Study on the Energy Impact of Bundled Processing.

1) replication:

   This directory contains measurements and scripts to generate the graphs used in the paper. 
   There are two subdirectories. 
   
   a) results:

   This directory contains all the measurement data and scripts to draw graphs for those data. 


   Requirements for running the python scripts for drawing graphs:

   i) Tested on Ubuntu 18.04.5 LTS. But should work on other systems. 

   ii) Python 2.7.12

   iii) Matplotlib 2.0
   
   sudo apt-get install python-matplotlib

   iv) Seaborn: it was used for calculating confidence interval with bootstrapping. 

   One way to install seaborn:

   sudo apt install python-pip

   sudo -H pip2 install --upgrade pip

   sudo pip2 install seaborn

   It is important to note that all measurements are shared. So one can draw graphs using any library. 


   b) maintenance-analysis: 
  
  This directory describe how to calculate the DL-score used in the paper for analyzing maintenance cost. The tools was made   commeercial by the original authors, limiting us to share it publicly.


2) extension:

  a) app-source-code:
  
  This directory contains the source code of all the apps (benchmark and real-world apps) including their versions      
  (original, bundling and dropping). For the bundling and dropping versions, one can modify the bundling and dropping waiting 
  times. Source code is shared for further extensions of this research. 
  
 

  b) apks:

   This directory contains some of the sample apks of the selected versions of the real-world apps. This is for quick   
   demonstraton in case building those apps from the source code is difficult (e.g., no Android Studio or similar tools are   
   already installed). The benchmark app has only one version, because one can set the type of version (noBundling, Bundling,    Dropping and other parameters at run-time). 
  
