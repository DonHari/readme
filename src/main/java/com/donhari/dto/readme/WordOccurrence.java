package com.donhari.dto.readme;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Serdeable
public class WordOccurrence {

    private String word;
    private Long occurrence;

}
