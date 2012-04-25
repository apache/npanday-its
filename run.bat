REM Start of LICENSE
GOTO LicenseComment
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
:LicenseComment

@echo off
if X%1==X set test=
if not X%1==X set test=-Dtest=*%1*

if X%2==X set version=1.5.0-incubating-SNAPSHOT
if not X%2==X set version=%2
@echo on

mvn test -Prun-its -o -Dnpanday.version=%version% %test%