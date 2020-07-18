package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.UserCredentials;
import HOMBOUND.Repositories.HomBoundUserRepository;
import HOMBOUND.Services.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("api/users")
@RestController
public class HomBoundUserController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @Autowired
    public HomBoundUserRepository homBoundUserRepository;

    @PostMapping(path = "/create")
    public HomBoundUser createUser(@RequestBody HomBoundUser user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return homBoundUserRepository.save(user);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials user){
        return  ResponseEntity.ok(userService.login(user));
    }


    @GetMapping
    public List<HomBoundUser> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public Optional<HomBoundUser> findUserById(@PathVariable("id") Long id){
        return userService.findUserById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@Valid @NotNull @PathVariable("id") @RequestBody HomBoundUser updatedUser){
        userService.updateUser(updatedUser);
    }




}
