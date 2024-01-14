package com.donhari.service;

import com.donhari.dto.github.GithubRepo;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
@AllArgsConstructor
public class ReadmeService {

    private final GithubIntegrationService githubIntegrationService;

    public Map<String, Long> getMostPopularWords(String organization) {
        List<GithubRepo> repos = githubIntegrationService.getRepos(organization);
        Map<String, Long> words = getMostPopularWords(organization, repos);
        return sortMap(words);
    }

    private Map<String, Long> getMostPopularWords(String organization, List<GithubRepo> repos) {
        AtomicReference<Map<String, Long>> wordOccurrence = new AtomicReference<>(new LinkedHashMap<>());
        repos.forEach(repo -> {
            String content = githubIntegrationService.getReadmeContentFromRepo(organization, repo);
            if (content == null || content.isBlank()) {
                return;
            }
            Map<String, Long> wordOccurrenceInContent = Arrays.stream(cleanUpContent(content).split("\\W+"))
                    .filter(str -> str != null && !str.isBlank() && str.length() >= 4)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            wordOccurrence.set(mergeMaps(wordOccurrence.get(), wordOccurrenceInContent));
        });
        return wordOccurrence.get();
    }

    private String cleanUpContent(String content) {
        return content.replace("\\n", "\n");
    }

    private Map<String, Long> mergeMaps(Map<String, Long> first, Map<String, Long> second) {
        return Stream.concat(first.entrySet()
                        .stream(), second.entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }

    private Map<String, Long> sortMap(Map<String, Long> map) {
        List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.<String, Long>comparingByValue()
                .reversed());
        list = list.size() > 3 ? list.subList(0, 3) : list;
        return list.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
