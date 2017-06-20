package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;

import java.util.List;

/**
 * Repository for InfoSystem persistence using file system.
 *
 * @author Valentin Suhnjov
 */
public class FileInfoSystemRepository implements InfoSystemRepository {

    @Override
    public List<Long> add(InfoSystem infoSystem) {
        throw new InfoSystemRepositoryException("Not implemented");
    }

    @Override
    public InfoSystem get(Long id) {
        throw new InfoSystemRepositoryException("Not implemented");
    }

    @Override
    public void update(Long id, InfoSystem infoSystem) {
        throw new InfoSystemRepositoryException("Not implemented");
    }

    @Override
    public void remove(Long id) {
        throw new InfoSystemRepositoryException("Not implemented");
    }
}
