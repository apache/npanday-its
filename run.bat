@echo off
if X%1==X set test=
if not X%1==X set test=-Dtest=*%1*
@echo on
mvn test -Prun-its -Dnpanday.version=1.4.0-incubating-SNAPSHOT %test%