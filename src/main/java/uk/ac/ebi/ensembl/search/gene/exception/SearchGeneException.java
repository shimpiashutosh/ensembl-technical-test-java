package uk.ac.ebi.ensembl.search.gene.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class SearchGeneException extends RuntimeException {

    private final HttpStatus httpStatus;

    public SearchGeneException(final String message,
                               final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static SearchGeneException dataNotFound() {
        return new SearchGeneException("Data not found", NOT_FOUND);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
