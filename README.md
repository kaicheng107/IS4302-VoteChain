# IS4302-VoteChain
This is a project prepare for IS4302 by Tutorial 1 Group 4, voteChain.

## Prerequisites

Software Used:
* [Java SE JDK V8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)      - The Java Technology Used
* [NetBean IDE 8.2](https://netbeans.org/downloads)     - The Midware Used
* GlassFish 4.1.1     - The Server Used for deployment of the Netbean
* [Primefaces 6.1](https://www.primefaces.org/downloads/)      - The front-end add-on for interfaces
* [MySQL 5.7](http://dev.mysql.com/downloads/mysql)               - The Common backend for Application
* [MySQL Connector/J](	http://dev.mysql.com/downloads/connector/j)   - A connector for MySQL to NetBean

* [Vagrant](https://github.com/suenchunhui/fabric-tutorial-vagrant)		      - The Virtual Machine to run a Hyperledger Fabric
* Composer Playground - The blockchain Technology Used

## Installing

Follow the order above for installation, `GlassFish` is together with the bundle download of NetBeans.
1. For Java SE, download version 8 as Java SE 9 is not compatible with NetBeans 8.2
2. 'MySQL Connector' does not require installation. Just extract and copy 'mysql-connector-java-5.1.45-bin.jar' into these two folders: 1) NetBeans installation folder - 
```
"C:\Program Files\NetBeans 8.2\ide\modules\ext";
```
and 2) GlassFish installation folder - 
```
"C:\glassfish-4.1.1\glassfish\domains\domain1\lib"
```
Your installation folders might differ from mine. To use this JAR file in NetBeans, replace the existing driver JAR file in NetBeans under "Services > Databases > Drivers > MySQL (Connector/J driver) > Customize".

3. For `Vagrant` after following the install guide,
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

This is a guided basic usage of how to run the program:

1. After doing the installation `vagrant halt` and `vagrant up` again to make the changes updated in `vagrant`, the browser-based IDE will be available at [http://localhost:7171](http://localhost:7171)
2. Upon login to `vagrant`, drag and drop 'voting-network.bna' to composer-playground folder
3. After which cd to ~/composer-playground and run `./playground.sh`
4. After which 
```
sudo cp voting-network.bna ./mnt
```
to copy the 'voting-network.bna' from mnt folder to deploy it.
5. To create NetworkAdmin credential for the playground. The command will enrol the root registra inside the fabric-ca container itself, using the default password of fabric-ca:
```
docker exec -it ca.org1.example.com fabric-ca-client enroll -M registrar -u http://admin:adminpw@localhost:7054
```
Then use this command to user the registrar credentials to register a new user (to be used as NetworkAdmin for composer-playground)
```
docker exec -it ca.org1.example.com fabric-ca-client register -M registrar -u http://localhost:7054 --id.name <user_id> --id.affiliation org1 --id.attrs '"hf.Registrar.Roles=client"' --id.type user
```
for mine case I would use `admin1` as the `<user_id>`. Please take note, a `<admin enrol password>` would be generated.
6. Open a new `terminal` and enter the CLI container using:
```
docker exec -it cli bash
```
7. Using the password for the newly register admin, deploy the network BNA using:
```
composer network deploy --archiveFile /mnt/voting-network.bna -A <admin card> -c PeerAdmin@hlfv1 -S <admin enrol password>
```
For the `<admin card>` use the admin name that you have chosen, in my case is `admin1`. Overwrite `<admin enrol password>` with the password that was generated in step 5.
8. After which we will need to import the business network card that was generated using:
```
composer card import -f <card file name>
```
So in my case it will be:
```
composer card import -f admin1@voting-network.card
```
9. After which started an admin Rest Server so that the `Netbeans` can do a data initialization for `MySQL` and `Composer-playground`, the command is as follow:
```
composer-rest-server -c <admin card>
```
So in my case will be:
```
composer-rest-server -c admin1@voting-network
```
### From this point onward it is slightly more complex as we are switching between software!
1. Open up `Netbeans 8.2`  after which open project of the `votingNetwork` `NetBean` file and open all the file, you will see in total 5 files being open, 3 Web application file(a global like icon) 1 ejb (a bean like icon) and 1 Enterprise Application (a triangle like icon)
2. Once the folder is open you will mostly recevie a few error message.
* Missing Libraries for the web application common missing libraries are `PrimeFaces 6.1` and the `theme jar` missing, for `Primefaces 6.1` you can go download it at [here](https://www.primefaces.org/downloads/), where you can download `primefaces-6.1.jar` and add it to the libraries. After download, it is recommended to store the file at this location `~\NetBeans 8.2\enterprise\modules\ext\primefaces` where all the libraries for primefaces are. After which go back to `NetBeans`, go to the Tools>Libraries and open up the Libraries, after which click on `New Library...` and create a new Library for primefaces 6.1, recommended naming convention is `PrimeFaces 6.2`, after which add the 'Jar file' that you have just downloaded. Finally, go back to the web application that has the missing `PrimeFaces 6.1`, right click `properties>Libraries` and remove the broken library and add the newly created library. Do it for all of it.
* For the missing 'theme jar', same as `PrimeFaces 6.1`, right click `properties>Libraries`, remove the broken library and click on the button at the side `Add JAR/folder`. Go the gitfile that you had downloaded in the votingNetwork folder, there is a copy of the theme used, apply it to all of the Web Application that is missing it.
* The `ejb` module will still be red, because of the missing JDBC resource from the database, go to the Services>Databases, right click on MySQL Server and click on `Create database`, in the new Database name section enter `votingnetwork`, after which click on 'Grant Full Access' to `mysql.sys@localhost`
* One last common error that will occur is that the server resource password in the ejb is not your password for the databases, go to the projects tab>votingNetwork-ejb>server Resources>glassfish-resources.xml and change the password value to your password for MySQL databases.
3. After cleaning all the problem you can right click on votingNetwork (The triangle icon) to click on deploy. After the deployment is done, `MySQL` and `composer-playground` will have data being generated.
4. `Switch back to the Cloud9 IDE`, we can now stop the Composer Rest Server by `Ctr-c`.
5. After the data are generated, we can now create the participant Card and `composer rest server` for each of them
6. To issue an identity, open a `new terminal` and using the above `point 6` method. After which run the following command:
This is the grammar for the command
```
composer identity issue –c <admin card> -u <username to be issued> -a <participant to be attached to>
```
so an example for the voterHub will be :
```
composer identity issue -c admin1@voting-network -u voterHub -a org.acme.voting.VoterHub#voterHub
```
Do replace the admin card with your own admin card that you have created.
After which we will have to import the business card to the network:
```
composer card import -f voterHub@voting-network.card
```
The general grammar is at the above `point 8`.
7. Similar to point 6 we will have to generate an identity for the EDS. The command is as follow:
```
composer identity issue -c admin1@voting-network -u EDS -a org.acme.voting.EDS#1
```
we have to import the card too:
```
composer card import -f EDS@voting-network.card
```
Please edit according to your admin Card details
8. Finally you can start the `composer rest server` for the `VoterHub` and `EDS`. The command is as follow:
For EDS:
```
composer-rest-server -c EDS@voting-network
```
For VoterHub:
```
composer-rest-server -c voterHub@voting-network -p 3001
```
Please start the composer rest server at different `terminal` so that both of them can run.
Please follow the port strictly as it is mapped to `Netbeans` as followed, else there is a chance that it will not work.
This is the grammar for the localhost is as follow:
```
netbeanLocalHost/war Name
```
The intend web application URL is
```
http://localhost:8080/votingNetwork-war
http://localhost:8080/edsNetwork/
http://localhost:8080/adminNetwork
```
Now everything is set up, Have Fun!

## Common Bug
* These are some possible bugs that may occur when using `Netbeana 8.2` as glassfish is buggy
```
javax.servlet.ServletException: org.glassfish.jersey.server.ContainerException: java.lang.NoClassDefFoundError: com/fasterxml/jackson/module/jaxb/JaxbAnnotationIntrospector
```
This is a post to fixed the bug
```
https://stackoverflow.com/questions/31275480/glassfish-4-rest-xml-working-json-error/31276156
```
To download the file you can go to
```
http://book2s.com/java/jar/download-jar-file-j.html
```

* Some other bugs are
```
java.lang.NoClassDefFoundError: Could not initialize class org.eclipse.persistence.jaxb.BeanValidationHelper
```
This post will help to fixed the bug
```
https://www.ivonet.nl/2016/09/06/Could%20not%20initialize%20class%20org.eclipse.persistence.jaxb.BeanValidationHelper/
```
