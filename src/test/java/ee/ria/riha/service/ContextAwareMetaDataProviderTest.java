package ee.ria.riha.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Valentin Suhnjov
 */
public class ContextAwareMetaDataProviderTest {

    private ContextAwareMetaDataProvider metaDataProvider = new ContextAwareMetaDataProvider();

    @Test
    public void providesTestOwnerCode() {
        assertThat(metaDataProvider.getOwnerCode(), equalTo("1234567890"));
    }

    @Test
    public void providesTestOwnerName() {
        assertThat(metaDataProvider.getOwnerName(), equalTo("Rebane"));
    }
}