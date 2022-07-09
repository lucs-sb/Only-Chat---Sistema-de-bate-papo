package api.onlychat.controllers;

import api.onlychat.entities.Contact;
import api.onlychat.services.ContactService;
import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRestController {
    @Autowired
    private UserAccountService userService;
    @Autowired
    private ContactService contactService;

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

//    @GetMapping("/user/{id}/contacts")
//    public Set<Contact> getContacts(@PathVariable int id) throws Exception{
//        try {
//            return contactService.getContacts(id);
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
//    }

    @PostMapping("/user/{id}/newcontact")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable Long userLogado, @RequestBody UserAccount newContact) throws Exception{
        try {
            userService.addContact(userLogado, newContact);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}