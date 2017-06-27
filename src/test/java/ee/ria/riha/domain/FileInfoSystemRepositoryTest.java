package ee.ria.riha.domain;

import ee.ria.riha.domain.FileInfoSystemRepository;
import ee.ria.riha.domain.InfoSystemRepositoryException;
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
        service.get(123L);
    }

    @Test(expected = InfoSystemRepositoryException.class)
    public void update() {
        service.update(123L, new InfoSystem("{}"));
    }

    @Test(expected = InfoSystemRepositoryException.class)
    public void remove() {
        service.remove(123L);
    }
}