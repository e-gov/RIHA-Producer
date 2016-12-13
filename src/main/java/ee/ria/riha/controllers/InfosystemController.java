package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.DateTimeService;
import ee.ria.riha.services.InfosystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static ee.ria.riha.services.DateTimeService.format;
import static ee.ria.riha.services.DateTimeService.toUTC;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Controller
public class InfosystemController {

  @Autowired InfosystemStorageService infosystemStorageService;

  @Autowired DateTimeService dateTimeService;

  @Value("${owner}")
  String owner;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = "/form/", method = RequestMethod.GET)
  public String form() {
    return "form";
  }

  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public String save(@RequestParam("name") String name, @RequestParam("shortName") String shortName, @RequestParam("documentation") String documentation) {
    Infosystem infosystem = new Infosystem(name, shortName, documentation, owner, format(toUTC(dateTimeService.now())));
    if (!isValid(infosystem)) throw new BadRequest();

    infosystemStorageService.save(infosystem);
    return "redirect:/";
  }

  @RequestMapping(value = "/systems.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String json() {
    return infosystemStorageService.load();
  }

  boolean isValid(Infosystem infosystem) {
    return isNotBlank(infosystem.getName())
      && isNotBlank(infosystem.getShortname())
      && isNotBlank(infosystem.getDocumentation())
      && isNotBlank(infosystem.getOwner())
      && isNotBlank(infosystem.getStatus().getTimestamp());
  }

  @ExceptionHandler(BadRequest.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleAppException(BadRequest e) {
    return e.getMessage();
  }
}
