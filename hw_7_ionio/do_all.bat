cd ..
cd .\parser\
call mvn clean install
cd ..
cd .\hw_7_ionio\
call rd /s /q "./logs/test"
call mvn clean test -Ptest
call rd /s /q "./logs/prod"
call mvn clean install -Pprod -DskipTests
call java -jar .\target\hw_7_ionio.jar