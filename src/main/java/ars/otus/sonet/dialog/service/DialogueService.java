package ars.otus.sonet.dialog.service;

import ars.otus.sonet.dialog.model.dto.DialogueMessage;

import java.util.List;

/**
 * Интерфейс описывает методы для управления диалогами.
 */
public interface DialogueService {
    /**
     * Возвращает список сообщений адресованых пользователю another
     * от текущего пользователя, и ообратно.
     *
     * @param another пользователь, диалоги с которым хочет получить текущий
     *                пользователь системы.
     * @return список диалогов.
     */
    List<DialogueMessage> getList(String another);

    /**
     * Создание нового сообщения в таблице диалогов.
     *
     * @param another адресат сообщения.
     * @param text    текст сообщения.
     */
    void send(String another, String text);
}
