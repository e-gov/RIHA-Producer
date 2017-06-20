package ee.ria.riha.web;

import ee.ria.riha.domain.model.Infosystem;
import ee.ria.riha.service.DateTimeService;
import ee.ria.riha.service.InfosystemStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.ui.Model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InfosystemControllerTest {

  @Mock InfosystemStorageService infosystemStorageService;
  @Mock DateTimeService dateTimeService;

  @Spy @InjectMocks
  private InfosystemController controller = new InfosystemController();

  @Test
  public void save() {
    doReturn(ZonedDateTime.of(2016, 1, 1, 10, 11, 12, 0, ZoneId.of("Europe/Tallinn"))).when(dateTimeService).now();
    controller.owner = "123";
    controller.baseUrl = "http://base.url";
    doReturn(true).when(controller).isValid(any(Infosystem.class));

    controller.save(null, "name", "shortName", "docUrl");

    ArgumentCaptor<Infosystem> infosystemArgument = ArgumentCaptor.forClass(Infosystem.class);
    verify(infosystemStorageService).save(infosystemArgument.capture());
    Infosystem infosystem = infosystemArgument.getValue();
    assertEquals("name", infosystem.getName());
    assertEquals("shortName", infosystem.getShortname());
    assertEquals("docUrl", infosystem.getDocumentation());
    assertEquals("123", infosystem.getOwner().getCode());
    assertEquals("2016-01-01T08:11:12", infosystem.getMeta().getSystem_status().getTimestamp());
    assertEquals("http://base.url/shortName", infosystem.getUri());
  }

  @Test
  public void save_updatesExisting() {
    doReturn(ZonedDateTime.of(2016, 1, 1, 10, 11, 12, 0, ZoneId.of("Europe/Tallinn"))).when(dateTimeService).now();
    controller.owner = "123";
    controller.baseUrl = "http://base.url";
    doReturn(true).when(controller).isValid(any(Infosystem.class));

    controller.save("existing-shortName", "name", "new-shortName", "docUrl");

    ArgumentCaptor<Infosystem> infosystemArgument = ArgumentCaptor.forClass(Infosystem.class);
    verify(infosystemStorageService).save(eq("existing-shortName"), infosystemArgument.capture());
    Infosystem infosystem = infosystemArgument.getValue();
    assertEquals("name", infosystem.getName());
    assertEquals("new-shortName", infosystem.getShortname());
    assertEquals("docUrl", infosystem.getDocumentation());
    assertEquals("123", infosystem.getOwner().getCode());
    assertEquals("2016-01-01T08:11:12", infosystem.getMeta().getSystem_status().getTimestamp());
    assertEquals("http://base.url/new-shortName", infosystem.getUri());
  }

  @Test
  public void edit() {
    Infosystem infosystem = mock(Infosystem.class);
    doReturn(infosystem).when(infosystemStorageService).find("shortname");
    Model model = mock(Model.class);
    
    controller.edit(model, "shortname");

    verify(model).addAttribute("infosystem", infosystem);
  }

  @Test(expected = BadRequest.class)
  public void save_doesNotSaveInvalidInfosystem() {
    doReturn(ZonedDateTime.of(2016, 1, 1, 10, 11, 12, 0, ZoneId.of("Europe/Tallinn"))).when(dateTimeService).now();
    doReturn(false).when(controller).isValid(any(Infosystem.class));

    controller.save(null, "", "", "");

    verify(infosystemStorageService, never()).save(any(Infosystem.class));
  }

  @Test
  public void badRequest() {
    assertEquals("", controller.handleAppException(new BadRequest()));
    assertEquals("error", controller.handleAppException(new BadRequest("error")));
  }

  @Test
  public void json() {
    doReturn("[{\"name\":\"infosystem name\"}]").when(infosystemStorageService).load();

    JSONAssert.assertEquals("[{\"name\":\"infosystem name\"}]", controller.json(), true);
  }

  @Test
  public void isValid() {
    assertTrue(controller.isValid(new Infosystem("name", "shortName", "docUrl", "12345", "2016-12-10T01:00:00", "http://base.url")));

    assertFalse(controller.isValid(new Infosystem("", "shortName", "docUrl", "12345", "2016-12-10T01:00:00", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem("name", "", "docUrl", "12345", "2016-12-10T01:00:00", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "", "12345", "2016-12-10T01:00:00", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "docUrl", "", "2016-12-10T01:00:00", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "docUrl", "12345", "", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem("    ", "   ", "   ", "   ", "  ", "http://base.url")));
    assertFalse(controller.isValid(new Infosystem(null, null, null, null, null, "http://base.url")));
  }

  @Test
  public void delete() {
    controller.delete("shortname");

    verify(infosystemStorageService).delete("shortname");
  }
}