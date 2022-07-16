package api.onlychat.repositories;

import api.onlychat.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT M.* FROM message M " +
            "INNER JOIN contact_message CM ON CM.message_id = M.id " +
            "INNER JOIN contact C ON C.id = CM.contact_id " +
            "WHERE (C.principal = :paramSender AND C.friend = :paramReceiver) " +
            "OR (C.principal = :paramReceiver AND C.friend = :paramSender) " +
            "ORDER BY M.date_time ASC /*#pageable*/",
            countQuery = "SELECT M.* FROM message M " +
                    "INNER JOIN contact_message CM ON CM.message_id = M.id " +
                    "INNER JOIN contact C ON C.id = CM.contact_id " +
                    "WHERE (C.principal = :paramSender AND C.friend = :paramReceiver) " +
                    "OR (C.principal = :paramReceiver AND C.friend = :paramSender) " +
                    "ORDER BY M.date_time ASC /*#pageable*/", nativeQuery = true)
    Page<Message> getAllChatMessages(Pageable pageable, Long paramSender, Long paramReceiver);

    //--#pageable\n for postgresql
}
