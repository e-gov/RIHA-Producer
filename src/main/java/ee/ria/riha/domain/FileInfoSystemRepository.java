package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;

import java.util.List;

/**
 * Repository for InfoSystem persistence using file system.
 *
 * @author Valentin Suhnjov
 */
public class FileInfoSystemRepository implements InfoSystemRepository {

    private static final String NOT_IMPLEMENTED = "Not implemented";

    @Override
    public InfoSystem add(InfoSystem infoSystem) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }

    @Override
    public InfoSystem load(String shortName) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }

    @Override
    public void update(String shortName, InfoSystem infoSystem) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }

    @Override
    public void remove(String shortName) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }

    @Override
    public PagedResponse<InfoSystem> list(Pageable pageable, Filterable filterable) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }

    @Override
    public List<InfoSystem> find(Filterable filterable) {
        throw new InfoSystemRepositoryException(NOT_IMPLEMENTED);
    }
}
