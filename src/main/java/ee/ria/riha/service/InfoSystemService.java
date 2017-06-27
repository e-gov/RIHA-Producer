package ee.ria.riha.service;

import ee.ria.riha.domain.InfoSystemRepository;
import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.util.FilterRequest;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Valentin Suhnjov
 */
@Service
public class InfoSystemService {

    @Autowired
    private InfoSystemRepository infoSystemRepository;

    @Autowired
    private MetaDataProvider metaDataProvider;

    /**
     * Creates new {@link InfoSystem}. Newly created {@link InfoSystem} will be filled with owner information and
     * generated UUID.
     *
     * @param model new {@link InfoSystem} model
     * @return created {@link InfoSystem}
     */
    public InfoSystem create(InfoSystem model) {
        InfoSystem infoSystem = new InfoSystem(model.getJsonObject());

        infoSystem.setUuid(UUID.randomUUID());
        infoSystem.setOwnerCode(metaDataProvider.getOwnerCode());
        infoSystem.setOwnerName(metaDataProvider.getOwnerName());

        List<Long> ids = infoSystemRepository.add(infoSystem);
        return get(ids.get(0));
    }

    /**
     * Retrieves {@link InfoSystem} by its id
     *
     * @param id an id of {@link InfoSystem}
     * @return retrieved {@link InfoSystem}
     */
    public InfoSystem get(Long id) {
        return infoSystemRepository.get(id);
    }

    /**
     * Creates new record with the same UUID and owner. Other parts of {@link InfoSystem} are updated from model.
     *
     * @param id    an id of {@link InfoSystem}
     * @param model updated {@link InfoSystem} model
     * @return new {@link InfoSystem}
     */
    public InfoSystem update(Long id, InfoSystem model) {
        InfoSystem existingInfoSystem = get(id);

        InfoSystem updatedInfoSystem = new InfoSystem(model.getJsonObject());
        updatedInfoSystem.setUuid(existingInfoSystem.getUuid());
        updatedInfoSystem.setOwnerCode(existingInfoSystem.getOwnerCode());
        updatedInfoSystem.setOwnerName(existingInfoSystem.getOwnerName());

        List<Long> ids = infoSystemRepository.add(updatedInfoSystem);
        return get(ids.get(0));
    }

    /**
     * Retrieves list of paged and filtered {@link InfoSystem}s that belong to current owner.
     *
     * @param pageable   paging definition
     * @param filterable filter definition
     * @return {@link PagedResponse} containing search results
     */
    public PagedResponse<InfoSystem> list(Pageable pageable, Filterable filterable) {
        FilterRequest filter = addOwnerToFilter(filterable);
        return infoSystemRepository.list(pageable, filter);
    }

    private FilterRequest addOwnerToFilter(Filterable filterable) {
        String ownerFilter = "owner.code,jilike," + metaDataProvider.getOwnerCode();

        String filter = filterable.getFilter() != null
                ? filterable.getFilter() + "," + ownerFilter
                : ownerFilter;

        return new FilterRequest(filter, filterable.getSort(), filterable.getFields());
    }
}
