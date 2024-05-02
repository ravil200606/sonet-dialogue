package ars.otus.sonet.dialog.service.impl;

import ars.otus.sonet.dialog.model.context.UserContext;
import ars.otus.sonet.dialog.model.dto.DialogueMessage;
import ars.otus.sonet.dialog.repository.DialogueRepository;
import ars.otus.sonet.dialog.service.DialogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис реализует методы для управления диалогами.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DialogueServiceImpl implements DialogueService {

    private final DialogueRepository repository;

    /**
     * Возвращает список сообщений адресованых пользователю another
     * от текущего пользователя, и ообратно.
     *
     * @param another пользователь, диалоги с которым хочет получить текущий
     *                пользователь системы.
     * @return список диалогов.
     */
    @Override
    public List<DialogueMessage> getList(String another) {
        var owner = UserContext.getCurrentUser();
        return repository.getList(owner, another);
    }

    /**
     * Создание нового сообщения в таблице диалогов.
     *
     * @param another адресат сообщения.
     * @param text    текст сообщения.
     */
    @Override
    public void send(String another, String text) {
        var owner = UserContext.getCurrentUser();
        repository.insert(owner, another, text);
    }
}
