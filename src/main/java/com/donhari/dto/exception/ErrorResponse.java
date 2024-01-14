package com.donhari.dto.exception;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Serdeable
@Data
public class ErrorResponse {

    private String message;
    private Integer statusCode;

}
