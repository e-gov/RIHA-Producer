package ee.ria.riha.services;

import ee.ria.riha.models.Infosystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class InfosystemStorageServiceTest {

  @Spy
  @InjectMocks
  InfosystemStorageService service;


  @Before
  public void setUp() throws Exception {
    service.filePath = Files.createTempFile("", "");
  }

  @Test
  public void save() throws IOException {
    doReturn(null).when(service).load();

    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode", "status-timestamp"));

    String savedData = new String(Files.readAllBytes(service.filePath), UTF_8);

    assertEquals("[{\"owner\":\"ownerCode\"," +
      "\"meta\":{\"URI\":\"/ownerCode/short-name\"}," +
      "\"docUrl\":\"http://doc.url\"," +
      "\"name\":\"name\"," +
      "\"shortName\":\"short-name\"," +
      "\"status\":{\"timestamp\":\"status-timestamp\"}}]", savedData);
  }

  @Test
  public void save_mergesWithExistingInfosystems() throws IOException {
    Files.write(service.filePath, "[{\"name\":\"existing-system-name\"}]".getBytes());

    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode", "status-timestamp"));

    String savedData = new String(Files.readAllBytes(service.filePath), UTF_8);

    assertEquals("[{\"name\":\"existing-system-name\"}," +
      "{\"owner\":\"ownerCode\"," +
      "\"meta\":{\"URI\":\"/ownerCode/short-name\"}," +
      "\"docUrl\":\"http://doc.url\"," +
      "\"name\":\"name\"" +
      ",\"shortName\":\"short-name\"," +
      "\"status\":{\"timestamp\":\"status-timestamp\"}}]", savedData);
  }
}