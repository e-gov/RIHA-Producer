package ee.ria.riha.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;

@Getter
public class Infosystem {
  String name;
  String shortname;
  String documentation;
  String owner;
  Status status;
  Meta meta;

  public Infosystem(String name, String shortName, String documentation, String owner, String statusTimestamp) {
    this.name = name;
    this.shortname = shortName;
    this.documentation = documentation;
    this.owner = owner;
    this.status = new Status(statusTimestamp);
    this.meta = new Meta();
  }

  public Infosystem(JSONObject jsonObject) {
    this(
      getPropertyValue(jsonObject, "name"),
      getPropertyValue(jsonObject, "shortname"),
      getPropertyValue(jsonObject, "documentation"),
      getPropertyValue(jsonObject, "owner"),
      jsonObject.has("status") ? getPropertyValue(jsonObject.getJSONObject("status"), "timestamp") : null
    );
  }

  private static String getPropertyValue(JSONObject jsonObject, String name) {
    return jsonObject.has(name) ? jsonObject.getString(name) : null;
  }

  @Getter
  @AllArgsConstructor
  public class Status {
    String timestamp;
  }

  @AllArgsConstructor
  public class Meta {
    public String getURI() {
      if (owner == null || shortname == null) return null;
      return "/" + owner + "/" + shortname;
    };
  }
}
