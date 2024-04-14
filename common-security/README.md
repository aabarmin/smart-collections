# Common Security

Please add the following to the `application.yml` to enable login via Google: 

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: google-client-id
            client-secret: google-client-secret
```