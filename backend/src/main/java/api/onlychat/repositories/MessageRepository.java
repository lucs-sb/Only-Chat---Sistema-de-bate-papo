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

    @Query(value = "SELECT m.* FROM message AS m /*#pageable*/" +
            "INNER JOIN contact_message AS cm ON cm.message_id = m.id " +
            "WHERE (m.sender = :paramSender AND m.receiver = :paramReceiver) " +
            "OR (m.sender = :paramReceiver AND m.receiver = :paramSender) " +
            "ORDER BY m.date_time ASC",
            //countQuery = "SELECT count(*) FROM message",
            nativeQuery = true)
    Page<Message> getAllChatMessages(Pageable pageable, Long paramSender, Long paramReceiver);
}
