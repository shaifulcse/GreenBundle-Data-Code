#!/bin/bash
echo "We will run all the graph generation scripts!"
GRAPHS="./results/benchmark-app/bundling-dropping/graph.py
./results/benchmark-app/all-emitters-all-rate/graph.py
./results/real-world-apps/acrylicpaint/energy/graph.py
./results/real-world-apps/acrylicpaint/resource/graph_cpu.py
./results/real-world-apps/acrylicpaint/resource/graph_cntx.py
./results/real-world-apps/angulo/graph.py
./results/real-world-apps/sensor/normal/graph.py
./results/real-world-apps/sensor/variable-sampling/graph.py
./results/real-world-apps/colorpicker/graph.py"
for graph in $GRAPHS
do
	pushd `dirname "$graph"`
	python2 ./`basename "$graph"`
	popd
done
