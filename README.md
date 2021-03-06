# GreenBundle-Data-Code

Artifacts of ICSE 2019 research paper (technical track)

GreenBundle: An Empirical Study on the Energy Impact of Bundled Processing.

Authors: Shaiful Chowdhury, Abram Hindle, Rick Kazman, Takumi Shuto, Ken Matsui and Yasutaka Kamei


There are two main directories: 1) replication and 2) extension. 

The replication directory contains information for reproducing the results presented in the paper. 

The extension directory contain source code (and sample apks) of the benchmark and real-world apps for further studies
in this direction. 


# replication:

       There are two sub-directories: a) results and b) maintainability-analysis.
   
## a) results:

          This directory contains all the measurement data and scripts to draw graphs for those data. 


          Requirements for running the python scripts for drawing graphs:

### i) Tested on Ubuntu 18.04.5 LTS. But should work on other systems. 

#### ii) install pip for python2 

          sudo apt install python-pip

          sudo -H pip2 install --upgrade pip

####  iii) Python 2.7

####  iv) Matplotlib 2.0
   
          sudo apt-get install python-matplotlib

or
          
          pip2 install --user -U python-matplotlib 

#### iv) Seaborn: it was used for calculating confidence interval with bootstrapping. 

          One way to install seaborn:

          sudo apt-get install python-seaborn

or

          pip2 install --user -U seaborn

          pip2 install -U --user backports.functools_lru_cache

          # in the root dir of the replication repository

          cd ./replication/
          bash plot-all.sh

          # or for drawing a specific graph

          cd ./replication/results/results/benchmark-app/all-emitters-all-rate/
          python2 graph.py
          
          
It is important to note that all measurements are shared. So one can draw graphs using any library. 

   
   
## b) maintainability-analysis: 
  
This directory describe how to calculate the DL-score used in the paper for analyzing maintenance cost. 
The tool we used was made commercial by the original authors, thus limiting us to share it publicly.





# 2) extension:

##  a) app-source-code:
  
This directory contains the source code of all the apps (benchmark and real-world apps) including their versions      
(original, bundling and dropping). For the bundling and dropping versions, one can modify the bundling and dropping   
waiting times. Source code is shared for further extensions of this research. 
  
 

##  b) apks:

This directory contains some of the sample apks of the selected versions of the benchmark and real-world apps. 
This is for quick demonstraton in case building those apps from the source code is difficult (e.g., no Android 
Studio or similar tools are already installed). The benchmark app has only one version, because one can set 
the type of version at run-time (noBundling, Bundling, Dropping and other parameters such as bundling/dropping 
waiting time). 

# Licensing
  
Unless otherwise specified, the graphs and the text of this replication is licensed under the  CC-BY-SA 4.0 License (c) 2018 Shaiful Chowdhury

Unless otherwise specified, the code is licensed under the Apache 2.0 License
(c) 2018 Shaiful Chowdhury 

Academics are expected to cite the work:

    @inproceedings{ChowdhuryICSE2019,
       title={GreenBundle: An Empirical Study on the Energy Impact of Bundled Processing},
       authors={Shaiful Chowdhury, Abram Hindle, Rick Kazman, Takumi Shuto, Ken Matsui and Yasutaka Kamei},
      booktitle = {Proc. of the International Conference on Software Engineering (ICSE-2019)},
      year      = {2019},
      pages     = 12
    }


