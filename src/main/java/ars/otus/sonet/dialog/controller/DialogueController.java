package ars.otus.sonet.dialog.controller;

import ars.otus.sonet.dialog.model.dto.DialogueMessage;
import ars.otus.sonet.dialog.model.dto.DialogueText;
import ars.otus.sonet.dialog.model.dto.ErrorResponse;
import ars.otus.sonet.dialog.service.DialogueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Ошибка сервера.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Невалидные данные.",
                content = @Content(schema = @Schema(implementation = String.class)))
})
@RequiredArgsConstructor
@RestController
public class DialogueController {

    private final DialogueService service;

    /**
     * Диалог между двумя пользователями.
     *
     * @param userId Пользователь для которого запросить диалоги.
     * @return ответ о результате аутентификации {@link ResponseEntity <AuthenticationToken>}.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список диалогов.",
                    content = @Content(schema = @Schema(implementation = DialogueMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = """
            Диалог между двумя пользователями. Получение списка сообщений.
            """)
    @GetMapping(value = "/dialog/{user_id}/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DialogueMessage>> list(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(service.getList(userId));
    }

    /**
     * Создать запись в диалоге между двумя пользователями.
     *
     * @param userId Пользователь которому отправить сообщение.
     * @return ответ о результате аутентификации {@link ResponseEntity <AuthenticationToken>}.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно отправлено.",
                    content = @Content(schema = @Schema(implementation = DialogueMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @Operation(summary = """
            Диалог между двумя пользователями. Создание нового сообщения.
            """)
    @PostMapping(value = "/dialog/{user_id}/send",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DialogueMessage>> send(@PathVariable("user_id") String userId,
                                                      @RequestBody DialogueText text) {
        service.send(userId, text.getText());
        return ResponseEntity.ok().build();
    }
}
