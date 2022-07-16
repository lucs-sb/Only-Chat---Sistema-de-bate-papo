package api.onlychat.services;

import api.onlychat.entities.Contact;
import api.onlychat.entities.Message;
import api.onlychat.entities.UserAccount;
import api.onlychat.exceptionHandler.Contact.ContactNotFoundException;
import api.onlychat.exceptionHandler.User.UserNotFoundException;
import api.onlychat.exceptionHandler.message.MessageNotFoundException;
import api.onlychat.repositories.ContactRepository;
import api.onlychat.repositories.MessageRepository;
import api.onlychat.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserAccountRepository userRepository;

    @Transactional(readOnly = true)
    public Page<Message> gelAllMessages(Pageable pageable, Long userLogado, Long receiver) throws Exception{
        try {
            return messageRepository.getAllChatMessages(pageable, userLogado, receiver);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void sendMessage(Message newMessage, Long userLogado, Long friend) throws Exception{
        try {
            Optional<UserAccount> sender = userRepository.findById(userLogado);
            if (sender.isEmpty())
                throw new UserNotFoundException("Usuario logado não encontrado");

            Optional<UserAccount> receiver = userRepository.findById(friend);
            if (receiver.isEmpty())
                throw new UserNotFoundException("Usuario não encontrado");

            Contact contact = contactRepository.findContactByPrincipalAndFriend(sender.get().getId(), receiver.get().getId());
            if (contact == null)
                throw new ContactNotFoundException("Contato não encontrado");

            Message message = new Message();
            message.setContact_id(contact);
            message.setMessage(newMessage.getMessage());
            message.setDate_time(LocalDateTime.now());
            messageRepository.saveAndFlush(message);

            contact.getMessages().add(message);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}
