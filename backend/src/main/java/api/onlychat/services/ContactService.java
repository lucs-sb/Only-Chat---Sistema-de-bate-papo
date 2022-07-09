package api.onlychat.services;

import api.onlychat.entities.Contact;
import api.onlychat.entities.UserAccount;
import api.onlychat.exceptionHandler.User.UserBadRequestException;
import api.onlychat.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Service
public class ContactService {
//    @Autowired
//    private ContactRepository contactRepository;
//    @Autowired
//    private UserAccountService userService;
//
//    @Transactional(readOnly = true)
//    public Set<Contact> getContacts(int id) throws Exception{
//        try {
//            Set<Contact> contacts = contactRepository.getContacts(id);
//            if (contacts.isEmpty())
//                throw new UserBadRequestException("");
//
//            return contacts;
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }
//
//    public void addContact(Long userLogado, UserAccount newContact) throws Exception{
//        try {
//            Contact contact = contactRepository.findByPrincipal(userLogado);
//            if (contact != null)
//                throw new Exception("");
//
//            contact = new Contact();
//            contact.setPrincipal(userLogado);
//            contact.setEmail(newContact.getEmail());
//            contact.setNome(newContact.getName());
//            contact.setPhoto(newContact.getPhoto());
//            contact.setDate_time(LocalDateTime.now());
//            contactRepository.saveAndFlush(contact);
//
//            userService.saveNewContact(userLogado, contact);
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }
}
