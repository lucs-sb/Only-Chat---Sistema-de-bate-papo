package api.onlychat.services;

import api.onlychat.entities.Contact;
import api.onlychat.entities.UserAccount;
import api.onlychat.exceptionHandler.User.UserBadRequestException;
import api.onlychat.repositories.ContactRepository;
import api.onlychat.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository repository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void addUserAccount(UserAccount newUser) throws Exception{
        try {
            UserAccount user = repository.getUserByEmail(newUser.getEmail());
            if (user != null)
                throw new UserBadRequestException("Usuário já existe");
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

    @Transactional
    public void addContact(Long userLogado, UserAccount newContact) throws Exception{
        try {
            Optional<UserAccount> user = repository.findById(userLogado);
            if (user.isEmpty())
                throw new Exception("");

            Contact contact = contactRepository.findByPrincipal(userLogado);
            if (contact != null)
                throw new Exception("");

            contact = new Contact();
            contact.setPrincipal(userLogado);
            contact.setEmail(newContact.getEmail());
            contact.setNome(newContact.getName());
            contact.setPhoto(newContact.getPhoto());
            contact.setDate_time(LocalDateTime.now());
            contactRepository.saveAndFlush(contact);

            user.get().getContacts().add(contact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

//    public void saveNewContact(Long userLogado, Contact newContact) throws Exception{
//        try {
//            Optional<UserAccount> user = repository.findById(userLogado);
//            if (user.isEmpty())
//                throw new Exception("");
//
//            user.get().getContacts().add(newContact);
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }
}