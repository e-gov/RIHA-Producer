package ee.ria.riha.service;

/**
 * Preconfigured {@link MetaDataProvider}
 *
 * @author Valentin Suhnjov
 */
public class PreConfiguredMetaDataProvider implements MetaDataProvider {

    private String ownerCode;
    private String ownerName;

    public PreConfiguredMetaDataProvider(String ownerCode, String ownerName) {
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
    }

    @Override
    public String getOwnerCode() {
        return ownerCode;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

}
