call rd /s /q "./logs/prod"
call mvn clean install -Pprod -DskipTests