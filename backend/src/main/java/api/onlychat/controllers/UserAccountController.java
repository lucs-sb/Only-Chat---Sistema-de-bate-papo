package api.onlychat.controllers;

import api.onlychat.services.UserAccountService;
import api.onlychat.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserAccountController {
    @Autowired
    private UserAccountService service;

    @GetMapping
    public List<UserAccount> getAllUserAccounts() throws Exception{
        try {
            return service.getAllUserAccounts();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    @PostMapping("/cadastrar")
    public void addUserAccount(@RequestBody UserAccount user) throws Exception{
        try {
            service.addUserAccount(user);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}