package onlychat.api.services;

import onlychat.api.entities.UserAccount;
import onlychat.api.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserAccount> getAllUserAccounts() throws Exception{
        try {
            List<UserAccount> list = repository.findAll();
            if (list.isEmpty())
                throw new Exception("Não há registro de usuários");

            return list;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public Optional<UserAccount> findUserAccountByEmail(String email){
        return repository.findUserAccountByEmail(email);
    }

    @Transactional
    public UserAccount addUserAccount(UserAccount newUser) throws Exception{
        try {
            UserAccount user = repository.getUserAccountByEmail(newUser.getEmail());
            if (user != null)
                throw new Exception("Usuário já existe");

            user = new UserAccount();
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setName(newUser.getName());
            user.setGenero(newUser.getGenero());
            repository.saveAndFlush(user);

            return user;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}
