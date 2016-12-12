package ee.ria.riha.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Infosystem {
  String name;
  String shortName;
  String docUrl;
  String owner;
  Status status;
  Meta meta;

  public Infosystem(String name, String shortName, String docUrl, String owner, String statusTimestamp) {
    this.name = name;
    this.shortName = shortName;
    this.docUrl = docUrl;
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
      return "/" + owner + "/" + shortName;
    };
  }
}
