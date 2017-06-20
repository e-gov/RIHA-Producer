package ee.ria.riha.web;

public class BadRequest extends RuntimeException {
    public BadRequest() {
        super("");
    }

    public BadRequest(String message) {
        super(message);
    }
}
