package ee.ria.riha.services;

import ee.ria.riha.models.Infosystem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class InfosystemStorageService {

  Path filePath = Paths.get("systems.json");

  synchronized public void save(Infosystem infosystem) {
    JSONArray infosystems = new JSONArray(load());
    infosystems.put(new JSONObject(infosystem));
    save(infosystems);
  }

  synchronized public void delete(String shortName) {
    JSONArray infosystems = new JSONArray(load());
    for (int i = 0; i < infosystems.length(); i++) {
      JSONObject infosystem = infosystems.getJSONObject(i);
      if (infosystem.getString("shortname").equals(shortName)) {
        infosystems.remove(i);
      }
    }
    save(infosystems);
  }

  public String load() {
    if (!filePath.toFile().exists()) return "[]";
    try {
      return new String(Files.readAllBytes(filePath), UTF_8);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void save(JSONArray infosystems) {
    try {
      Files.write(filePath, infosystems.toString().getBytes(UTF_8));
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
