package ee.ria.riha.conf;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valentin Suhnjov
 */
@Configuration
@ConfigurationProperties(prefix = "producer")
public class ApplicationProperties {

    private final MetaProperties meta = new MetaProperties();
    private final StorageClientProperties storageClient = new StorageClientProperties();
    private final SecurityProperties security = new SecurityProperties();
    private final InfoSystemValidationProperties infoSystemValidation = new InfoSystemValidationProperties();

    private boolean selfHosted = true;

    public MetaProperties getMeta() {
        return meta;
    }

    public StorageClientProperties getStorageClient() {
        return storageClient;
    }

    public SecurityProperties getSecurity() {
        return security;
    }

    public InfoSystemValidationProperties getInfoSystemValidation() {
        return infoSystemValidation;
    }

    public boolean isSelfHosted() {
        return selfHosted;
    }

    public void setSelfHosted(boolean selfHosted) {
        this.selfHosted = selfHosted;
    }

    public static class MetaProperties {

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

    public static class StorageClientProperties {

        @NotEmpty
        private String baseUrl;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class Cors {
        private final List<String> allowedOrigins = new ArrayList<>();

        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins.addAll(allowedOrigins);
        }
    }

    public static class SecurityProperties {
        private final Cors cors = new Cors();

        public Cors getCors() {
            return cors;
        }
    }

    public static class InfoSystemValidationProperties {
        private String schemaUrl;

        public String getSchemaUrl() {
            return schemaUrl;
        }

        public void setSchemaUrl(String schemaUrl) {
            this.schemaUrl = schemaUrl;
        }
    }
}
