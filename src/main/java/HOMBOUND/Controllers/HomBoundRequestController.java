package HOMBOUND.Controllers;

import HOMBOUND.Models.HomBoundRequest;
import HOMBOUND.Models.HomBoundUser;
import HOMBOUND.Models.enums.HomBoundStatus;
import HOMBOUND.Repositories.HomBoundRequestRepository;
import HOMBOUND.Repositories.HomBoundUserRepository;
import HOMBOUND.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("api/requests")
@RestController
public class HomBoundRequestController {
    @Autowired
    public HomBoundRequestRepository homBoundRequestRepository;

    @Autowired
    public HomBoundUserRepository homBoundUserRepository;

    @Autowired
    public ItemController itemController;

    @PostMapping(path = "/create/{userId}")
    public HomBoundRequest createRequest(@RequestBody HomBoundRequest homBoundRequest, @PathVariable("userId") Long userId){
        Optional<HomBoundUser> user = homBoundUserRepository.findById(userId);
        if(!user.isPresent()){
            throw new Error("User " + userId + " does not exist!");
        }
        homBoundRequest.setStatus(HomBoundStatus.Open.name());
        homBoundRequest.setRequestedBy(user.get());
        return homBoundRequestRepository.save(homBoundRequest);
    }

    @PostMapping(path="{requestId}/submit")
    public HomBoundRequest submitRequest(@PathVariable("requestId") Long requestId){
        Optional<HomBoundRequest> request = homBoundRequestRepository.findById(requestId);
        if(!request.isPresent()){
            throw new Error("Could not find HomBound Request with id " + requestId + "!");
        }
        request.get().setStatus(HomBoundStatus.Requested.name());
        return homBoundRequestRepository.save(request.get());
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

    @GetMapping(path="/requestedBy/{userId}")
    public List<HomBoundRequest> getRequestsByUser(@PathVariable("userId") Long userId){
        Optional<HomBoundUser> user = homBoundUserRepository.findById(userId);
        return user.map(currentUser -> {
            return currentUser.getHomBoundsRequested();
        }).orElseThrow(() -> new Error("User " + userId + " does not exist!"));
    }

    @GetMapping(path="/available")
    public List<HomBoundRequest> getAllAvailableRequests(){
        return homBoundRequestRepository.findAllByStatus(HomBoundStatus.Requested.name());
    }



    @DeleteMapping(path="/{requestId}/delete")
    public ResponseEntity deleteRequest(@PathVariable("requestId") Long requestId, @RequestBody Long userId){
        if(itemController.canUserUpdate(userId, requestId)) {
            homBoundRequestRepository.deleteById(requestId);
            return ResponseEntity.ok("Request Deleted");
        }
        else{
            throw new Error("User " + userId + "does not have permission to delete any items from request " + requestId);
        }

    }

    // Custom Input Types
    private static class RequestWithUserId {
        public Long userId;
        public HomBoundRequest request;

        public RequestWithUserId(Long userId, HomBoundRequest request) {
            this.userId = userId;
            this.request = request;
        }
    }

}
