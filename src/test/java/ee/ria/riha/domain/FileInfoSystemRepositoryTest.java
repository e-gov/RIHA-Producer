package ee.ria.riha.domain;

import ee.ria.riha.domain.model.InfoSystem;
import org.junit.Test;

public class FileInfoSystemRepositoryTest {

    FileInfoSystemRepository service = new FileInfoSystemRepository();

    @Test(expected = InfoSystemRepositoryException.class)
    public void add() {
        service.add(new InfoSystem("{}"));
    }

    @Test(expected = InfoSystemRepositoryException.class)
    public void get() {
        service.load("test-system");
    }

    @Test(expected = InfoSystemRepositoryException.class)
    public void update() {
        service.update("test-system", new InfoSystem("{}"));
    }

    @Test(expected = InfoSystemRepositoryException.class)
    public void remove() {
        service.remove("test-system");
    }
}