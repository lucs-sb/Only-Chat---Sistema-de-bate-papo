package api.onlychat.repositories;

import api.onlychat.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
//    @Query(value = "SELECT c.id, c.user_id, c.date_time, c.name, c.photo, c.email" +
//            "FROM contacts AS c INNER JOIN messages AS m ON m.from = c.user_id")
//    Set<Contact> getContacts();
}
