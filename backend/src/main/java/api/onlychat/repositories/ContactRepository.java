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
            "WHERE us.user_id = :paramid ORDER BY c.date_time desc", nativeQuery = true)
    Set<Contact> getContacts(Long paramid);

    Contact findContactByPrincipalAndFriendAndEmail(Long principal, Long friend, String email);

    Contact findContactByPrincipalAndFriend(Long principal, Long friend);

    @Query(value = "SELECT * FROM contact " +
            "WHERE principal = :paramPrincipal " +
            "AND (nome LIKE CONCAT('%',:paramBusca,'%') " +
            "OR email LIKE CONCAT('%',:paramBusca,'%'))", nativeQuery = true)
    Set<Contact> findContacts(@Param("paramPrincipal") Long paramPrincipal, @Param("paramBusca") String paramBusca);

    @Query(value = "SELECT c.* FROM contact AS c " +
            "INNER JOIN contact_message AS cm ON cm.contact_id = c.id " +
            "INNER JOIN message AS m ON m.id = cm.message_id " +
            "WHERE c.principal = :paramPrincipal " +
            "AND (m.sender = :paramPrincipal OR m.receiver = :paramPrincipal) " +
            "AND m.message LIKE CONCAT('%',:paramBusca,'%')", nativeQuery = true)
    Set<Contact> findContactsWithChats(@Param("paramPrincipal") Long paramPrincipal, @Param("paramBusca") String paramBusca);

    @Query(value = "SELECT c.* FROM contact AS c " +
            "INNER JOIN contact_message AS cm ON cm.contact_id = c.id " +
            "INNER JOIN message AS m ON m.id = cm.message_id " +
            "WHERE m.sender = :paramUserLogado " +
            "OR m.receiver = :paramUserLogado " +
            "ORDER BY m.date_time DESC", nativeQuery = true)
    Set<Contact> findByChats(Long paramUserLogado);
}
