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

  public void save(Infosystem infosystem) {
    try {
      String existingInfosystems = load();
      JSONArray infosystems = existingInfosystems == null ? new JSONArray() : new JSONArray(existingInfosystems);
      infosystems.put(new JSONObject(infosystem));
      Files.write(filePath, infosystems.toString().getBytes(UTF_8));
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String load() {
    if (!filePath.toFile().exists()) return null;
    try {
      return new String(Files.readAllBytes(filePath), UTF_8);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
