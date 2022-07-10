package api.onlychat.services;

import api.onlychat.entities.Contact;
import api.onlychat.entities.Message;
import api.onlychat.exceptionHandler.Contact.ContactNotFoundException;
import api.onlychat.exceptionHandler.message.MessageNotFoundException;
import api.onlychat.repositories.ContactRepository;
import api.onlychat.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public Set<Message> gelAllMessages(Long userLogado, Long receiver) throws Exception{
        try {
            Set<Message> messages = messageRepository.getAllChatMessages(userLogado, receiver);
            if (messages.isEmpty())
                throw new MessageNotFoundException("Conversa vazia");

            return messages;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void sendMessage(Message newMessage) throws Exception{
        try {
            Contact contact = contactRepository.findContactByPrincipalAndFriend(newMessage.getSender(), newMessage.getReceiver());
            if (contact == null)
                throw new ContactNotFoundException("Contato não encontrado");

            Message message = new Message();
            message.setMessage(newMessage.getMessage());
            message.setSender(newMessage.getSender());
            message.setReceiver(newMessage.getReceiver());
            message.setDate_time(LocalDateTime.now());
            messageRepository.saveAndFlush(message);

            contact.getMessages().add(message);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}
