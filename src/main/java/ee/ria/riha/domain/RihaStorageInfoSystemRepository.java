package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.storage.client.StorageClient;

import java.util.List;

/**
 * Repository for InfoSystem entity persistence using RIHA-Storage.
 *
 * @author Valentin Suhnjov
 */
public class RihaStorageInfoSystemRepository implements InfoSystemRepository {

    private static final String MAIN_RESOURCE_PATH = "db/main_resource";

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
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void remove(Long id) {
        throw new RuntimeException("Not implemented");
    }
}
