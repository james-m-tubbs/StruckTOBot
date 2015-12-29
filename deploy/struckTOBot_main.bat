SET echo ON
SET homedir=D:\Dev\git\StruckTOBot\deploy
SET main=ca.gkwb.struckto.main.StruckTOMain

SET classpath=%homedir%\*;%homedir%\lib\*;%homedir%\conf
echo Classpath = %classpath%

java %main% TPSOperations 100 BikeTO,WalkTO -Dlog4j.configuration=%homedir%\conf\log4j.properties -cp=%classpath%