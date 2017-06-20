package ee.ria.riha.service;

/**
 * {@link MetaDataProvider} that is aware of application context.
 *
 * @author Valentin Suhnjov
 */
public class ContextAwareMetaDataProvider implements MetaDataProvider {

    @Override
    public String getOwnerCode() {
        return "1234567890";
    }

    @Override
    public String getOwnerName() {
        return "anonymous";
    }

}
