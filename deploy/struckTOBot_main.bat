SET echo ON
SET homedir=D:\Dev\git\StruckTOBot\deploy
#SET main=ca.gkwb.struckto.main.StruckTOMain
SET main=StruckTOBot-FULL.jar

SET classpath=%homedir%\*;%homedir%\conf
echo Classpath = %classpath%

#java %main% TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=%homedir%\conf\log4j.properties -cp=%classpath%
java -jar %main% TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=file:%homedir%\conf\log4j.properties -cp=%classpath%