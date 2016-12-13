package ee.ria.riha.controllers;

public class BadRequest extends RuntimeException {
  public BadRequest() {
    super("");
  }

  public BadRequest(String message) {
    super(message);
  }
}
