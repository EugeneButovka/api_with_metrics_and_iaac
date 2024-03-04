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


### Monitoring endpoints for local
* General health: http://localhost:8080/actuator
* Prometheus scraping endpoint: http://localhost:8080/actuator/prometheus
    - custom metric:
      ```
      # HELP user_flow_exceptions_total Counts user flow exceptions exception occurrences
      # TYPE user_flow_exceptions_total counter
      user_flow_exceptions_total{application="user-flow-api-service",exception="UserFlowInternalException",} 1.0
      ```

### Misc
