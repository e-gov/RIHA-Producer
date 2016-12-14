package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.DateTimeService;
import ee.ria.riha.services.InfosystemStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;

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
    doReturn(true).when(controller).isValid(any(Infosystem.class));

    controller.save("name", "shortName", "docUrl");

    ArgumentCaptor<Infosystem> infosystemArgument = ArgumentCaptor.forClass(Infosystem.class);
    verify(infosystemStorageService).save(infosystemArgument.capture());
    Infosystem infosystem = infosystemArgument.getValue();
    assertEquals("name", infosystem.getName());
    assertEquals("shortName", infosystem.getShortname());
    assertEquals("docUrl", infosystem.getDocumentation());
    assertEquals("123", infosystem.getOwner());
    assertEquals("2016-01-01T08:11:12", infosystem.getStatus().getTimestamp());
    assertEquals("/123/shortName", infosystem.getMeta().getURI());
  }

  @Test(expected = BadRequest.class)
  public void save_doesNotSaveInvalidInfosystem() {
    doReturn(ZonedDateTime.of(2016, 1, 1, 10, 11, 12, 0, ZoneId.of("Europe/Tallinn"))).when(dateTimeService).now();
    doReturn(false).when(controller).isValid(any(Infosystem.class));

    controller.save("", "", "");

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
    assertTrue(controller.isValid(new Infosystem("name", "shortName", "docUrl", "12345", "2016-12-10T01:00:00")));

    assertFalse(controller.isValid(new Infosystem("", "shortName", "docUrl", "12345", "2016-12-10T01:00:00")));
    assertFalse(controller.isValid(new Infosystem("name", "", "docUrl", "12345", "2016-12-10T01:00:00")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "", "12345", "2016-12-10T01:00:00")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "docUrl", "", "2016-12-10T01:00:00")));
    assertFalse(controller.isValid(new Infosystem("name", "shortName", "docUrl", "12345", "")));
    assertFalse(controller.isValid(new Infosystem("    ", "   ", "   ", "   ", "  ")));
    assertFalse(controller.isValid(new Infosystem(null, null, null, null, null)));
  }

  @Test
  public void delete() {
    controller.delete("shortname");

    verify(infosystemStorageService).delete("shortname");
  }
}