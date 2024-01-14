package com.donhari.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;

@ConfigurationProperties(GithubConfig.PREFIX)
@Requires(property = GithubConfig.PREFIX)
public record GithubConfig(@Nullable String token) {

    public static final String PREFIX = "github";

}
