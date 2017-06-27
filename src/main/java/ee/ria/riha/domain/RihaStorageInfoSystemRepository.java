package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.client.StorageClient;
import ee.ria.riha.storage.util.Filterable;
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

    private static final String MAIN_RESOURCE_PATH = "db/main_resource";
    private static final String NOT_IMPLEMENTED = "Not implemented";

    private final StorageClient storageClient;

    public RihaStorageInfoSystemRepository(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Override
    public List<Long> add(InfoSystem infoSystem) {
        return storageClient.create(MAIN_RESOURCE_PATH, infoSystem.getJsonObject().toString());
    }

    @Override
    public InfoSystem get(Long id) {
        String infoSystem = storageClient.get(MAIN_RESOURCE_PATH, id);
        return infoSystem != null && !infoSystem.isEmpty() ? new InfoSystem(infoSystem) : null;
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
        PagedResponse<InfoSystem> response = new PagedResponse<>(pageable);

        long totalElements = storageClient.count(MAIN_RESOURCE_PATH, filterable.getFilter());
        response.setTotalElements(totalElements);

        if (totalElements > 0) {
            List<String> descriptions = storageClient.find(MAIN_RESOURCE_PATH, pageable.getPageSize(),
                                                           pageable.getOffset(), filterable.getFilter(),
                                                           filterable.getSort(), filterable.getFields());
            response.setContent(descriptions.stream()
                                        .map(InfoSystem::new)
                                        .collect(Collectors.toList()));
        }

        return response;
    }
}
