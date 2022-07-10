package api.onlychat.repositories;

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

    @Query(value = "SELECT * FROM user_account WHERE email = :email", nativeQuery = true)
    UserAccount getUserByEmail(String email);

    @Query(value = " SELECT * FROM user_account WHERE id != :paramId AND " +
            "(name LIKE CONCAT('%',:paramBusca,'%') OR " +
            "email LIKE CONCAT('%',:paramBusca,'%'))", nativeQuery = true)
    Set<UserAccount> findUsers(@Param("paramId") Long paramId, @Param("paramBusca") String paramBusca);
}