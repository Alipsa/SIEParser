package alipsa.sieparser;

/**
 * Base exception for all SIE parser errors.
 * Extends RuntimeException to allow use with standard functional interfaces.
 */
public class SieException extends RuntimeException {

    public SieException() {
        super();
    }

    public SieException(String message) {
        super(message);
    }

    public SieException(String message, Throwable cause) {
        super(message, cause);
    }

    public SieException(Throwable cause) {
        super(cause);
    }
}
