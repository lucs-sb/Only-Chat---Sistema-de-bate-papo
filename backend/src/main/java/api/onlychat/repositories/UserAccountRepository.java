package api.onlychat.repositories;

import api.onlychat.entities.Contact;
import api.onlychat.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findUserAccountByEmail(String email);

    @Query(value = "SELECT U.* FROM user_account U " +
            "INNER JOIN contact C ON C.friend = U.id " +
            "INNER JOIN user_contact UC ON C.id = UC.contact_id " +
            "WHERE UC.user_id = :paramid ORDER BY C.date_time desc", nativeQuery = true)
    Set<UserAccount> getContacts(Long paramid);

    @Query(value = "SELECT U.* FROM user_account U " +
            "INNER JOIN contact C ON C.friend = U.id " +
            "INNER JOIN user_contact UC ON UC.contact_id = C.id " +
            "WHERE UC.user_id = :paramPrincipal " +
            "AND (U.name LIKE CONCAT('%',:paramBusca,'%') " +
            "OR U.email LIKE CONCAT('%',:paramBusca,'%'))", nativeQuery = true)
    Set<UserAccount> findContacts(Long paramPrincipal, String paramBusca);

    @Query(value = " SELECT * FROM user_account WHERE id != :paramId AND " +
            "(name LIKE CONCAT('%',:paramBusca,'%') OR " +
            "email LIKE CONCAT('%',:paramBusca,'%'))", nativeQuery = true)
    Set<UserAccount> findUsers(Long paramId, String paramBusca);

    @Query(value = "SELECT * FROM user_account WHERE id != :paramId", nativeQuery = true)
    Set<UserAccount> findNoContacts(Long paramId);
}