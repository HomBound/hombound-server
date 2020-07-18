package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Repositories.HomBoundUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("api/users")
@RestController
public class HomBoundUserController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public HomBoundUserRepository homBoundUserRepository;

    @PostMapping(path = "/create")
    public HomBoundUser createUser(@RequestBody HomBoundUser user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return homBoundUserRepository.save(user);
    }
}
