package ee.ria.riha.service;

import ee.ria.riha.domain.InfoSystemRepository;
import ee.ria.riha.domain.model.InfoSystem;
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

    public InfoSystem create(InfoSystem model) {
        InfoSystem infoSystem = new InfoSystem(model.getJsonObject());

        infoSystem.setUuid(UUID.randomUUID());
        infoSystem.setOwnerCode(metaDataProvider.getOwnerCode());
        infoSystem.setOwnerName(metaDataProvider.getOwnerName());

        List<Long> ids = infoSystemRepository.add(infoSystem);
        return get(ids.get(0));
    }

    public InfoSystem get(Long id) {
        return infoSystemRepository.get(id);
    }

    public InfoSystem update(Long id, InfoSystem model) {
        InfoSystem existingInfoSystem = get(id);

        InfoSystem updatedInfoSystem = new InfoSystem(model.getJsonObject());
        updatedInfoSystem.setUuid(existingInfoSystem.getUuid());
        updatedInfoSystem.setOwnerCode(existingInfoSystem.getOwnerCode());
        updatedInfoSystem.setOwnerName(existingInfoSystem.getOwnerName());

        List<Long> ids = infoSystemRepository.add(updatedInfoSystem);
        return get(ids.get(0));
    }
}
