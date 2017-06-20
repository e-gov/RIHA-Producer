package ee.ria.riha.service;

/**
 * Provides meta data about InfoSystem that belong to current domain.
 *
 * @author Valentin Suhnjov
 */
public interface MetaDataProvider {

    String getOwnerCode();

    String getOwnerName();
}
