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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void addUserAccount(UserAccount newUser) throws Exception {
        try {
            UserAccount user = userRepository.getUserByEmail(newUser.getEmail());
            if (user != null)
                throw new UserBadRequestException("Usuário já existe");

            user = new UserAccount();
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setName(newUser.getName());
            user.setGender(newUser.getGender());
            user.setUrl_photo(newUser.getUrl_photo());
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void uploadPhoto(String email, MultipartFile file) throws Exception{
        try {
            UserAccount user = userRepository.getUserByEmail(email);
            if (user == null)
                throw new UserBadRequestException("Usuário não registrado");

            user.setPhoto(file.getBytes());
            userRepository.save(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Contact> getContacts(Long userLogado) throws Exception {
        try {
            Optional<UserAccount> user = userRepository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            Set<Contact> contactsWithChats = contactRepository.findByChats(userLogado);
            Set<Contact> contacts = contactRepository.getContacts(userLogado);

            if (!contactsWithChats.isEmpty()) {
                for (Contact contact : contactsWithChats) {
                    contacts.removeIf(contact2 -> contact2 != null && contact.getId().equals(contact2.getId()));
                }
            }

            contactsWithChats.addAll(contacts);

            if (contactsWithChats.isEmpty())
                throw new ContactNotFoundException("Usuário não contém contatos");

            return contactsWithChats;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void addContact(Long userLogadoId, UserAccount newContact) throws Exception {
        try {
            Optional<UserAccount> userLogado = userRepository.findById(userLogadoId);
            if (userLogado.isEmpty())
                throw new UserNotFoundException("Usuário logado não encontrado");

            Optional<UserAccount> friend = userRepository.findById(newContact.getId());
            if (friend.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            Contact contact = contactRepository.findContactByPrincipalAndFriendAndEmail(userLogadoId,
                    friend.get().getId(), friend.get().getEmail());
            if (contact != null)
                throw new ContactBadRequestException("Esse contato já existe");

            contact = new Contact();
            contact.setPrincipal(userLogadoId);
            contact.setFriend(friend.get().getId());
            contact.setEmail(friend.get().getEmail());
            contact.setNome(friend.get().getName());
            contact.setPhoto(friend.get().getPhoto());
            contact.setUrl_photo(friend.get().getUrl_photo());
            contact.setDate_time(LocalDateTime.now());
            contactRepository.saveAndFlush(contact);

            userLogado.get().getContacts().add(contact);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteContact(Long userLogado, Long contactId) throws Exception {
        try {
            boolean exists = false;

            Optional<UserAccount> user = userRepository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            for (Contact contact : user.get().getContacts()) {
                if (contactId.equals(contact.getId())) {
                    user.get().getContacts().remove(contact);
                    contactRepository.delete(contact);
                    exists = true;
                    break;
                }
            }
            if (!exists)
                throw new ContactBadRequestException("O usuário não possui esse contato");
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<Contact> findContacts(Long userLogado, String busca) throws Exception {
        try {
            Set<Contact> contacts = contactRepository.findContacts(userLogado, busca);
            Set<Contact> contactsWithChats = contactRepository.findContactsWithChats(userLogado, busca);

            if (!contactsWithChats.isEmpty()) {
                for (Contact contact : contactsWithChats) {
                    contacts.removeIf(contact2 -> contact2 != null && contact.getId().equals(contact2.getId()));
                }
            }

            contacts.addAll(contactsWithChats);

            if (contacts.isEmpty())
                throw new ContactNotFoundException("Não há registro de contatos para essa busca");

            return contacts;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<UserAccount> findUsers(Long userLogado, String busca) throws Exception {
        try {
            Optional<UserAccount> logado = userRepository.findById(userLogado);
            if (logado.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            Set<UserAccount> users = userRepository.findUsers(userLogado, busca);
            if (users.isEmpty())
                throw new UserNotFoundException("Não há registro de usuários");

            for (UserAccount user : users) {
                for (Contact contact : logado.get().getContacts()) {
                    if (contact != null && contact.getEmail().equals(user.getEmail())) {
                        users.remove(user);
                        break;
                    }
                }
            }

            if (users.isEmpty())
                throw new UserNotFoundException("Não existe usuário para ser adicionado");

            return users;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public UserAccount getUserByEmail(String email) throws Exception {
        try {
            UserAccount user = userRepository.getUserByEmail(email);
            if (user == null)
                throw new UserNotFoundException("Usuário não encontrado");

            user.setPassword("");

            return user;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}