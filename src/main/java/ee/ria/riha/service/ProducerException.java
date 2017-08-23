package ee.ria.riha.service;

/**
 * @author Valentin Suhnjov
 */
public class ProducerException extends RuntimeException {

    public ProducerException() {
        super();
    }

    public ProducerException(String message) {
        super(message);
    }

    public ProducerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProducerException(Throwable cause) {
        super(cause);
    }

}
