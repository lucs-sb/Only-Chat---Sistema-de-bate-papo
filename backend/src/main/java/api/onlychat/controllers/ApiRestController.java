package api.onlychat.controllers;

import api.onlychat.entities.Contact;
import api.onlychat.entities.Message;
import api.onlychat.services.MessageService;
import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRestController {
    @Autowired
    private UserAccountService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/login/{email}")
    public UserAccount login(@PathVariable("email") String email) throws Exception {
        try {
            return userService.getUserByEmail(email);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccount(@RequestBody UserAccount user) throws Exception {
        try {
            userService.addUserAccount(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

//    @PostMapping("/upload/{email}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void uploadPhoto(@PathVariable String email, @RequestParam("file") MultipartFile file) throws Exception{
//        try {
//            userService.uploadPhoto(email, file);
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }

    @GetMapping("/{logadoId}/contacts")
    public Set<UserAccount> getContacts(@PathVariable("logadoId") Long userLogado) throws Exception {
        try {
            return userService.getContacts(userLogado);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/{logadoId}/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable("logadoId") Long userLogado, @RequestBody UserAccount newContact) throws Exception {
        try {
            userService.addContact(userLogado, newContact);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("/{logadoId}/contact/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("logadoId") Long userLogado, @PathVariable("friendId") Long friendId)
            throws Exception {
        try {
            userService.deleteContact(userLogado, friendId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @RequestMapping(value = "/{logadoId}/busca", params = "busca")
    public Set<UserAccount> findContacts(@PathVariable("logadoId") Long userLogado, @RequestParam(required = false) String busca)
            throws Exception {
        try {
            return userService.findContacts(userLogado, busca);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @RequestMapping(value = "/{logadoId}", params = "adicionar")
    public Set<UserAccount> findNoFriends(@PathVariable("logadoId") Long userLogado, @RequestParam(required = false) String busca)
            throws Exception {
        try {
            return userService.findUsers(userLogado, busca);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/{logadoId}/adicionar")
    public Set<UserAccount> findNoContacts(@PathVariable("logadoId") Long userLogado)
            throws Exception {
        try {
            return userService.findNoContacts(userLogado);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/{userLogado}/message/{receiver}")
    public Page<Message> getAllChatMessages(Pageable pageable, @PathVariable Long userLogado, @PathVariable Long receiver)
            throws Exception {
        try {
            return messageService.gelAllMessages(pageable, userLogado, receiver);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody Message message) throws Exception {
        try {
            messageService.sendMessage(message);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id){
//        userService.deleteUser(id);
//    }
}