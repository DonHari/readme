package com.donhari.exception;

import com.donhari.dto.exception.ErrorResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Produces
@Singleton
@Requires(classes = {Exception.class, ExceptionHandler.class})
@Slf4j
public class CustomExceptionHandler implements ExceptionHandler<Exception, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, Exception exception) {
        log.error("Some exception occurred ", exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Something went wrong");
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        return HttpResponse.serverError(errorResponse).status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
