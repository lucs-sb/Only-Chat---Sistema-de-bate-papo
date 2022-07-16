package api.onlychat.repositories;

import api.onlychat.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT * FROM contact WHERE principal = :principal AND friend = :friend", nativeQuery = true)
    Contact findContactByPrincipalAndFriend(Long principal, Long friend);
}
