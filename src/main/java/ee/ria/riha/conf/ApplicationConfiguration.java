package ee.ria.riha.conf;

import ee.ria.riha.domain.FileInfoSystemRepository;
import ee.ria.riha.domain.InfoSystemRepository;
import ee.ria.riha.domain.RihaStorageInfoSystemRepository;
import ee.ria.riha.service.ContextAwareMetaDataProvider;
import ee.ria.riha.service.MetaDataProvider;
import ee.ria.riha.service.PreConfiguredMetaDataProvider;
import ee.ria.riha.storage.client.StorageClient;
import ee.ria.riha.storage.domain.MainResourceRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Valentin Suhnjov
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

    @Bean
    public InfoSystemRepository infoSystemRepository(ApplicationProperties applicationProperties) {
        if (applicationProperties.isSelfHosted()) {
            return new FileInfoSystemRepository();
        }

        MainResourceRepository mainResourceRepository = new MainResourceRepository(
                getStorageClient(applicationProperties));
        return new RihaStorageInfoSystemRepository(mainResourceRepository);
    }

    private StorageClient getStorageClient(ApplicationProperties applicationProperties) {
        RestTemplate restTemplate = new RestTemplate();
        return new StorageClient(restTemplate, applicationProperties.getStorageClient().getBaseUrl());
    }

    @Bean
    public MetaDataProvider metaDataProvider(ApplicationProperties applicationProperties) {
        if (applicationProperties.isSelfHosted()) {
            return new PreConfiguredMetaDataProvider(applicationProperties.getMeta().getOwnerCode(),
                                                     applicationProperties.getMeta().getOwnerName());
        }

        return new ContextAwareMetaDataProvider();
    }

}