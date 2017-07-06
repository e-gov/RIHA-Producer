package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.domain.MainResourceRepository;
import ee.ria.riha.storage.domain.model.MainResource;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.PageRequest;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for InfoSystem entity persistence using RIHA-Storage.
 *
 * @author Valentin Suhnjov
 */
public class RihaStorageInfoSystemRepository implements InfoSystemRepository {

    private static final String NOT_IMPLEMENTED = "Not implemented";

    private final MainResourceRepository mainResourceRepository;

    public RihaStorageInfoSystemRepository(MainResourceRepository mainResourceRepository) {
        this.mainResourceRepository = mainResourceRepository;
    }

    @Override
    public List<Long> add(InfoSystem infoSystem) {
        return mainResourceRepository.add(new MainResource(infoSystem.getJsonObject()));
    }

    @Override
    public InfoSystem get(Long id) {
        MainResource mainResource = mainResourceRepository.get(id);
        return mainResource != null ? new InfoSystem(mainResource.getJsonObject()) : null;
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
                        .map(mr -> new InfoSystem(mr.getJsonObject()))
                        .collect(Collectors.toList()));
    }
}
