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
import java.util.HashSet;
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
            Optional<UserAccount> user = userRepository.findUserAccountByEmail(newUser.getEmail());
            if (user.isPresent())
                throw new UserBadRequestException("Usuário já existe");

            user = Optional.of(new UserAccount());
            user.get().setEmail(newUser.getEmail());
            user.get().setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.get().setName(newUser.getName());
            user.get().setGender(newUser.getGender());
            user.get().setUrl_photo(newUser.getUrl_photo());
            userRepository.saveAndFlush(user.get());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

//    @Transactional
//    public void uploadPhoto(String email, MultipartFile file) throws Exception{
//        try {
//            UserAccount user = userRepository.getUserByEmail(email);
//            if (user == null)
//                throw new UserBadRequestException("Usuário não registrado");
//
//            user.setPhoto(file.getBytes());
//            userRepository.save(user);
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }
//
    @Transactional(readOnly = true)
    public Set<UserAccount> getContacts(Long userLogado) throws Exception {
        try {
            Optional<UserAccount> user = userRepository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            //Set<Contact> contactsWithChats = userRepository.findByChats(userLogado);
            Set<UserAccount> contacts = userRepository.getContacts(userLogado);

//            if (!contactsWithChats.isEmpty()) {
//                for (Contact contact : contactsWithChats) {
//                    contacts.removeIf(contact2 -> contact2 != null && contact.getId().equals(contact2.getId()));
//                }
//            }
//
//            contactsWithChats.addAll(contacts);
//
//            return contactsWithChats;
            return contacts;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void addContact(Long userLogadoId, UserAccount newContact) throws Exception {
        try {
            if (!newContact.getId().equals(userLogadoId)){
                Optional<UserAccount> userLogado = userRepository.findById(userLogadoId);
                if (userLogado.isEmpty())
                    throw new UserNotFoundException("Usuário logado não encontrado");

                Optional<UserAccount> friend = userRepository.findById(newContact.getId());
                if (friend.isEmpty())
                    throw new UserNotFoundException("Usuário não encontrado");

                Contact contact = contactRepository.findContactByPrincipalAndFriend(userLogado.get().getId(),
                        friend.get().getId());
                if (contact != null)
                    throw new ContactBadRequestException("Esse contato já existe");

                contact = new Contact();
                contact.setPrincipal(userLogado.get());
                contact.setFriend(friend.get());
                contact.setDate_time(LocalDateTime.now());
                contactRepository.saveAndFlush(contact);

                userLogado.get().getContacts().add(contact);
            }
            else
                throw new UserBadRequestException("Usuário logado não pode se adicionar como amigo");

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteContact(Long userLogado, Long friendId) throws Exception {
        try {
            boolean exists = false;

            Optional<UserAccount> user = userRepository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário logado não encontrado");

            Optional<UserAccount> friend = userRepository.findById(friendId);
            if (friend.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            for (Contact contact : user.get().getContacts()) {
                if (contact.getFriend().getId().equals(friendId)) {
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
    public Set<UserAccount> findContacts(Long userLogado, String busca) throws Exception {
        try {
            Optional<UserAccount> user = userRepository.findById(userLogado);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário logado não encontrado");

            Set<UserAccount> contacts = userRepository.findContacts(user.get().getId(), busca);
            Set<UserAccount> contactsWithChats = userRepository.findContactsWithChats(userLogado, busca);

            if (!contactsWithChats.isEmpty()) {
                for (UserAccount contact : contactsWithChats) {
                    contacts.removeIf(contact2 -> contact2 != null && contact.getId().equals(contact2.getId()));
                }
            }

            contacts.addAll(contactsWithChats);

            return contacts;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<UserAccount> findUsers(Long userLogado, String busca) throws Exception {
        try {
            Set<UserAccount> contacts = new HashSet<>();

            Optional<UserAccount> logado = userRepository.findById(userLogado);
            if (logado.isEmpty())
                throw new UserNotFoundException("Usuário logado não encontrado");

            Set<UserAccount> users = userRepository.findUsers(userLogado, busca);

            for (UserAccount user : users){
                for (Contact contact : logado.get().getContacts()){
                    if (contact != null && contact.getFriend().getId().equals(user.getId())){
                        contacts.add(user);
                        break;
                    }
                }
            }

            if (!contacts.isEmpty())
                users.removeAll(contacts);

            return users;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public Set<UserAccount> findNoContacts(Long userLogado) throws Exception{
        try {
            Set<UserAccount> contacts = new HashSet<>();

            Optional<UserAccount> logado = userRepository.findById(userLogado);
            if (logado.isEmpty())
                throw new UserNotFoundException("Usuario logado não encontrado");

            Set<UserAccount> users = userRepository.findNoContacts(logado.get().getId());

            for (UserAccount user : users){
                for (Contact contact : logado.get().getContacts()){
                    if (contact != null && contact.getFriend().getId().equals(user.getId())){
                        contacts.add(user);
                        break;
                    }
                }
            }

            if (!contacts.isEmpty())
                users.removeAll(contacts);

            return users;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional(readOnly = true)
    public UserAccount getUserByEmail(String email) throws Exception {
        try {
            Optional<UserAccount> user = userRepository.findUserAccountByEmail(email);
            if (user.isEmpty())
                throw new UserNotFoundException("Usuário não encontrado");

            return user.get();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

//    @Transactional
//    public void deleteUser(Long id){
//        Optional<UserAccount> user = userRepository.findById(id);
//        userRepository.delete(user.get());
//    }
}