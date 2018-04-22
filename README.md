# IS4302-VoteChain
This is a project prepare for IS4302 by Tutorial 1 Group 4, voteChain.

## Prerequisites

Software Used:
* [Java SE JDK V8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)      - The Java Technology Used
* [NetBean IDE 8.2](https://netbeans.org/downloads)     - The Midware Used
* GlassFish 4.1.1     - The Server Used for deployment of the Netbean
* [MySQL](http://dev.mysql.com/downloads/mysql)               - The Common backend for Application
* [Vagrant](https://github.com/suenchunhui/fabric-tutorial-vagrant)		      - The Virtual Machine to run a Hyperledger Fabric
* Composer Playground - The blockchain Technology Used

Follow the order above for installation, `GlassFish` is together with the bundle download of NetBean.
For Java SE be load the version 8 as Java SE 9 is not compatible with NetBeans 8.2
For `Vagrant` after following the install guide,
Change the follow:
```
 config.vm.provision "shell", inline: $script
  config.vm.network :forwarded_port, guest: 8080, host: 8080  #composer
  config.vm.network :forwarded_port, guest: 8181, host: 8181  #cloud9-ide
  config.vm.network :forwarded_port, guest: 9090, host: 9090  #custom-ui
  config.vm.network :forwarded_port, guest: 3000, host: 3000  #marbles-ui
  config.vm.network :forwarded_port, guest: 3001, host: 3001  #marbles-ui
end
```
to
```
 config.vm.provision "shell", inline: $script
  config.vm.network :forwarded_port, guest: 8080, host: 7070  #composer Relink it to port 7070 from 8080
  config.vm.network :forwarded_port, guest: 8181, host: 7171  #cloud9-ide
  config.vm.network :forwarded_port, guest: 9090, host: 9090  #custom-ui
  config.vm.network :forwarded_port, guest: 3000, host: 3000  #Composer Rest Server EDS
  config.vm.network :forwarded_port, guest: 3001, host: 3001  #Composer Rest Server VoterHub
end
```

## Usage

## Common Bug