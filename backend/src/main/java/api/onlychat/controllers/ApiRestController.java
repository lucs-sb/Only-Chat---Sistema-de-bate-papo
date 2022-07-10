package api.onlychat.controllers;

import api.onlychat.entities.Contact;
import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRestController {
    @Autowired
    private UserAccountService userService;

    @PostMapping("/user/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUserAccount(@RequestBody UserAccount user) throws Exception{
        try {
            userService.addUserAccount(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/user/{id}/contact")
    public Set<Contact> getContacts(@PathVariable("id") Long userLogado) throws Exception{
        try {
            return userService.getContacts(userLogado);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/user/{id}/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable("id") Long userLogado, @RequestBody UserAccount newContact) throws Exception{
        try {
            userService.addContact(userLogado, newContact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping("/user/{user}/contact/{contact}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("user") Long userLogado, @PathVariable("contact") Long contact) throws Exception{
        try {
            userService.deleteContact(userLogado, contact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @RequestMapping("/user/{id}/busca")
    public Set<Contact> findContacts(@PathVariable("id") Long userLogado, @RequestParam("busca") String busca) throws Exception{
        try {
            return userService.findContacts(userLogado, busca);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @RequestMapping("/user/{id}/adicionar")
    public Set<UserAccount> findUsers(@PathVariable("id") Long userLogado, @RequestParam("adicionar") String busca) throws Exception{
        try {
            return userService.findUsers(userLogado, busca);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}