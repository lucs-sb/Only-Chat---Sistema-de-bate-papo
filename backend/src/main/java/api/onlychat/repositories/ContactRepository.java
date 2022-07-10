package api.onlychat.repositories;

import api.onlychat.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT c.* " +
            "FROM contact AS c INNER JOIN user_contact AS us ON c.id = us.contact_id " +
            "WHERE us.user_id = :paramid ORDER BY c.date_time", nativeQuery = true)
    Set<Contact> getContacts(Long paramid);

    Contact findByPrincipalAndEmail(Long principal, String email);

    @Query(value = "SELECT * FROM contact " +
            "WHERE principal = :paramPrincipal " +
            "AND (nome LIKE CONCAT('%',:paramBusca,'%') " +
            "OR email LIKE CONCAT('%',:paramBusca,'%'))", nativeQuery = true)
    Set<Contact> findContacts(@Param("paramPrincipal") Long paramPrincipal, @Param("paramBusca") String paramBusca);
}
