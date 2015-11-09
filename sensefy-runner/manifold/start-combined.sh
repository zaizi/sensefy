#! /bin/sh
ps -p `cat ${sensefy.basepath}/manifold-run/manifold.pid` > /dev/null
if [ $? -eq 0 ]
then
	echo "[Apache ManifoldCF] Stopping an already running instance"
	kill -9 `cat ${sensefy.basepath}/manifold-run/manifold.pid`
	sleep 5s
fi
echo "[Apache ManifoldCF] Starting"
$* > /dev/null 2>&1 &
echo $! > ${sensefy.basepath}/manifold-run/manifold.pid
exit 0