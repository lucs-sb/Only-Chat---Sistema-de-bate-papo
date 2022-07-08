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
import java.util.Set;

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
    public Optional<UserAccount> findUserAccountByUsername(String email){
        return repository.findUserAccountByEmail(email);
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

//    @Transactional(readOnly = true)
//        public Set<UserAccount> getContacts(int id) throws Exception{
//        try {
//            Set<UserAccount> contacts = repository.getContacts(id);
//            if (contacts.isEmpty())
//                throw new UserBadRequestException("");
//
//            return contacts;
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }
}