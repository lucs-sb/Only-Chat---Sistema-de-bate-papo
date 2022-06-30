package onlychat.api.repositories;

import onlychat.api.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByEmail(String email);

    @Query(value = "SELECT * FROM user_account WHERE email = :email", nativeQuery = true)
    UserAccount getUserAccountByEmail(String email);
}
