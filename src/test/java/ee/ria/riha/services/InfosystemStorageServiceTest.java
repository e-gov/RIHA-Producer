package ee.ria.riha.services;

import ee.ria.riha.models.Infosystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
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
    doReturn("[]").when(service).load();

    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode", "status-timestamp"));

    JSONAssert.assertEquals("[{\"owner\":\"ownerCode\"," +
      "\"meta\":{\"URI\":\"/ownerCode/short-name\"}," +
      "\"documentation\":\"http://doc.url\"," +
      "\"name\":\"name\"," +
      "\"shortname\":\"short-name\"," +
      "\"status\":{\"timestamp\":\"status-timestamp\"}}]", fileData(), true);
  }

  @Test
  public void save_mergesWithExistingInfosystems() throws IOException {
    Files.write(service.filePath, "[{\"name\":\"existing-system-name\"}]".getBytes());

    service.save(new Infosystem("name", "short-name", "http://doc.url", "ownerCode", "status-timestamp"));

    JSONAssert.assertEquals("[{\"name\":\"existing-system-name\"}," +
      "{\"owner\":\"ownerCode\"," +
      "\"meta\":{\"URI\":\"/ownerCode/short-name\"}," +
      "\"documentation\":\"http://doc.url\"," +
      "\"name\":\"name\"" +
      ",\"shortname\":\"short-name\"," +
      "\"status\":{\"timestamp\":\"status-timestamp\"}}]", fileData(), true);
  }

  @Test
  public void delete() throws IOException {
    Files.write(service.filePath, "[{\"shortname\":\"other-short-name\"}, {\"shortname\":\"short-name\"}]".getBytes());

    service.delete("short-name");

    JSONAssert.assertEquals("[{\"shortname\":\"other-short-name\"}]", fileData(), true);
  }

  private String fileData() throws IOException {
    return new String(Files.readAllBytes(service.filePath), UTF_8);
  }
}