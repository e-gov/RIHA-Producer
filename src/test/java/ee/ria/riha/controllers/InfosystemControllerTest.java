package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.DateTimeService;
import ee.ria.riha.services.InfosystemStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InfosystemControllerTest {

  @Mock InfosystemStorageService infosystemStorageService;
  @Mock DateTimeService dateTimeService;

  @InjectMocks
  private InfosystemController controller = new InfosystemController();

  @Test
  public void save() {
    doReturn(ZonedDateTime.of(2016, 1, 1, 10, 11, 12, 0, ZoneId.of("Europe/Tallinn"))).when(dateTimeService).now();
    controller.owner = "123";

    controller.save("name", "shortName", "docUrl");

    ArgumentCaptor<Infosystem> infosystemArgument = ArgumentCaptor.forClass(Infosystem.class);
    verify(infosystemStorageService).save(infosystemArgument.capture());
    Infosystem infosystem = infosystemArgument.getValue();
    assertEquals("name", infosystem.getName());
    assertEquals("shortName", infosystem.getShortName());
    assertEquals("docUrl", infosystem.getDocUrl());
    assertEquals("123", infosystem.getOwner());
    assertEquals("2016-01-01T08:11:12", infosystem.getStatus().getTimestamp());
    assertEquals("/123/shortName", infosystem.getMeta().getURI());
  }

  @Test
  public void json() {
    doReturn("[{\"name\":\"infosystem name\"}]").when(infosystemStorageService).load();

    assertEquals("[{\"name\":\"infosystem name\"}]", controller.json());
  }
}