cd D:\vscode\java\dataDistributionCenter\data-distribution-center\distribution-center-common
call mvn clean
call mvn compile
call mvn package
call mvn install:install-file -Dfile='D:\vscode\java\dataDistributionCenter\data-distribution-center\distribution-center-common\target\distribution-center-common-1.0-SNAPSHOT-jar-with-dependencies.jar' -DgroupId='www.ezrpro.com' -DartifactId=distribution-center-common -Dversion'=1.0-SNAPSHOT' -Dpackaging=jar
cd cd D:\vscode\java\dataDistributionCenter\data-distribution-center\distribution-center-web
call mvn clean
call mvn compile
call mvn package