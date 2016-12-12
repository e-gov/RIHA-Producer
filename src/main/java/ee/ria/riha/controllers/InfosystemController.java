package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.DateTimeService;
import ee.ria.riha.services.InfosystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static ee.ria.riha.services.DateTimeService.format;
import static ee.ria.riha.services.DateTimeService.toUTC;

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

  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public String save(@RequestParam("name") String name, @RequestParam("shortName") String shortName, @RequestParam("docUrl") String docUrl) {
    Infosystem infosystem = new Infosystem(name, shortName, docUrl, owner, format(toUTC(dateTimeService.now())));
    infosystemStorageService.save(infosystem);
    //todo show success message
    return "redirect:/";
  }

  @RequestMapping(value = "/systems.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String json() {
    return infosystemStorageService.load();
  }
}
