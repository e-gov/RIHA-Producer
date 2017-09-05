package ee.ria.riha.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import ee.ria.riha.domain.model.InfoSystem;
import ee.ria.riha.service.InfoSystemService;
import ee.ria.riha.service.JsonValidationException;
import ee.ria.riha.storage.util.Filterable;
import ee.ria.riha.storage.util.PageRequest;
import ee.ria.riha.storage.util.Pageable;
import ee.ria.riha.storage.util.PagedResponse;
import ee.ria.riha.web.model.InfoSystemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/systems")
public class InfoSystemController {

    @Autowired
    private InfoSystemService infoSystemService;

    @GetMapping
    public ResponseEntity list(Pageable pageable, Filterable filterable) {
        PagedResponse<InfoSystem> list = infoSystemService.list(pageable, filterable);
        return ResponseEntity.ok(createPagedModel(list));
    }

    private PagedResponse<InfoSystemModel> createPagedModel(PagedResponse<InfoSystem> list) {
        return new PagedResponse<>(new PageRequest(list.getPage(), list.getSize()),
                                   list.getTotalElements(),
                                   list.getContent().stream()
                                           .map(this::createModel)
                                           .collect(toList()));
    }

    @GetMapping("/{shortName}")
    public ResponseEntity get(@PathVariable("shortName") String shortName) {
        InfoSystem infoSystem = infoSystemService.get(shortName);
        return ResponseEntity.ok(createModel(infoSystem));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody InfoSystemModel model) {
        InfoSystem infoSystem = infoSystemService.create(new InfoSystem(model.getJson()));
        return ResponseEntity.ok(createModel(infoSystem));
    }

    private InfoSystemModel createModel(InfoSystem infoSystem) {
        InfoSystemModel model = new InfoSystemModel();
        model.setId(infoSystem.getId());
        model.setJson(infoSystem.getJsonObject().toString());

        return model;
    }

    @PutMapping("/{shortName}")
    public ResponseEntity update(@PathVariable("shortName") String shortName, @RequestBody InfoSystemModel model) {
        InfoSystem infoSystem = infoSystemService.update(shortName, new InfoSystem(model.getJson()));
        return ResponseEntity.ok(createModel(infoSystem));
    }

    @DeleteMapping(value = "/{shortName}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity delete(@RequestParam("shortName") String shortName) {
        return ResponseEntity.badRequest().body("Not implemented");
    }

    @GetMapping(value = "/systems.json")
    @ResponseBody
    public ResponseEntity publish() {
        return ResponseEntity.badRequest().body("Not implemented");
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAppException(BadRequest e) {
        return e.getMessage();
    }

    @ExceptionHandler(JsonValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<JsonNode> handleJsonValidationException(JsonValidationException e) {
        return e.getMessages().stream()
                .map(ProcessingMessage::asJson)
                .collect(toList());
    }
}
