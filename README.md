### High level architecture reveiw
See api_with_metrics_and_iaac_system_arch.pdf in the root of the project

### Local run instructions

#### Run

Build the project
```bash
./gradlew build
```

Run the project
```bash
./gradlew bootRun
```

Test the project
```bash
./gradlew test
```

Generate openapi docs (will generate to build/openapi.json)
```bash
./gradlew clean generateOpenApiDocs
```

### API documentation
See 'Generate openapi docs' or swagger UI after running app locally at http://localhost:8080/swagger-ui.html


### Load tests
Run the app and use command line from the root of the project:
```
jmeter -n -t Api_load_tests_jmeter.jmx
```

you should be able to see summary in console, for example
```
summary =    500 in 00:00:10 =   47.7/s Avg:     3 Min:     2 Max:   103 Err:     0 (0.00%)
```

remove -n parameter in the command to use JMeter GUI.

### Monitoring endpoints for local
* General health: http://localhost:8080/actuator
* Prometheus scraping endpoint: http://localhost:8080/actuator/prometheus
    - custom metric:
      ```
      # HELP user_flow_exceptions_total Counts user flow exceptions exception occurrences
      # TYPE user_flow_exceptions_total counter
      user_flow_exceptions_total{application="user-flow-api-service",exception="UserFlowInternalException",} 1.0
      ```

### Local docker build and run
To build a docker image with the app (after it is build by gradle) use commands from the root of the project:
```
docker rmi api_with_metrics_and_iaac:latest
docker build --tag api_with_metrics_and_iaac:latest .
```

To run docker image with the app mounted to http://localhost:8080 use commands:
```
docker stop api_with_metrics_and_iaac-container
docker rm api_with_metrics_and_iaac-container
docker run -d -p 8080:8080 --name api_with_metrics_and_iaac-container api_with_metrics_and_iaac:latest 
```

### Infrastructure deploy
1. Add env variables for AWS credentials
```
% export AWS_ACCESS_KEY_ID="anaccesskey"
% export AWS_SECRET_ACCESS_KEY="asecretkey"
```
2. Go to **iaac/eks_cluster** folder
3. To download terraform modules use console command
```
terraform init
```
4. To plan deployemnt use 
```
terraform plan
```
5. To deploy resuources use
```
terraform apply
```
