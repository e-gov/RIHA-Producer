package ee.ria.riha.conf;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Valentin Suhnjov
 */
@Configuration
@ConfigurationProperties(prefix = "producer")
public class ApplicationProperties {

    private final Meta meta = new Meta();
    private final StorageClient storageClient = new StorageClient();
    private boolean selfHosted = true;

    public Meta getMeta() {
        return meta;
    }

    public StorageClient getStorageClient() {
        return storageClient;
    }

    public boolean isSelfHosted() {
        return selfHosted;
    }

    public void setSelfHosted(boolean selfHosted) {
        this.selfHosted = selfHosted;
    }

    public static class Meta {

        private String ownerCode;
        private String ownerName;

        public String getOwnerCode() {
            return ownerCode;
        }

        public void setOwnerCode(String ownerCode) {
            this.ownerCode = ownerCode;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
    }

    public static class StorageClient {

        @NotEmpty
        private String baseUrl;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}
