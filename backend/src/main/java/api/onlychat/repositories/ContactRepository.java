package api.onlychat.repositories;

import api.onlychat.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(value = "SELECT c.* " +
            "FROM contact AS c INNER JOIN user_contact AS us " +
            "WHERE us.user_id = :paramid ORDER BY c.date_time", nativeQuery = true)
    Set<Contact> getContacts(int paramid);

    Contact findByPrincipal(Long principal);

    //Contact findByEmail(String email);
}
