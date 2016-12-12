package ee.ria.riha.services;

import ee.ria.riha.models.Infosystem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class InfosystemStorageServiceTest {

  InfosystemStorageService service;

  @Before
  public void setUp() throws Exception {
    service = new InfosystemStorageService();
    service.filePath = Files.createTempFile("", "");
  }

  @Test
  public void save() throws IOException {
    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode"));

    String savedData = new String(Files.readAllBytes(service.filePath), UTF_8);

    assertEquals("[{\"owner\":\"ownerCode\",\"docUrl\":\"http://doc.url\",\"name\":\"name\",\"shortName\":\"short-name\"}]", savedData);
  }

  @Test
  public void save_mergesWithExistingInfosystems() throws IOException {
    Files.write(service.filePath, "[{\"owner\":\"ownerCode\",\"docUrl\":\"http://doc.url?existing\",\"name\":\"existing-name\",\"shortName\":\"existing-short-name\"}]".getBytes());

    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode"));

    String savedData = new String(Files.readAllBytes(service.filePath), UTF_8);

    assertEquals("[{\"owner\":\"ownerCode\",\"docUrl\":\"http://doc.url?existing\",\"name\":\"existing-name\",\"shortName\":\"existing-short-name\"},{\"owner\":\"ownerCode\",\"docUrl\":\"http://doc.url\",\"name\":\"name\",\"shortName\":\"short-name\"}]", savedData);
  }
}