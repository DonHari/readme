### Application to find most common words in all user's public repositories.

### To run application execute commands below:

```
./gradlew dockerBuild
docker run -d --rm -p 8080:8080 --privileged readme:latest
```

### Working with application

Send request to `localhost:8080/readme?organization=<ORG_NAME>`
You can try it with `spotify` public repositories.

### Troubleshooting

By default, application runs without GitHub token, so it might be blocked due to api rate limit. To bypass this
limitation you can create you personal token [here](https://github.com/settings/tokens) and set property `github.token`
in `application.properties` file. Rebuild container and rerun image after this.


