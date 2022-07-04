package api.onlychat.services;

import api.onlychat.entities.UserAccount;
import api.onlychat.exceptionHandler.User.UserBadRequestException;
import api.onlychat.repositories.UserAccountRepository;
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
            return repository.findAll();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<UserAccount> findUserAccountByUsername(String userName){
        return repository.findUserAccountByUsername(userName);
    }

    @Transactional
    public void addUserAccount(UserAccount newUser) throws Exception{
        try {

            UserAccount user = repository.getUserByEmail(newUser.getEmail());
            if (user != null){
                throw new UserBadRequestException("Usuário já existe");
            }
            user = new UserAccount();
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setName(newUser.getName());
            user.setGender(newUser.getGender());
            user.setPhoto(newUser.getPhoto());
            repository.saveAndFlush(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}