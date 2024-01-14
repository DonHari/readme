package com.donhari.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
@Data
public class GithubRepo {

    private String name;

    @JsonProperty("private")
    private Boolean isPrivate;

}
