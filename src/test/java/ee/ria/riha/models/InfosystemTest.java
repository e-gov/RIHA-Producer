package ee.ria.riha.models;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InfosystemTest {

  @Test
  public void createFromEmptyJsonObject() {
    Infosystem infosystem = new Infosystem(new JSONObject("{}"));

    assertNull(infosystem.getOwner());
    assertNull(infosystem.getMeta().getURI());
    assertNull(infosystem.getDocumentation());
    assertNull(infosystem.getName());
    assertNull(infosystem.getShortname());
    assertNull(infosystem.getStatus().getTimestamp());
  }

  @Test
  public void createNewFromJsonObject() {
    String json = "{\"owner\":\"12345\",\"meta\":{\"URI\":\"/12345/fox\"},\"documentation\":\"http://riha.eesti.ee\",\"name\":\"Rebaste register\",\"shortname\":\"fox\",\"status\":{\"timestamp\":\"2016-12-13T17:10:20.785\"}}";

    Infosystem infosystem = new Infosystem(new JSONObject(json));

    assertEquals("12345", infosystem.getOwner());
    assertEquals("/12345/fox", infosystem.getMeta().getURI());
    assertEquals("http://riha.eesti.ee", infosystem.getDocumentation());
    assertEquals("Rebaste register", infosystem.getName());
    assertEquals("fox", infosystem.getShortname());
    assertEquals("2016-12-13T17:10:20.785", infosystem.getStatus().getTimestamp());
  }

  @Test
  public void metaUri() {
    assertNull(meta(null, null).getURI());
    assertNull(meta("owner", null).getURI());
    assertNull(meta(null, "shortName").getURI());
    assertEquals("/owner/shortName", meta("owner", "shortName").getURI());
  }

  private Infosystem.Meta meta(String owner, String shortName) {
    Infosystem infosystem = new Infosystem(null, shortName, null, owner, null);
    return infosystem.getMeta();
  }
}