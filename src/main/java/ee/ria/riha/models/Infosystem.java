package ee.ria.riha.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Infosystem {
  String name;
  String shortName;
  String docUrl;
  String owner;
}
