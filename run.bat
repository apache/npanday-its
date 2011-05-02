@echo off
if X%1==X set test=
if not X%1==X set test=-Dtest=*%1*
@echo on
mvn test -Prun-its -Dnpanday.version=1.3.1-incubating-SNAPSHOT %test%