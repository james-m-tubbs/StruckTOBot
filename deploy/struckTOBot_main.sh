homedir=/home/Gingerk1d/StruckTOBot
echo $homedir

#combined file config
main=/home/Gingerk1d/StruckTOBot/StruckTOBot-FULL.jar

#split file config
#main=ca.gkwb.struckto.main.StruckTOMain
echo $main

#Split file config
#classpath=$(echo $homedir/lib/*.jar | tr ' ' ':'):$homedir/conf:$homedir/StruckTOBot.jar
#classpath=$homedir:$homedir/StruckTOBot.jar
#echo Classpath = $classpath

#split file run
#java $main TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=$homedir/conf/log4j.properties -cp=$classpath

#bundled file run
java -jar $main TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=$homedir/conf/log4j.properties
