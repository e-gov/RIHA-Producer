package ee.ria.riha.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Valentin Suhnjov
 */
public class PreConfiguredMetaDataProviderTest {

    private PreConfiguredMetaDataProvider metaDataProvider = new PreConfiguredMetaDataProvider("testCode", "testName");

    @Test
    public void providesConfiguredOwnerCode() {
        assertThat(metaDataProvider.getOwnerCode(), equalTo("testCode"));
    }

    @Test
    public void providesConfiguredOwnerName() {
        assertThat(metaDataProvider.getOwnerName(), equalTo("testName"));
    }
}