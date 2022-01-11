# DuchnetServer
### A web service for metadata and peer storage
DuchnetServer is a web service designed to be used together with Duchnet nodes. While it's necessary to have multiple Duchnet nodes opened, there must be only one DuchnetServer active at one time.  
DuchnetServer has been implemented as part of the Distributed Computing subject at the Universitat de Lleida by Joel Aumedes and Pau Escolà.
## How to run the server
The server stores its data in a postgres database. This database must be running before running the application.
> Note: Some of the required docker commands might require sudo access.
### First time setup
This must be done only for the first time running the server.  
First, create the docker container using:  
```$ docker run -p 5432:5432 -d -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=root postgres```  
This will download and start a new postgres database with _postgres_ as the user and _root_ as the password.  
Then, use ```$ docker ps``` to find the Container ID of this newly created container, and use it in this command:  
```docker exec -it <Container ID> psql --username=postgres```
This will open the PostgreSQL tool in the container. Now, create a database called _duchnet_ in the container using:  
```# CREATE DATABASE duchnet;```
You can check that it has been created successfully using ```\l```. Now you can exit the PostgreSQL tool and start the server.
### Rebuilding
If the container has been created using the First time setup before, it doesn't need to be created again. Using this command will restart it:  
```$ docker start <Container ID>```
Start the application again and your DuchnetServer is up and running.