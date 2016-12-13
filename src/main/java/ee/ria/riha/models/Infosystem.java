package ee.ria.riha.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

  @Getter
  @AllArgsConstructor
  public class Status {
    String timestamp;
  }

  @AllArgsConstructor
  public class Meta {
    public String getURI() {
      return "/" + owner + "/" + shortname;
    };
  }
}
