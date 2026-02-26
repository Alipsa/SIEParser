package alipsa.sieparser;

/**
 * Thrown when an error occurs during parsing of a SIE file.
 */
public class SieParseException extends SieException {

    public SieParseException() {
        super();
    }

    public SieParseException(String message) {
        super(message);
    }

    public SieParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SieParseException(Throwable cause) {
        super(cause);
    }
}
