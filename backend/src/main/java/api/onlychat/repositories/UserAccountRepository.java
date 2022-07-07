package api.onlychat.repositories;

import api.onlychat.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByEmail(String email);

    @Query(value = "select * from user where email = :email", nativeQuery = true)
    UserAccount getUserByEmail(String email);

    @Query(value = "SELECT u.id, u.email, u.name, u.photo " +
            "FROM user u INNER JOIN contact c ON c.contact = u.id " +
            "WHERE c.user_id = :paramId ORDER BY c.date_time")
    Set<UserAccount> getContacts(int paramId);
}