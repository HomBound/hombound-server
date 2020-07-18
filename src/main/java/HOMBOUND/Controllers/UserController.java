package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.UserCredentials;
import HOMBOUND.Services.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody HomBoundUser user){
        userService.createUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials user){
        return  ResponseEntity.ok(userService.login(user));

    }

//    @GetMapping("/login")
//    @ResponseBody
//    public ResponseEntity login(@RequestBody HomBoundUser user){
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
//        HomBoundUser logUser = userService.login(user);
//        if(logUser != null) {
//            System.out.println("Ready to return");
//            System.out.println(logUser.getPassword());
//            return new ResponseEntity(logUser);
//        }
//        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    // return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
//    }

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
