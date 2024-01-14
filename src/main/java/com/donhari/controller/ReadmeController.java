package com.donhari.controller;

import com.donhari.dto.readme.WordOccurrence;
import com.donhari.mapper.readme.WordOccurrenceMapper;
import com.donhari.service.ReadmeService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@Controller("/readme")
@AllArgsConstructor
@ExecuteOn(TaskExecutors.BLOCKING)
public class ReadmeController {

    private final ReadmeService readmeService;
    private final WordOccurrenceMapper wordOccurrenceMapper;

    @Get
    public HttpResponse<?> getMostPopularWords(@QueryValue String organization) {
        Map<String, Long> wordOccurrenceMap = readmeService.getMostPopularWords(organization);
        List<WordOccurrence> wordOccurrences = wordOccurrenceMapper.toDto(wordOccurrenceMap);
        return HttpResponse.status(HttpStatus.OK)
                .body(wordOccurrences);
    }

}
