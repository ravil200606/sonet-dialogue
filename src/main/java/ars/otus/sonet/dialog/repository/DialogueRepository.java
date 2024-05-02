package ars.otus.sonet.dialog.repository;

import ars.otus.sonet.dialog.model.dto.DialogueMessage;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DialogueRepository extends BaseRepository {
    public DialogueRepository(JdbcTemplate serviceJdbcTemplate) {
        super(serviceJdbcTemplate);
    }

    private static final String SQL_LIST = """
            SELECT SF.user_id AS FROM_ID, ST.user_id AS TO_ID,D.content FROM dialogue D
            JOIN sonet_user SF on SF.id = D.from_id
            JOIN sonet_user ST on ST.id = D.to_id
            WHERE (D.from_id = (SELECT id
                                FROM sonet_user
                                WHERE user_id = :owner)
                AND
                   D.to_id = (SELECT id FROM sonet_user WHERE user_id = :another)
                      )
            OR
                (D.from_id = (SELECT id
                              FROM sonet_user
                              WHERE user_id = :another)
                    AND
                 D.to_id = (SELECT id FROM sonet_user WHERE user_id = :owner)
                    )
            ORDER BY D.id
            """;

    /**
     * Возвращает диалоги пользователя owner с другим пользователем another.
     *
     * @param owner   пользователь запросивший список своих диалогов.
     * @param another пользователь, диалоги с которым хочет получить запросивший.
     * @return список диалогов.
     */
    public List<DialogueMessage> getList(@NonNull String owner, String another) {
        return getNamedParameterJdbcTemplate().query(SQL_LIST,
                new MapSqlParameterSource("owner", owner)
                        .addValue("another", another),
                (rs, rowNum) -> DialogueMessage.builder()
                        .from(rs.getString("FROM_ID"))
                        .to(rs.getString("TO_ID"))
                        .text(rs.getString("CONTENT"))
                        .build());
    }

    private static final String SQL_INSERT = """
            INSERT INTO DIALOGUE(from_id, to_id, dialogue_date, content)
            VALUES ((SELECT ID
                   FROM SONET_USER
                   WHERE user_id = :owner),
                    (SELECT ID
                     FROM SONET_USER
                   WHERE user_id = :another),
                   now(),
                   :content
            )
            """;

    /**
     * Добавляет новую запись в таблицу диалогов.
     *
     * @param owner   пользователь автор сообщения.
     * @param another пользователь адресат сообщения.
     * @param text    текст сообщения.
     */
    public void insert(String owner, String another, String text) {
        getNamedParameterJdbcTemplate().update(
                SQL_INSERT,
                new MapSqlParameterSource("owner", owner)
                        .addValue("another", another)
                        .addValue("content", text));
    }
}
