package com.donhari.filter;

import com.donhari.config.GithubConfig;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import lombok.RequiredArgsConstructor;

@ClientFilter({"/orgs/**/repos", "/repos/**/**/contents/README.md"})
@Requires(property = GithubConfig.PREFIX + ".token")
@RequiredArgsConstructor
public class GithubFilter {

    private final GithubConfig githubConfig;

    @RequestFilter
    public void doFilter(MutableHttpRequest<?> request) {
        String token = githubConfig.token();
        if (token == null || token.isBlank()) {
            return;
        }
        request.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}
