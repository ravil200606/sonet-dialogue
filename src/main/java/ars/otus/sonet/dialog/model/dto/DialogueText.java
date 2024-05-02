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
public class DialogueText {
    @Schema(name = "text", example = "Новое сообщение!",
            description = "Текст сообщения.")
    @JsonProperty(value = "text")
    private String text;
}
