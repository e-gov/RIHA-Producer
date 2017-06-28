package ee.ria.riha.domain;

/**
 * Base class for {@link InfoSystemRepository} exceptions
 *
 * @author Valentin Suhnjov
 */
public class InfoSystemRepositoryException extends RuntimeException {

    public InfoSystemRepositoryException() {
        super();
    }

    public InfoSystemRepositoryException(String message) {
        super(message);
    }

    public InfoSystemRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoSystemRepositoryException(Throwable cause) {
        super(cause);
    }

    protected InfoSystemRepositoryException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
