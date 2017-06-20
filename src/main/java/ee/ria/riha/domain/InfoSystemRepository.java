package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;

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

}
