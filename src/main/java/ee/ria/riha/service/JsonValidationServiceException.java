package ee.ria.riha.service;

import com.github.fge.jsonschema.core.report.ProcessingReport;

/**
 * Runtime exception indicating that validation service caught unrecoverable exception.
 *
 * @author Valentin Suhnjov
 */
public class JsonValidationServiceException extends RuntimeException {

    public JsonValidationServiceException(String message, Exception e) {
        super(message, e);
    }
}
