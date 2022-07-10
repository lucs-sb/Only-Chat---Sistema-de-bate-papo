package api.onlychat.controllers;

import api.onlychat.entities.Contact;
import api.onlychat.entities.Message;
import api.onlychat.services.MessageService;
import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class ApiRestController {
    @Autowired
    private UserAccountService userService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccount(@RequestBody UserAccount user) throws Exception{
        try {
            userService.addUserAccount(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{id}/contact")
    public Set<Contact> getContacts(@PathVariable("id") Long userLogado) throws Exception{
        try {
            return userService.getContacts(userLogado);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/{id}/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable("id") Long userLogado, @RequestBody UserAccount newContact) throws Exception{
        try {
            userService.addContact(userLogado, newContact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{user}/contact/{contact}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("user") Long userLogado, @PathVariable("contact") Long contact) throws Exception{
        try {
            userService.deleteContact(userLogado, contact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @RequestMapping("/{id}/busca")
    public Set<Contact> findContacts(@PathVariable("id") Long userLogado, @RequestParam("busca") String busca) throws Exception{
        try {
            return userService.findContacts(userLogado, busca);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @RequestMapping("/{id}/adicionar")
    public Set<UserAccount> findUsers(@PathVariable("id") Long userLogado, @RequestParam("adicionar") String busca) throws Exception{
        try {
            return userService.findUsers(userLogado, busca);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{userLogado}/message/{receiver}")
    public Set<Message> getAllChatMessages(@PathVariable Long userLogado, @PathVariable Long receiver) throws Exception{
        try {
            return messageService.gelAllMessages(userLogado, receiver);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody Message message) throws Exception{
        try {
            messageService.sendMessage(message);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}