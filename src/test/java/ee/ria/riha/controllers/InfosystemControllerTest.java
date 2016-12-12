package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.InfosystemStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InfosystemControllerTest {

  @Mock InfosystemStorageService infosystemStorageService;

  @InjectMocks
  private InfosystemController controller = new InfosystemController();

  @Test
  public void save() {
    controller.owner = "123";

    controller.save("name", "shortName", "docUrl");

    ArgumentCaptor<Infosystem> infosystemArgument = ArgumentCaptor.forClass(Infosystem.class);
    verify(infosystemStorageService).save(infosystemArgument.capture());
    Infosystem infosystem = infosystemArgument.getValue();
    assertEquals("name", infosystem.getName());
    assertEquals("shortName", infosystem.getShortName());
    assertEquals("docUrl", infosystem.getDocUrl());
    assertEquals("123", infosystem.getOwner());
  }

}