package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.HomBoundUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
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
        if(!user.isPresent()){
            throw new Error("User " + userId + " does not exist!");
        }
        homBoundRequest.setStatus(HomBoundStatus.Requested.name());
        homBoundRequest.setRequestedBy(user.get());
        return homBoundRequestRepository.save(homBoundRequest);
    }

    @PostMapping(path = "/{requestId}/accept/{userId}")
    public HomBoundRequest acceptRequest(@PathVariable("requestId") Long requestId, @PathVariable("userId") Long userId){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        if(!request.isPresent()){
            throw new Error("Could not find HomBound Request with id " + requestId + "!");
        }
        Optional<HomBoundUser> acceptingUser = homBoundUserRepository.findById(userId);
        if(!acceptingUser.isPresent()){
            throw new Error("Could not find user with id " + requestId + "!");
        }
        Instant instant = Instant.now();
        Date acceptedAt = Date.from(instant);
        request.get().setAcceptedBy(acceptingUser.get());
        request.get().setAcceptedAt(acceptedAt);
        request.get().setStatus(HomBoundStatus.Accepted.name());
        return homBoundRequestRepository.save(request.get());
    }
}
