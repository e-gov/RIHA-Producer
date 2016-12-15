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

  public Infosystem(String name, String shortName, String documentation, String owner, String statusTimestamp, String baseUrl) {
    this.name = name;
    this.shortname = shortName;
    this.documentation = documentation;
    this.owner = owner;
    this.status = new Status(statusTimestamp);
    this.meta = new Meta(baseUrl);
  }

  public Infosystem(JSONObject jsonObject, String baseUrl) {
    this(
      getPropertyValue(jsonObject, "name"),
      getPropertyValue(jsonObject, "shortname"),
      getPropertyValue(jsonObject, "documentation"),
      getPropertyValue(jsonObject, "owner"),
      jsonObject.has("status") ? getPropertyValue(jsonObject.getJSONObject("status"), "timestamp") : null,
      baseUrl);
  }

  private static String getPropertyValue(JSONObject jsonObject, String name) {
    return jsonObject.has(name) ? jsonObject.getString(name) : null;
  }

  @Getter
  @AllArgsConstructor
  public class Status {
    String timestamp;
  }

  public class Meta {

    String baseUrl;

    public Meta(String baseUri) {
      this.baseUrl = baseUri;
    }

    public String getURI() {
      if (shortname == null) return null;
      return baseUrl + "/" + shortname;
    };
  }
}
