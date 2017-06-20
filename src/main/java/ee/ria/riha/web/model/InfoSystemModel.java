package ee.ria.riha.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Valentin Suhnjov
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InfoSystemModel {

    private Integer id;

    @JsonIgnore
    private String raw;

    @JsonRawValue
    public String getDetails() {
        return raw;
    }

    @JsonSetter
    public void setDetails(JsonNode raw) {
        this.raw = raw.toString();
    }

    public String getRaw() {
        return this.raw;
    }

    public void setRaw(String description) {
        this.raw = description;
    }
}
