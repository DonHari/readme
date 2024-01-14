package com.donhari.client;

import com.donhari.dto.github.GithubRepo;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import java.util.List;

@Client(id = "github")
@Header(name = HttpHeaders.USER_AGENT, value = "DonHari's HTTP client")
public interface GithubClient {

    @Get("/orgs/{organization}/repos")
    @Header(name = HttpHeaders.ACCEPT, value = "application/vnd.github.v3+json, application/json")
    @SingleResult
    Publisher<HttpResponse<List<GithubRepo>>> getAllRepos(String organization);

    @Get("/repos/{organization}/{repository}/contents/README.md")
    @Header(name = HttpHeaders.ACCEPT, value = "application/vnd.github.raw")
    @SingleResult
    Publisher<HttpResponse<String>> getReadmeContent(String organization, String repository);

}
