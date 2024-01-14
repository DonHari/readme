package com.donhari.mapper.readme;

import com.donhari.dto.readme.WordOccurrence;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

@Singleton
public class WordOccurrenceMapper {

    public List<WordOccurrence> toDto(Map<String, Long> map) {
        return map.entrySet().stream().map(entry -> new WordOccurrence(entry.getKey(), entry.getValue())).toList();
    }

}
