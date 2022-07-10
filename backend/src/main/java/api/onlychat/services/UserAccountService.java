package api.onlychat.services;

import api.onlychat.entities.Contact;
import api.onlychat.entities.UserAccount;
import api.onlychat.exceptionHandler.Contact.ContactBadRequestException;
import api.onlychat.exceptionHandler.Contact.ContactNotFoundException;
import api.onlychat.exceptionHandler.User.UserBadRequestException;
import api.onlychat.exceptionHandler.User.UserNotFoundException;
import api.onlychat.repositories.ContactRepository;
import api.onlychat.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional(readOnly = true)
    public Set<Contact> getContacts(Long userLogado) throws Exception{
        try {
            Optional<UserAccount> user = repository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            Set<Contact> contacts = contactRepository.getContacts(userLogado);
            if (contacts.isEmpty())
                throw new ContactNotFoundException("Usuário não contém contatos");

            return contacts;
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
                throw new UserNotFoundException("Usuário não encontrado");

            Contact contact = contactRepository.findByPrincipalAndEmail(userLogado, newContact.getEmail());
            if (contact != null)
                throw new ContactBadRequestException("Esse contato já existe");

            contact = new Contact();
            contact.setPrincipal(userLogado);
            contact.setEmail(newContact.getEmail());
            contact.setNome(newContact.getName());
            contact.setPhoto(newContact.getPhoto());
            contact.setDate_time(new Date());
            contactRepository.saveAndFlush(contact);

            user.get().getContacts().add(contact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteContact(Long userLogado, Long contactId) throws Exception{
        try {
            boolean exists = false;

            Optional<UserAccount> user = repository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            for(Contact contact : user.get().getContacts()){
                if (contactId.equals(contact.getId())) {
                    user.get().getContacts().remove(contact);
                    repository.save(user.get());
                    contactRepository.delete(contact);
                    exists = true;
                    break;
                }
            }
            if (!exists)
                throw new ContactBadRequestException("O usuário não possui esse contato");


        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Contact> findContacts(Long userLogado, String busca) throws Exception{
        try {
            Set<Contact> contacts = contactRepository.findContacts(userLogado, busca);
            if (contacts.isEmpty())
                throw new ContactNotFoundException("Não há registro de contatos para essa busca");

            return contacts;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<UserAccount> findUsers(Long userLogado, String busca) throws Exception{
        try {
            Optional<UserAccount> logado = repository.findById(userLogado);
            if (logado.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            Set<UserAccount> users = repository.findUsers(userLogado, busca);
            if (users.isEmpty())
                throw new UserNotFoundException("Não há registro de usuários");

            for (UserAccount user : users){
                for (Contact contact : logado.get().getContacts()){
                    if (contact != null && contact.getEmail().equals(user.getEmail())){
                        users.remove(user);
                        break;
                    }
                }
            }

            if (users.isEmpty())
                throw new UserNotFoundException("Não existe usuário para ser adicionado");

            return users;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}