package ars.otus.sonet.dialog.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogueMessage {
    @Schema(name = "request_id", example = "1d535fd6-7521-4cb1-aa6d-031be7123c4d.",
            description = "От кого сообщение.")
    @JsonProperty(value = "from")
    private String from;
    @Schema(name = "request_id", example = "1d535fd6-7521-4cb1-a76d-031be7123c5d.",
            description = "Кому сообщение.")
    @JsonProperty(value = "to")
    private String to;
    @Schema(name = "request_id", example = "Привет. Как дела?.",
            description = "Содержание сообщения.")
    @JsonProperty(value = "text")
    private String text;
}
