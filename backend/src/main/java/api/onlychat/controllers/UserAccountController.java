package api.onlychat.controllers;

import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserAccountController {
    @Autowired
    private UserAccountService userService;
//    @Autowired
//    private ContactService contactService;

    @GetMapping
    public List<UserAccount> getAllUserAccounts() throws Exception{
        try {
            return userService.getAllUserAccounts();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/cadastrar")
    public void addUserAccount(@RequestBody UserAccount user) throws Exception{
        try {
            userService.addUserAccount(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("/{id}/contacts")
    public Set<UserAccount> getContacts(@PathVariable int id) throws Exception{
        try {
            return userService.getContacts(id);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}