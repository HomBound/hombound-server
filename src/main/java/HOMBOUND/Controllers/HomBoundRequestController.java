package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.HomBoundUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RequestMapping("api/requests")
@RestController
public class HomBoundRequestController {
    @Autowired
    public HomBoundRequestRepository homBoundRequestRepository;

    @Autowired
    public HomBoundUserRepository homBoundUserRepository;

    @PostMapping(path = "/create/{userId}")
    public HomBoundRequest createRequest(@RequestBody HomBoundRequest homBoundRequest, @PathVariable("userId") Long userId){
        Optional<HomBoundUser> user = homBoundUserRepository.findById(userId);
        if(user == null){
            throw new Error("User " + userId + " does not exist!");
        }
        homBoundRequest.setStatus(HomBoundStatus.Requested);
        homBoundRequest.setRequestedBy(user.get());
        return homBoundRequestRepository.save(homBoundRequest);
    }
}
