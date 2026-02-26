package alipsa.sieparser;

/**
 * Base exception for all SIE parser errors.
 * Extends RuntimeException to allow use with standard functional interfaces.
 */
public class SieException extends RuntimeException {

    /** Creates a new SieException with no message. */
    public SieException() {
        super();
    }

    /**
     * Creates a new SieException with the given message.
     * @param message the detail message
     */
    public SieException(String message) {
        super(message);
    }

    /**
     * Creates a new SieException with the given message and cause.
     * @param message the detail message
     * @param cause the underlying cause
     */
    public SieException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new SieException with the given cause.
     * @param cause the underlying cause
     */
    public SieException(Throwable cause) {
        super(cause);
    }
}
