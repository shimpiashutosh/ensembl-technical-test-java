package uk.ac.ebi.ensembl.search.gene.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public class SearchGeneRestControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchGeneRestControllerAdvice.class);

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class})
    public ResponseEntity<String> handleBadRequest(final Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(buildErrorResponse("Bad request"));
    }


    @ExceptionHandler({SearchGeneException.class})
    public ResponseEntity<String> handleSearchGeneException(final SearchGeneException searchGExp) {
        LOGGER.error(searchGExp.getMessage(), searchGExp);
        return ResponseEntity
                .status(searchGExp.getHttpStatus())
                .contentType(APPLICATION_JSON)
                .body(buildErrorResponse(searchGExp.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(buildErrorResponse("Internal Server Error"));
    }

    private String buildErrorResponse(final String errorMessage) {
        return String.format("{\"message\": \"%s\"}", errorMessage);
    }
}
