# poc-google-mock-h2

An example of mocking google API (currently only regions and zones).</br>
The application is using techniques like scala, play2, squeryl, h2.

#### 1. Pre-requisites
Scala: 2.11.7</br>
Playframework: 2.4.x (with activator)</br>

#### 2. Compile and Run
Compile and run poc-google-mock-h2 through play-activator:</br>
[command] cd <Root folder of poc-google-mock-h2></br>
[command] activator</br>
Inside activator - Basic steps to run the application:</br>
[command] clean</br>
[command] compile</br>
[command] run

#### 3. Edit by IDE (eclipse or intellij)
Inside project/plugins.sbt, sbt-eclipse and mpeltonen-idea have already been added to the plugins.</br>
Generate Eclipse Project:</br>
[command] eclipse</br>
Generate IntelliJ Project:</br>
[command] gen-idea

#### 4. Database configuration
conf/application.conf

#### 5. Sample request
http://<host:port>/compute/v1/projects/test-vim-120/regions</br>
http://<host:port>/compute/v1/projects/test-vim-120/regions/asia-east1</br>
http://<host:port>/compute/v1/projects/test-vim-120/zones</br>
http://<host:port>/compute/v1/projects/test-vim-120/zones/asia-east1-a

