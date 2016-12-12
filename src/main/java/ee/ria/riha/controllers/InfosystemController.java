package ee.ria.riha.controllers;

import ee.ria.riha.models.Infosystem;
import ee.ria.riha.services.InfosystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfosystemController {

  @Autowired InfosystemStorageService infosystemStorageService;

  @Value("${owner}")
  String owner;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public String save(@RequestParam("name") String name, @RequestParam("shortName") String shortName, @RequestParam("docUrl") String docUrl) {
    Infosystem infosystem = new Infosystem(name, shortName, docUrl, owner);
    infosystemStorageService.save(infosystem);
    //todo show success message
    return "redirect:/";
  }
}
