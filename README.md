# Restful Assessment

Run with docker

```
git clone https://github.com/s4kibs4mi/restful_assessment.git
./gradlew distDocker
docker run ninja.sakib.restful_assessment:$version
```

Run with jar

Install java in your system
[Download JAR from here](https://github.com/s4kibs4mi/restful_assessment/releases/download/1.3/restful_assessment-1.3.jar)

then execute 

```
java -jar downloadfile.jar
```

## Documentation

Application port docker `8080` and JAR `4567`

BASE_URL : `host_name:$port`

1. Get Values : 

GET /values
```json
{
    "key1": "value1",
    "key2": "value2"
    .....
}
```
or

GET /values?keys=key1,key3
```json
{
    "key1": "value1",
    "key3": "value3"
    .....
}
```

2. Add Values

POST /values

Request,
```json
{
    "key1": "value1",
    "key2": "value2"
}
```

Response,
```
{
    "code": 200
}
```
or
```
{
    "code": 201
}
```

Note : Redis used for data caching. In get request result will be cached for 10seconds.
