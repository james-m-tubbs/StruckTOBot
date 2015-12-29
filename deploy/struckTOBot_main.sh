homedir=/home/Gingerk1d/StruckTOBot
echo $homedir
main=ca.gkwb.struckto.main.StruckTOMain
echo $main

#classpath=$(echo $homedir/lib/*.jar | tr ' ' ':'):$homedir/conf:$homedir/StruckTOBot.jar
classpath=$homedir:$homedir/StruckTOBot.jar
echo Classpath = $classpath

java $main TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=$homedir/conf/log4j.properties -cp=$classpath
