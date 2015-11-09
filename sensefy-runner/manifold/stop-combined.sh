#! /bin/sh
if [ -f "${sensefy.basepath}/manifold-run/manifold.pid" ]
then
	echo "[Apache ManifoldCF] Stopping"
	kill -9 `cat ${sensefy.basepath}/manifold-run/manifold.pid`
	rm ${sensefy.basepath}/manifold-run/manifold.pid
fi
exit 0