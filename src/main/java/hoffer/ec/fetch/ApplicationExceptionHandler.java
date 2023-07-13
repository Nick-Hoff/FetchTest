package hoffer.ec.fetch;

import hoffer.ec.fetch.model.exception.ReceiptNotFoundException;
import hoffer.ec.fetch.model.exception.ReceiptProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Catches exceptions the service might encounter and provides more relevant error messages to the client.
 */
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "Requested Receipt Not Found")
    @ExceptionHandler(ReceiptNotFoundException.class)
    public void handleException(ReceiptNotFoundException e) {
        log.error("Client tried to access non-existent receipt id", e);
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Error processing receipt")
    @ExceptionHandler(ReceiptProcessingException.class)
    public void handleException(ReceiptProcessingException e) {
        log.error("Error processing client receipt", e);
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Error processing receipt")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException(HttpMessageNotReadableException e) {
        log.error("Client sent bad request", e);
    }

    /**
     *  Catch all exception handler. If some exception doesn't match a different path, it will get routed here and only
     *  show a generic 500 error to clients for security.
     */
    @ResponseStatus(
            value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = "Internal Server Error")
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("Unhandled exception: ", e);
    }
}