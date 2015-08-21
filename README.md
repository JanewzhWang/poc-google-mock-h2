# poc-google-mock-h2

An example of mocking google API (currently only regions and zones).
The application is using techniques like scala, play2, squeryl, h2.

## 1. Pre-requisites
Scala: 2.11.7
Playframework: 2.4.x (with activator)

## 2. Compile and Run
Compile and run poc-google-mock-h2 through play-activator:
cd <Root folder of poc-google-mock-h2>
activator
Inside activator - Basic steps to run the application:
clean
compile
run

## 3. Edit by IDE (eclipse or intellij)
Inside project/plugins.sbt, sbt-eclipse and mpeltonen-idea have already been added to the plugins.
Generate Eclipse Project:
eclipse
Generate IntelliJ Project:
gen-idea

## 4. Database configuration
conf/application.conf

## 5. Sample request
http://<host:port>/compute/v1/projects/test-vim-120/regions
http://<host:port>/compute/v1/projects/test-vim-120/regions/asia-east1
http://<host:port>/compute/v1/projects/test-vim-120/zones
http://<host:port>/compute/v1/projects/test-vim-120/zones/asia-east1-a

