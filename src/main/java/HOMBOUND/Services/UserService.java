package HOMBOUND.Services;

import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.UserCredentials;
import HOMBOUND.Repositories.HomBoundUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    HomBoundUserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void createUser(HomBoundUser user){
        userRepository.save(user);
    }

    public List<HomBoundUser> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<HomBoundUser> findUserById(Long id){
        return userRepository.findById(id);
    }

    public void updateUser(HomBoundUser updatedUser){
        Optional<HomBoundUser> userToBeUpdated = userRepository.findById(updatedUser.getId());
        if(userToBeUpdated.isPresent()){
            updatedUser = userToBeUpdated.get();
        };
        userRepository.save(updatedUser);
    }

    public HomBoundUser login(UserCredentials user){
        System.out.println("Login Method");

        HomBoundUser dbUser = userRepository.findByUsername(user.getUsername());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        if (dbUser != null && user.getPassword().equals(dbUser.getPassword())){
            System.out.println("pasword are equal");
            dbUser.setActive(true);
            dbUser.setPassword(null);
            return dbUser;
        }
        System.out.println("pasword are NOT equal");
        return null;

    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}

