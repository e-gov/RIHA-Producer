package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;

import java.util.List;

/**
 * Interface for repositories that persist InfoSystem entities.
 *
 * @author Valentin Suhnjov
 */
public interface InfoSystemRepository {

    List<Long> add(InfoSystem infoSystem);

    InfoSystem get(Long id);

    void update(Long id, InfoSystem infoSystem);

    void remove(Long id);

    PagedResponse<InfoSystem> list(Pageable pageable, Filterable filterable);
}
