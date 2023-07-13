# Receipt Processor Service

Nick Hoff   
7/12/23

## How to run

I've included a docker image `fetch_server.tar.gz`. You can download this file off of the repo and run it standalone.

Run it with the following commands:
``` 
gunzip fetch_server.tar.gz                   # Unzips the Docker image
docker image load -i fetch_server.tar        # Loads the Docker image
docker run -p8887:8080 fetch-server:latest   # Runs the server in a new container exposed on port 8887
```

It will spin up the Tomcat server and expose service on port 8887 (or the port of your choosing).

From there you can invoke the APIs via curl (or postman, etc) like so:
```
curl localhost:8887/receipts/cea636ac-cbd6-4af5-b9a6-71df337d511e/points
```

## Exposed API's:
* **POST** `/receipts/process`   
* **GET** `/receipts/{id}/points`

API spec defined in
https://github.com/fetch-rewards/receipt-processor-challenge

## Language Selection
Most of my experience is working in enterprise Java systems, so I've gone for a Java/Spring solution here.
* Java 17
* Spring Boot 
* Lombok for annotations