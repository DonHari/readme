package com.donhari.service;

import com.donhari.client.GithubClient;
import com.donhari.dto.github.GithubRepo;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class GithubIntegrationService {

    private final GithubClient githubClient;

    public List<GithubRepo> getRepos(String organization) {
        List<GithubRepo> repos = (List<GithubRepo>) Mono.from(githubClient.getAllRepos(organization))
                .onErrorReturn(HttpResponse.notFound(List.of()))
                .filter(this::isOk)
                .map(response -> {
                    Optional<List<GithubRepo>> reposOptional = response.getBody();
                    if (reposOptional.isPresent()) {
                        return reposOptional.get();
                    }
                    return List.of();
                })
                .block();
        return repos == null ? List.of() : repos;
    }

    public String getReadmeContentFromRepo(String organization, GithubRepo repo) {
        Mono<HttpResponse<String>> contentMono = Mono.from(githubClient.getReadmeContent(organization, repo.getName()))
                .onErrorReturn(HttpResponse.notFound(""));
        HttpResponse<String> response = contentMono.block();
        if (isOk(response)) {
            Optional<String> contentOptional = response.getBody();
            if (contentOptional.isPresent()) {
                return contentOptional.get();
            }
        }
        return "";
    }

    private boolean isOk(HttpResponse<?> response) {
        return response != null && response.getStatus()
                .equals(HttpStatus.OK);
    }

}
