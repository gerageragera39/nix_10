call rd /s /q "C:\Users\admin\IdeaProjects\nix_10\hw_4_logs_and_test\logs\test"
call mvn clean test -Ptest
call rd /s /q "C:\Users\admin\IdeaProjects\nix_10\hw_4_logs_and_test\logs\prod"
call mvn clean install -Pprod -DskipTests
call java -jar .\target\hw_4_logs_and_test.jar
