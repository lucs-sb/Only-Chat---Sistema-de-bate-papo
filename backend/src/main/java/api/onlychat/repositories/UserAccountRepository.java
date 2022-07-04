package api.onlychat.repositories;

import api.onlychat.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByUsername(String username);

    @Query(value = "select * from user with (nolock) where email = :email", nativeQuery = true)
    UserAccount getUserByEmail(String email);
}