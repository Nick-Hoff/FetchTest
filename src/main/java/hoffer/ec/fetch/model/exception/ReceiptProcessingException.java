package hoffer.ec.fetch.model.exception;

/**
 * Thrown when a client tries to upload a receipt which fails to process when we're tallying up the points.
 */
public class ReceiptProcessingException extends RuntimeException {
    public ReceiptProcessingException(String s) {
        super(s);
    }
    public ReceiptProcessingException(String s, Exception e) {
        super(s, e);
    }
}
