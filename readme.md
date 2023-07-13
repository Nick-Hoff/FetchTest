# Receipt Processor Service

Nick Hoff   
7/12/23

## How to run

I made a docker image. The Java 11 docker base image is pretty large, so I can't actually upload the Docker image
directly to the GitHub repo for size constraints. I've hosted it instead on Dropbox at:
https://www.dropbox.com/s/593bfwwjaj4az4x/fetch_server.tar.gz?dl=0

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