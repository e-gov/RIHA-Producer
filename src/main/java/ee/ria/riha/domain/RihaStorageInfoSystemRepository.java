package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.domain.MainResourceRepository;
import ee.ria.riha.storage.domain.model.MainResource;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.PageRequest;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Repository for InfoSystem entity persistence using RIHA-Storage.
 *
 * @author Valentin Suhnjov
 */
public class RihaStorageInfoSystemRepository implements InfoSystemRepository {

    private static final String NOT_IMPLEMENTED = "Not implemented";

    private static final Function<MainResource, InfoSystem> MAIN_RESOURCE_TO_INFO_SYSTEM = mainResource -> {
        if (mainResource == null) {
            return null;
        }
        return new InfoSystem(mainResource.getJson_context());
    };

    private static final Function<InfoSystem, MainResource> INFO_SYSTEM_TO_MAIN_RESOURCE = infoSystem -> {
        if (infoSystem == null) {
            return null;
        }
        return new MainResource(infoSystem.getJsonObject().toString());
    };

    private final MainResourceRepository mainResourceRepository;

    public RihaStorageInfoSystemRepository(MainResourceRepository mainResourceRepository) {
        this.mainResourceRepository = mainResourceRepository;
    }

    @Override
    public List<Long> add(InfoSystem infoSystem) {
        return mainResourceRepository.add(INFO_SYSTEM_TO_MAIN_RESOURCE.apply(infoSystem));
    }

    @Override
    public InfoSystem get(Long id) {
        MainResource mainResource = mainResourceRepository.get(id);
        return MAIN_RESOURCE_TO_INFO_SYSTEM.apply(mainResource);
    }

    @Override
    public void update(Long id, InfoSystem infoSystem) {
        throw new RuntimeException(NOT_IMPLEMENTED);
    }

    @Override
    public void remove(Long id) {
        throw new RuntimeException(NOT_IMPLEMENTED);
    }

    @Override
    public PagedResponse<InfoSystem> list(Pageable pageable, Filterable filterable) {
        PagedResponse<MainResource> mainResourcePagedResponse = mainResourceRepository.list(pageable, filterable);

        return new PagedResponse<>(
                new PageRequest(mainResourcePagedResponse.getPage(), mainResourcePagedResponse.getSize()),
                mainResourcePagedResponse.getTotalElements(),
                mainResourcePagedResponse.getContent().stream()
                        .map(MAIN_RESOURCE_TO_INFO_SYSTEM)
                        .collect(Collectors.toList()));
    }
}
