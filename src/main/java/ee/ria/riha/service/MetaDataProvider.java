package ee.ria.riha.service;

/**
 * Provides various information about application and user. Intended to use when creating information systems.
 *
 * @author Valentin Suhnjov
 */
public interface MetaDataProvider {

    String getOwnerCode();

    String getOwnerName();
}
