# Flight Plan

## Prerequisites
- Java 8
- Maven

## How to run the applications

### flight-plan-core (command line tool)

Build the `flight-plan-core` application using the Maven command line tool:
```shell
cd flight-plan-core
mvn clean package
```  

Run the `JAR` file created inside `target` directory with the routes CSV file as a parameter:
```shell
java -jar target/flight-plan-core-1.0-SNAPSHOT.jar /path/to/routes.csv
```
Follow the screen instructions to get the best route for your trip and have a nice vacation!

### fligh-plan-api (REST API)

Build and install the `flight-plan-core` application (if you haven't done it yet):
```shell
cd flight-plan-core
mvn clean install
```

Start the `flight-plan-api` service with `SpringBoot` Maven plugin:
```shell
cd flight-plan-api
mvn spring-boot:run
```

If nothing gets wrong, the service will run in `8080` port.

#### Get the best route
Call a `GET` call at `http://localhost:8080/v1/api/routes/best` with:  
- **origin:** The origin
- **destination:** The destination

##### Example
**cURL command**
```shell
curl -X GET \
  'http://localhost:8080/v1/api/routes/best?origin=GRU&destination=CDG'
```
**Expected response**
```javascript
{
    "route": "GRU >> BRC >> SCL >> ORL >> CDG",
    "price": 40
}
```

#### Create a new route
Call a `POST` call at `http://localhost:8080/v1/api/routes` with a JSON body:
- **origin:** The origin (String)
- **destination:** The destination (String)
- **price:** The price (Number)

#### Example
**cURL command**
```shell
curl -X POST \
  http://localhost:8080/v1/api/routes \
  -H 'Content-Type: application/json' \
  -d '{
    "origin": "ORI",
    "destination": "DES",
    "price": 10.0
}'
```
**Expected response**
`200`
