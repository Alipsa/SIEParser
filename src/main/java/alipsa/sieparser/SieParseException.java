package alipsa.sieparser;

/**
 * Thrown when an error occurs during parsing of a SIE file.
 */
public class SieParseException extends SieException {

    /** Creates a new SieParseException with no message. */
    public SieParseException() {
        super();
    }

    /**
     * Creates a new SieParseException with the given message.
     * @param message the detail message
     */
    public SieParseException(String message) {
        super(message);
    }

    /**
     * Creates a new SieParseException with the given message and cause.
     * @param message the detail message
     * @param cause the underlying cause
     */
    public SieParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new SieParseException with the given cause.
     * @param cause the underlying cause
     */
    public SieParseException(Throwable cause) {
        super(cause);
    }
}
